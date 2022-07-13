package com.Logan50miles.Implementation;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.Address;
import com.Logan50miles.Entity.OtpConfirmation;
import com.Logan50miles.Entity.Players;
import com.Logan50miles.Entity.Referral;
import com.Logan50miles.Entity.ReferralSubscription;
import com.Logan50miles.Entity.User;
import com.Logan50miles.Model.AddressTemp;
import com.Logan50miles.Model.ViewAddressList;
import com.Logan50miles.Repository.AddressRepository;
import com.Logan50miles.Repository.OtpConfirmationRepository;
import com.Logan50miles.Repository.PlayersRepository;
import com.Logan50miles.Repository.ReferralRepository;
import com.Logan50miles.Repository.ReferralSubscriptionRepository;
import com.Logan50miles.Repository.UserRepository;
import com.Logan50miles.Service.UserService;
import com.Logan50miles.Util.ResourceNotFoundException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class UserImplementation implements UserService {


	@Autowired
    private UserRepository userRepository;	
	@Autowired
	private PlayersRepository playersRepository;
    @Autowired
    private OtpConfirmationRepository otpConfirmationRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ReferralSubscriptionRepository referralSubscriptionRepository;
    @Autowired
    private ReferralRepository referralRepository;
    
    //AWS S3 BUCKET ACCESS
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    private AmazonS3 s3Client;
    private static String image="https://mylogantown.s3.amazonaws.com/event/";

    
    //public static final String ACCOUNT_SID = "AC7a6eead9ea9475bc2385fa3232a32387";
    //public static final String AUTH_TOKEN = "32e31b41496bec37af5db3ac10f770cd";
    
    BCryptPasswordEncoder bCryptPasswordEncoder =new BCryptPasswordEncoder(12);
    
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User getUserByEmailId(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Override
    public ResponseEntity<String> userRegistration(User user, MultipartFile file) throws ParseException, IOException {
    	ResponseEntity<String> result = null;
    	User userdata = userRepository.findByEmail(user.getEmail());
    	if (userdata!=null) {
    		result = ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\" : \"Already Registered\"}");
    	}
    	else {
    		if(file != null){
    			this.uploadImage(file,"event");
    			user.setiUmg(image+file.getOriginalFilename());
			}
			else {
				user.setiUmg("https://mylogantown.s3.amazonaws.com/event/player.png");
			}
    		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    		user.setUserReferral(createReferralCode());
    		userRepository.save(user);
    		OtpConfirmation confirmationToken = new OtpConfirmation(user);
			otpConfirmationRepository.save(confirmationToken);
			verificationOtp(user.getuPhone(),
					"Your LOGAN50MILES user verification OTP is:" + confirmationToken.getOtp());
    		result = ResponseEntity.status(HttpStatus.CREATED).body("{\"message\" : \"User Registered Successfully\"}");
    	}
    	return result;
    }
    
    @Override
    public ResponseEntity<String> userLogin(String email, String password) throws ParseException {
    	ResponseEntity<String> result = null;
    	User user = userRepository.findByEmail(email);
    	if (user!=null) {
    		if(bCryptPasswordEncoder.matches(password, user.getPassword())) {
    			
    			result = ResponseEntity.status(HttpStatus.OK).body("{\"message\" : \"User Login Success\", \"userid\" : \""+user.getUserid()+"\"}");
			}
			else {
				result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\" : \"Incorrect password. Try again.\"}");
			}
		}
		else {
			result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\" : \"Enter valid EmailId\"}");
		}
    	user.setEnabled(true);
    	return result; 
    }
    
    
    @Override
	public ResponseEntity<String> GuestUserRegistration(User user) throws ParseException {
		ResponseEntity<String> result;
		User existence = userRepository.findByEmail(user.getEmail());
		if (existence != null) {

			result = ResponseEntity.status(HttpStatus.CONFLICT).body("Already Registered");
		}

		else {
			user.setUserReferral(createReferralCode());

			if (user.getAffiliateCode() == null) {
				user.setAffiliateCode("None affiliated");
			} else {
				this.getRefCount(user.getAffiliateCode());
			}
			/*
			 * Referral module and increasing referral count on friend
			 */
			Referral referral = new Referral();
			referral.setRefCod(user.getUserReferral());
			referral.setCusId(user.getEmail());
			referral.setRefCount(0);
			referral.setRefAmount(0);
			referralRepository.save(referral);

			ReferralSubscription referralSubscribtion = new ReferralSubscription();
			LocalDate today = LocalDate.now();
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(today.toString());
			referralSubscribtion.setCusId(user.getEmail());
			referralSubscribtion.setPlan("SILVER");
			referralSubscribtion.setDate(date);
			referralSubscribtion.setStatus("ACTIVE");
			referralSubscriptionRepository.save(referralSubscribtion);
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setEnabled(true);
			userRepository.save(user);
			OtpConfirmation confirmationToken = new OtpConfirmation(user);
			System.out.println(confirmationToken.getOtp());
			otpConfirmationRepository.save(confirmationToken);
			verificationOtp(user.getuPhone(),"Your LOGAN50MILES verification OTP is:"+confirmationToken.getOtp());
			result = ResponseEntity.status(HttpStatus.CREATED).body("registered:" + user.getUserid());
		}
		return result;
	}


    public void getRefCount(String refCod) {
		Referral referral = referralRepository.findByRefCod(refCod);
		referral.setRefCount(referral.getRefCount() + 1);
	}
    
    public String createReferralCode(){
        int codeLength=8;
        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < codeLength; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        System.out.println(output);
        return output ;
    }
    
    @Override
    public String deleteUser(int id) {
    	userRepository.deleteById(id);
    	return "User Deleted Successfully";
    }
    
    @Override
    public String updateUser(User user,MultipartFile file) throws IOException {
    	User usr=userRepository.findByEmail(user.getEmail());
    	usr.setAffiliateCode(usr.getAffiliateCode());
    	usr.setEmail(usr.getEmail());
    	usr.setLname(user.getLname());
    	usr.setPassword(usr.getPassword());
    	usr.setuPhone(user.getuPhone());
    	usr.setUserid(user.getUserid());
    	usr.setUserReferral(usr.getUserReferral());
    	
    	if(file!=null) {
    		this.uploadImage(file,"event");
    		usr.setiUmg(image+file.getOriginalFilename());
    	}
    	else {
    		usr.setiUmg(usr.getiUmg());
    	}	
    	userRepository.save(usr);
    	return "updated";
    }
    
    @Override
    public ResponseEntity<String> forgotPassword(String email) throws ParseException {
        ResponseEntity<String> result = null;
        User userData = userRepository.findByEmail(email);
        if(userData!=null) {
            OtpConfirmation Otp = new OtpConfirmation(userData);
            System.out.println(Otp.getOtp());
            otpConfirmationRepository.save(Otp);

            verificationOtp(userData.getuPhone(), "LOGAN50MILES verification OTP is:" + Otp.getOtp());
            result = ResponseEntity.status(HttpStatus.CREATED).body("{ \"message\" : \""+Otp.getOtp()+"\"}");
        }else{
            result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\" : \"Please Register\"}");
        }
        return result;
    }
    
    public void verificationOtp(String phone, String body) {
        // Find your Account Sid and Token at twilio.com/console
        // and set the environment variables. See http://twil.io/secure

       // Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        //Message message = Message.creator(new PhoneNumber(phone), new PhoneNumber("+13237464865"), body).create();

       //System.out.println(message.getBody());
    	System.out.println(phone+" : "+body);
    }
    
    @Override
    public ResponseEntity <String> confirmRegistration(String confirmationToken){
    	ResponseEntity<String> result;
    	OtpConfirmation token = otpConfirmationRepository.findByOtp(confirmationToken);
    	if(token!=null) {
    		User user=userRepository.findByEmail(token.getUser().getEmail());
    		user.setEnabled(true);
    		userRepository.save(user);
    		result=ResponseEntity.status(HttpStatus.ACCEPTED).body("USER");
    		otpConfirmationRepository.delete(token);
    	}
    	else {
    		result=ResponseEntity.status(HttpStatus.CONFLICT).body("Incorrect Otp");
    	}
    	return result;
    }

    
    @Override
    public ResponseEntity<String> updatePassword(String email, String password) throws ParseException {
        ResponseEntity<String> result = null;
        User userData = userRepository.findByEmail(email);
        if(userData!=null) {
            userData.setPassword(bCryptPasswordEncoder.encode(password));
            userRepository.save(userData);
            result = ResponseEntity.status(HttpStatus.OK).body("{\"message\" : \"Reset Sucessfully\"}");

        }else{
            result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\" : \"Please Register\"}");
        }
        return result;
    }
    
    @Override
    public Players addPlayers(Players p, MultipartFile pimg) throws IOException {
    	
    	if(pimg!=null) {
    		this.uploadImage(pimg,"event");
    		p.setPlayerimg(image+pimg.getOriginalFilename());
    	}
    	return playersRepository.save(p);
    }
    
    @Override
    public Players updatePlayers(Players p, MultipartFile pimg) throws IOException, ResourceNotFoundException {
    	Players existance = playersRepository.findById(p.getPlayerid()).orElseThrow(()-> new ResourceNotFoundException("Resource Not found"));
    	if(pimg!=null) {
    		this.uploadImage(pimg, "event");
    		existance.setPlayerimg(image+pimg.getOriginalFilename());
    	}
    	else {
    		existance.setPlayerimg(existance.getPlayerimg());
    	}
    	if(p.getPlayername()!=null) {
    		existance.setPlayername(p.getPlayername());
    	}
    	else {
    		existance.setPlayername(existance.getPlayername());
    	}
    	if(p.getDescription()!=null) {
    		existance.setDescription(p.getDescription());
    	}
    	else {
    		existance.setDescription(existance.getDescription());
    	}
    	if(p.getEmail()!=null) {
    		existance.setEmail(p.getEmail());
    	}
    	else {
    		existance.setEmail(existance.getEmail());
    	}
    	if(p.getPhoneno()!=null) {
    		existance.setPhoneno(p.getPhoneno());
    	}
    	else {
    		existance.setPhoneno(existance.getPhoneno());
    	}
    	existance.setPlayerid(existance.getPlayerid());
    	return playersRepository.save(existance);
    }
    
    @Override
    public String deletePlayers(int id) {
    	playersRepository.deleteById(id);
    	return "Player Deleted";
    }
    
    @Override
    public List<Players> getAllPlayers() {
    	return playersRepository.findAll();
    }
    
    @Override
    public Players getPlayerbyId(int id) throws ResourceNotFoundException {
    	return playersRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource Not found"));
    }
    
    @Override
    public User getUserAddressByEmail(String email){
    	return userRepository.findByEmail(email);
    }
    @Override
    public Address addAddress(Address address) {
    	return addressRepository.save(address);
    }
    @Override
    public ViewAddressList getAddress(String email) {
    	User user=userRepository.findByEmail(email);
    	ViewAddressList viewAddressList=new ViewAddressList();
    	List<AddressTemp> address=new ArrayList<AddressTemp>();
    	viewAddressList.setUserid(user.getUserid());
    	viewAddressList.setuName(user.getFname());
    	viewAddressList.setuPhone(user.getuPhone());
    	
    	for(Address ad:user.getAddress()) {
    		AddressTemp addre=new AddressTemp();
    		addre.setId(ad.getId());
    		addre.setFlatNo(ad.getFlatNo());
    		addre.setLandMark(ad.getLandMark());
    		addre.setStreet(ad.getStreet());
    		addre.setPin(ad.getPin());
    		addre.setCity(ad.getCity());
    		addre.setName(ad.getName());
    		addre.setPhone(ad.getPhone());
    		addre.setEmail(ad.getEmail());
    		addre.setState(ad.getState());
    		addre.setCountry(ad.getCountry());
    		address.add(addre);
    		
    		
    	}
    	viewAddressList.setAddress(address);
    	return viewAddressList;
    }
    @Override
    public String deleteAddress(int id) {
    	addressRepository.deleteById(id);
    	return "deleted";
    }
    @Override
    public String updateAddress(Address address,int addId) {
    	
    	Address add=addressRepository.findByAddId(addId);
    	add.setName(address.getName());
    	add.setPhone(address.getPhone());
    	add.setFlatNo(address.getFlatNo());
    	add.setCity(address.getCity());
    	add.setLandMark(address.getLandMark());
    	add.setStreet(address.getStreet());
    	add.setPin(address.getPin());
    	add.setEmail(address.getEmail());
    	add.setUserid(add.getUserid());
    	add.setState(address.getState());
    	add.setCountry(address.getCountry());
    	addressRepository.save(add);
     return "updated";
    }
    
    //  Image Uploaded
    public String deleteS3Img(String keyName, String name) {
    	final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName + "/" + name, keyName);
    	s3Client.deleteObject(deleteObjectRequest);
    	return "deleted";
    }

    @PostConstruct
    private void initAmazon() {
    	BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
    	this.s3Client = AmazonS3ClientBuilder.standard().withRegion(region)
    			.withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
    }
    
    public void uploadImage(MultipartFile image, String name) throws IOException {
    	if (!image.isEmpty()) {
    		ObjectMetadata objectMetadata = new ObjectMetadata();
    		objectMetadata.setContentType(image.getContentType());
    		this.s3Client.putObject(new PutObjectRequest(bucketName +"/"+name, image.getOriginalFilename(),
    				image.getInputStream(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
    	}
    } 
    
}
