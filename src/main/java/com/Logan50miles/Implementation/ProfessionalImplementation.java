package com.Logan50miles.Implementation;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.BookingEvents;
import com.Logan50miles.Entity.Events;
import com.Logan50miles.Entity.FeedComment;
import com.Logan50miles.Entity.Likes;
import com.Logan50miles.Entity.OtpConfirmationPlayer;
import com.Logan50miles.Entity.PlayerProfile;
import com.Logan50miles.Entity.Professionals;
import com.Logan50miles.Entity.ProfileComment;
import com.Logan50miles.Repository.BookingEventsRepository;
import com.Logan50miles.Repository.EventsRepository;
import com.Logan50miles.Repository.FeedCommentRepository;
import com.Logan50miles.Repository.LikesRepository;
import com.Logan50miles.Repository.OtpConfirmationPlayerRepository;
import com.Logan50miles.Repository.PlayerProfileRepository;
import com.Logan50miles.Repository.ProfessionalsRepository;
import com.Logan50miles.Repository.ProfileCommentRepository;
import com.Logan50miles.Service.ProfessionalService;
import com.Logan50miles.Util.Mailer;
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
public class ProfessionalImplementation implements ProfessionalService {

	@Autowired
	private ProfessionalsRepository professionalsRepository;
	@Autowired
	private OtpConfirmationPlayerRepository otpConfirmationPlayerRepository;
	@Autowired
	private PlayerProfileRepository playerProfileRepository;
	@Autowired
	private FeedCommentRepository feedCommentRepository;
	@Autowired
	private ProfileCommentRepository profileCommentRepository;
	@Autowired
	private EventsRepository eventsRepository;
	@Autowired
	private BookingEventsRepository bookingEventsRepository;
	@Autowired
	private LikesRepository likesRepository;

	// AWS S3 BUCKET ACCESS
	@Value("${cloud.aws.credentials.accessKey}")
	private String accessKey;

	@Value("${cloud.aws.credentials.secretKey}")
	private String secretKey;

	@Value("${cloud.aws.region.static}")
	private String region;

	@Value("${app.awsServices.bucketName}")
	private String bucketName;

	private AmazonS3 s3Client;
	private static String image = "https://mylogantown.s3.amazonaws.com/event/";

	// public static final String ACCOUNT_SID =
	// "AC7a6eead9ea9475bc2385fa3232a32387";
	// public static final String AUTH_TOKEN = "32e31b41496bec37af5db3ac10f770cd";

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@Override
	public List<Professionals> getAllPlayers() {
		return professionalsRepository.findAll();
	}

	@Override
	public Professionals getPlayerByEmailId(String emailId) {
		return professionalsRepository.findByEmail(emailId);
	}

	@Override
	public ResponseEntity<String> registerationPlayer(Professionals playerdetails, MultipartFile file,
			MultipartFile file1, MultipartFile file2) throws IOException {
		ResponseEntity<String> result = null;
		Professionals playerData = professionalsRepository.findByEmail(playerdetails.getEmail());
		if (playerData != null) {
			result = ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\" : \"Already Registered \"}");
		} else {
			if (file != null) {
				this.uploadImage(file, "event");
				playerdetails.setPimg(image + file.getOriginalFilename());
			} else {
				playerdetails.setPimg(playerdetails.getPimg());
			}
			if (file1 != null) {
				this.uploadImage(file1, "event");
				playerdetails.setImgproof(image + file1.getOriginalFilename());
			} else {
				playerdetails.setImgproof(playerdetails.getImgproof());
			}
			if (file2 != null) {
				this.uploadImage(file2, "event");
				playerdetails.setVideoproof(image + file2.getOriginalFilename());
			} else {
				playerdetails.setVideoproof(playerdetails.getVideoproof());
			}
			playerdetails.setPassword(encoder.encode(playerdetails.getPassword()));
			playerdetails.setStatus("WAITING FOR APPROVAL");
			professionalsRepository.save(playerdetails);
			OtpConfirmationPlayer confirmationToken = new OtpConfirmationPlayer(playerdetails);
			otpConfirmationPlayerRepository.save(confirmationToken);
			verificationOtp(playerdetails.getPhoneno(),
					"Your PIKE50MILES user verification OTP is:" + confirmationToken.getOtp());
			result = ResponseEntity.status(HttpStatus.CREATED).body("{\"message\" : \"Register Succesfully\"}");
		}
		return result;
	}

	@Override
	public ResponseEntity<String> loginPlayer(String emailId, String password) {
		ResponseEntity<String> result = null;
		Professionals playerData = professionalsRepository.findByEmail(emailId);
		if (playerData != null) {
			if (encoder.matches(password, playerData.getPassword())) {
				result = ResponseEntity.status(HttpStatus.OK)
						.body("{\"message\" : \"Player Login Success\", \"playerid\" : \"" + playerData.getPid()
								+ "\", \"emailId\" : \"" + playerData.getEmail() + "\"}");
			} else {
				result = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body("{\"message\" : \"Incorrect password. Try again.\"}");
			}

		} else {
			result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\" : \"Please Register\"}");
		}
		return result;
	}

	@Override
	public String approvePlayer(int id, String status) throws ResourceNotFoundException {
		Professionals pro = professionalsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not found"));
		pro.setStatus(status);
		professionalsRepository.save(pro);
		String text = "<h4><u>Hi " + pro.getFname() + "</u></h4>" + "<h5>Your Player Registration for Pike50Miles has "
				+ status + "</h5>";
		Mailer mail = new Mailer();
		mail.sendMail("PIKE50MILES PLAYER APPROVAL", text, pro.getEmail(), "no_reply@memebike.tv", "Sal76928");

		return "Player Approved";
	}

	@Override
	public String deletePlayer(int id) {
		professionalsRepository.deleteById(id);
		return "Player Deleted Successfully";
	}

	@Override
	public String updatePlayer(Professionals playerdetails, MultipartFile file, MultipartFile file1,
			MultipartFile file2) throws ResourceNotFoundException, IOException {
		Professionals player = professionalsRepository.findById(playerdetails.getPid())
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not found"));
		if (file != null) {
			this.uploadImage(file, "event");
			player.setPimg(image + file.getOriginalFilename());
		} else {
			player.setPimg(player.getPimg());
		}
		if (file1 != null) {
			this.uploadImage(file1, "event");
			player.setImgproof(image + file1.getOriginalFilename());
		} else {
			player.setImgproof(player.getImgproof());
		}
		if (file2 != null) {
			this.uploadImage(file2, "event");
			player.setVideoproof(image + file2.getOriginalFilename());
		} else {
			player.setVideoproof(player.getVideoproof());
		}
		if (playerdetails.getFname() != null) {
			player.setFname(playerdetails.getFname());
		} else {
			player.setFname(player.getFname());
		}
		if (playerdetails.getLname() != null) {
			player.setLname(playerdetails.getLname());
		} else {
			player.setLname(player.getLname());
		}
		if (playerdetails.getPhoneno() != null) {
			player.setPhoneno(playerdetails.getPhoneno());
		} else {
			player.setPhoneno(player.getPhoneno());
		}
		if (playerdetails.getDob() != null) {
			player.setDob(playerdetails.getDob());
		} else {
			player.setDob(player.getDob());
		}
		if (playerdetails.getDescription() != null) {
			player.setDescription(playerdetails.getDescription());
		} else {
			player.setDescription(player.getDescription());
		}
		if (playerdetails.getAffiliateCode() != null) {
			player.setAffiliateCode(playerdetails.getAffiliateCode());
		} else {
			player.setAffiliateCode(player.getAffiliateCode());
		}
		if (playerdetails.getUserReferral() != null) {
			player.setUserReferral(playerdetails.getUserReferral());
		} else {
			player.setUserReferral(playerdetails.getUserReferral());
		}
		if (playerdetails.getState() != null) {
			player.setState(playerdetails.getState());
		} else {
			player.setState(player.getState());
		}
		if (playerdetails.getCity() != null) {
			player.setCity(playerdetails.getCity());
		} else {
			player.setCity(player.getCity());
		}
		if (playerdetails.getCountry() != null) {
			player.setCountry(playerdetails.getCountry());
		} else {
			player.setCountry(player.getCountry());
		}
		if (playerdetails.getInstaurl() != null) {
			player.setInstaurl(playerdetails.getInstaurl());
		} else {
			player.setInstaurl(player.getInstaurl());
		}
		if (playerdetails.getFburl() != null) {
			player.setFburl(playerdetails.getFburl());
		} else {
			player.setFburl(player.getFburl());
		}
		if (playerdetails.getEventlevel() != null) {
			player.setEventlevel(playerdetails.getEventlevel());
		} else {
			player.setEventlevel(player.getEventlevel());
		}
		player.setPassword(player.getPassword());
		player.setPid(player.getPid());
		player.setEmail(player.getEmail());
		professionalsRepository.save(player);
		return "Player Updated";
	}

	@Override
	public ResponseEntity<String> resetPlayerPassword(String emailId, String password) throws ParseException {
		ResponseEntity<String> result = null;
		Professionals playerData = professionalsRepository.findByEmail(emailId);
		if (playerData != null) {
			playerData.setPassword(encoder.encode(password));
			professionalsRepository.save(playerData);
			result = ResponseEntity.status(HttpStatus.OK).body("{\"message\" : \"Reset Sucessfully\"}");

		} else {
			result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\" : \"Please Register\"}");
		}
		return result;
	}

	@Override
	public ResponseEntity<String> forgotPassword(String emailId) throws ParseException {
		ResponseEntity<String> result = null;
		Professionals playerData = professionalsRepository.findByEmail(emailId);

		if (playerData != null) {
			OtpConfirmationPlayer old = otpConfirmationPlayerRepository.findByProf(playerData);
			if (old != null) {
				otpConfirmationPlayerRepository.deleteById(old.getOtpid());
			}
			OtpConfirmationPlayer Otp = new OtpConfirmationPlayer(playerData);
			otpConfirmationPlayerRepository.save(Otp);
			Mailer mail = new Mailer();
			mail.sendMail("PIKE50MILES Reset Password", "PIKE50MILES verification OTP is:" + Otp.getOtp(), emailId,
					"no_reply@memebike.tv", "Sal76928");
			verificationOtp(playerData.getPhoneno(), "PIKE50MILES verification OTP is:" + Otp.getOtp());
			result = ResponseEntity.status(HttpStatus.CREATED).body("{ \"message\" : \"" + Otp.getOtp() + "\"}");
		} else {
			result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\" : \"Please Register\"}");
		}
		return result;
	}

	public void verificationOtp(String phone, String body) {
		// Find your Account Sid and Token at twilio.com/console
		// and set the environment variables. See http://twil.io/secure

		// Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		// Message message = Message.creator(new PhoneNumber(phone), new
		// PhoneNumber("+13237464865"), body).create();

		// System.out.println(message.getBody());
		System.out.println(phone + " : " + body);
	}

	@Override
	public ResponseEntity<String> confirmOtpVerification(String otp) {
		ResponseEntity<String> result;
		OtpConfirmationPlayer otpverify = otpConfirmationPlayerRepository.findByOtp(otp);
		if (otpverify != null) {
			Professionals professionals = professionalsRepository.findById(otpverify.getProf().getPid()).orElse(null);
			professionalsRepository.save(professionals);
			result = ResponseEntity.status(HttpStatus.ACCEPTED).body("{\"message\" : \"ADMIN\"}");
			otpConfirmationPlayerRepository.deleteById(otpverify.getOtpid());

		} else {
			result = ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\" : \"Incorrect Otp\"}");
		}
		return result;
	}

	@Override
	public List<Professionals> getProfessionalsByEventLevel(String eventlevel) {
		return professionalsRepository.findByEventlevel(eventlevel);
	}

	@Override
	public PlayerProfile addProfileFeed(PlayerProfile pp, MultipartFile feed) throws IOException {
		if (feed != null) {
			this.uploadImage(feed, "event");
			pp.setFeedurl(image + feed.getOriginalFilename());
		}
		return playerProfileRepository.save(pp);
	}

	@Override
	public List<PlayerProfile> getAllProfileFeed() {
		return playerProfileRepository.findAll();
	}

	@Override
	public List<PlayerProfile> getAllProfileFeedByPlayerId(int pid) {
		return playerProfileRepository.findAllByPid(pid);
	}

	@Override
	public PlayerProfile getProfileFeedByFeedId(int ppid) throws ResourceNotFoundException {
		return playerProfileRepository.findById(ppid).orElseThrow(() -> new ResourceNotFoundException("Resource Not found"));
	}
	
	@Override
	public int getLikebyFeedId(int ppid) throws ResourceNotFoundException {
		PlayerProfile pp = playerProfileRepository.findById(ppid).orElseThrow(() -> new ResourceNotFoundException("Resource Not found"));
		return pp.getLike();
	}

	@Override
	public String deleteProfileFeed(int id) {
		playerProfileRepository.deleteById(id);
		return "Feed Deleted";
	}

	@Override
	public PlayerProfile updateProfileFeed(PlayerProfile pp) throws ResourceNotFoundException {
		PlayerProfile existance = playerProfileRepository.findById(pp.getPpid())
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not found"));
		if (pp.getDescription() != null) {
			existance.setDescription(pp.getDescription());
		} else {
			existance.setDescription(existance.getDescription());
		}
		existance.setFeedurl(existance.getFeedurl());
		existance.setDislike(existance.getDislike());
		existance.setPpid(existance.getPpid());
		existance.setPid(existance.getPid());
		existance.setLike(existance.getLike());
		return playerProfileRepository.save(existance);
	}

	@Override
	public String feedLike(int id) throws ResourceNotFoundException {
		PlayerProfile pp = playerProfileRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not found"));
		pp.setLike(pp.getLike() + 1);
		pp.setDescription(pp.getDescription());
		pp.setFeedurl(pp.getFeedurl());
		pp.setDislike(pp.getDislike());
		pp.setPpid(pp.getPpid());
		pp.setPid(pp.getPid());
		playerProfileRepository.save(pp);
		return "Liked";
	}

	@Override
	public String feedDislike(int id) throws ResourceNotFoundException {
		PlayerProfile pp = playerProfileRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not found"));
		pp.setLike(pp.getLike());
		pp.setDescription(pp.getDescription());
		pp.setFeedurl(pp.getFeedurl());
		pp.setDislike(pp.getDislike() + 1);
		pp.setPpid(pp.getPpid());
		pp.setPid(pp.getPid());
		playerProfileRepository.save(pp);
		return "Disliked";
	}

	@Override
	public FeedComment addComment(FeedComment comment) {
		return feedCommentRepository.save(comment);
	}

	@Override
	public List<FeedComment> getAllCommentByProfileFeedId(int id) {
		return feedCommentRepository.findByPpid(id);
	}

	@Override
	public List<FeedComment> getAllFeedComment() {
		return feedCommentRepository.findAll();
	}

	@Override
	public FeedComment updateFeedComment(FeedComment comment) throws ResourceNotFoundException {
		FeedComment fc = feedCommentRepository.findById(comment.getCid())
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not found"));
		if (comment.getComment() != null) {
			fc.setComment(comment.getComment());
		} else {
			fc.setComment(fc.getComment());
		}
		if (comment.getDate() != null) {
			fc.setDate(comment.getDate());
		} else {
			fc.setDate(fc.getDate());
		}
		fc.setCid(fc.getCid());
		fc.setPpid(fc.getPpid());
		fc.setUserid(fc.getUserid());
		fc.setUsername(fc.getUsername());
		return feedCommentRepository.save(fc);
	}

	@Override
	public String deleteFeedComment(int id) {
		feedCommentRepository.deleteById(id);
		return "Comment Deleted";
	}

	@Override
	public ProfileComment addProfileComment(ProfileComment comment) {
		return profileCommentRepository.save(comment);
	}

	@Override
	public List<ProfileComment> getAllCommentByPlayerId(int id) {
		return profileCommentRepository.findByPid(id);
	}

	@Override
	public ProfileComment updateProfileComment(FeedComment comment) throws ResourceNotFoundException {
		ProfileComment fc = profileCommentRepository.findById(comment.getCid())
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not found"));
		if (comment.getComment() != null) {
			fc.setComment(comment.getComment());
		} else {
			fc.setComment(fc.getComment());
		}
		if (comment.getDate() != null) {
			fc.setDate(comment.getDate());
		} else {
			fc.setDate(fc.getDate());
		}
		fc.setCid(fc.getCid());
		fc.setPid(fc.getPid());
		fc.setUserid(fc.getUserid());
		fc.setUsername(fc.getUsername());
		return profileCommentRepository.save(fc);
	}

	@Override
	public String deleteProfileComment(int id) {
		profileCommentRepository.deleteById(id);
		return "Comment Deleted";
	}

	@Override
	public List<Professionals> viewProfessionals(int id) throws ResourceNotFoundException {
		Events e = eventsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource Not found"));
		List<Professionals> pro = new ArrayList<Professionals>();
		List<BookingEvents> be = bookingEventsRepository.findAllByEid(e.getEid());
		for (BookingEvents i : be) {
			List<Professionals> pi = professionalsRepository.findAllByEmail(i.getMail());
			for (Professionals j : pi) {
				pro.add(j);
			}
		}
		return pro;
	}

	@Override
	public List<Professionals> viewProfessionals(String eventtype) {
		Events e = eventsRepository.findByEventtype(eventtype);
		List<Professionals> pro = new ArrayList<Professionals>();
		List<BookingEvents> be = bookingEventsRepository.findAllByEid(e.getEid());
		for (BookingEvents i : be) {
			List<Professionals> pi = professionalsRepository.findAllByEmail(i.getMail());
			for (Professionals j : pi) {
				pro.add(j);
			}
		}
		return pro;
	}

	@Override
	public PlayerProfile updateLike(String type, int ppid, String userid) throws ResourceNotFoundException {
		PlayerProfile existence = playerProfileRepository.findById(ppid).orElseThrow(() -> new ResourceNotFoundException("resource not found"));
		Likes like = likesRepository.findByPpidAndUserId(ppid, userid);
		if (like != null) {
			if (like.getType().equals("liked") && type.equals("liked")) {
				existence.setLike(existence.getLike() - 1);
				likesRepository.delete(like);
			} 
			else if (like.getType().equals("liked") && type.equals("disliked")) {
				existence.setDislike(existence.getDislike() + 1);
				existence.setLike(existence.getLike() - 1);
				like.setType(type);
				likesRepository.save(like);
			} 
			else if (like.getType().equals("disliked") && type.equals("disliked")) {
				existence.setDislike(existence.getDislike() - 1);
				likesRepository.delete(like);
			} 
			else {
				existence.setDislike(existence.getDislike() - 1);
				existence.setLike(existence.getLike() + 1);
				like.setType(type);
				likesRepository.save(like);
			}
		} 
		else {
			Likes liked = new Likes();
			if (type.equals("liked")) {
				existence.setLike(existence.getLike() + 1);
				liked.setPpid(ppid);
				liked.setType(type);
				liked.setUserId(userid);
				likesRepository.save(liked);
			}
			if (type.equals("disliked")) {
				existence.setDislike(existence.getDislike() + 1);
				liked.setPpid(ppid);
				liked.setType(type);
				liked.setUserId(userid);
				likesRepository.save(liked);
			}
		}
		return playerProfileRepository.save(existence);
	}

	// Image Uploaded
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
			this.s3Client.putObject(new PutObjectRequest(bucketName + "/" + name, image.getOriginalFilename(),
					image.getInputStream(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
		}
	}

}
