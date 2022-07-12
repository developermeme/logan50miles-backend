package com.Logan50miles.Implementation;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.Orders;
import com.Logan50miles.Entity.ShopAdmin;
import com.Logan50miles.Entity.SliderMobile;
import com.Logan50miles.Entity.Sliders;
import com.Logan50miles.Entity.StateTax;
import com.Logan50miles.Entity.VendorOtp;
import com.Logan50miles.Entity.VendorTax;
import com.Logan50miles.Repository.OrderRepository;
import com.Logan50miles.Repository.ShopAdminRepository;
import com.Logan50miles.Repository.SliderMobileRepository;
import com.Logan50miles.Repository.SlidersRepository;
import com.Logan50miles.Repository.StateTaxRepository;
import com.Logan50miles.Repository.VendorOtpRepository;
import com.Logan50miles.Repository.VendorTaxRepository;
import com.Logan50miles.Service.ShopAdminService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class ShopAdminImpl implements ShopAdminService{
	
	@Autowired
	private ShopAdminRepository shopAdminRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private SlidersRepository slidersRepository;
	@Autowired
	private SliderMobileRepository sliderMobileRepository;
	@Autowired
	private VendorOtpRepository vendorOtpRepository;
	@Autowired
	private VendorTaxRepository vendorTaxRepository;
	@Autowired
	private StateTaxRepository stateTaxRepository;
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
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

	private String db2="https://mylogantown.s3.amazonaws.com/event/";
	private String db1="https://mylogantown.s3.amazonaws.com/event/";
	private String db3="https://mylogantown.s3.amazonaws.com/event/";
	
	@Override
	public ResponseEntity<String> shopLogin(String shopName,String pwd){
		ResponseEntity<String> result;
		ShopAdmin existence=shopAdminRepository.findByShopName(shopName);
		if(existence!=null) {
			if(encoder.matches(pwd, existence.getPassword())) {
				result = ResponseEntity.status(HttpStatus.OK).body("SHOP-ADMIN!");
			}
			else {
				result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password. Try again.");
			}
		}
			else {
				result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are Not Shop Admin");
			}
		return result;
	}
	@Override
	public  ResponseEntity<String> shopRegisteration(ShopAdmin shopAdmin,MultipartFile file) throws IOException{
		ResponseEntity<String> result;
		ShopAdmin existence=shopAdminRepository.findByShopName(shopAdmin.getShopName());
		if(existence!=null) {
			result=ResponseEntity.status(HttpStatus.CONFLICT).body("Already Registered");
		}
		else {
			if(file!=null) {
				this.uploadImageProfile(file,"event");
				shopAdmin.setVendorUrl(db3+file.getOriginalFilename());
			}else {
				shopAdmin.setVendorUrl(db3+"success.png");
			}
			shopAdmin.setPassword(encoder.encode(shopAdmin.getPassword()));
			shopAdmin.setStatus(Boolean.FALSE);
			shopAdminRepository.save(shopAdmin);
			result=ResponseEntity.status(HttpStatus.CREATED).body("registered");
		}
		return result;
	}
	@Override
	public String forgorPassword(String shopName) {
		ShopAdmin existence=shopAdminRepository.findByShopName(shopName);
		String result=null;
		if(existence!=null) {
			VendorOtp confirmationToken = new VendorOtp();
			confirmationToken.setShopName(shopName);
			vendorOtpRepository.save(confirmationToken);
			result=confirmationToken.getOtp();
		}else {
			result="User Not Available";
		}
		return result;
	}
	@Override
	public String updatePassword(String shopName,String password) {
		ShopAdmin existence=shopAdminRepository.findByShopName(shopName);
		existence.setPassword(encoder.encode(password));
		existence.setCity(existence.getCity());
		existence.setCountry(existence.getCountry());
		existence.setShopId(existence.getShopId());
		existence.setShopName(shopName);
		existence.setState(existence.getState());
		existence.setStatus(existence.getStatus());
		existence.setVendorName(existence.getVendorName());
		existence.setVendorUrl(existence.getVendorUrl());
		shopAdminRepository.save(existence);
		return "updated succesfully";
	}
	@Override
	public String updateVendor(ShopAdmin existence1) {
		ShopAdmin existence=shopAdminRepository.findByShopName(existence1.getShopName());
		existence.setPassword(existence1.getPassword());
		existence.setCity(existence1.getCity());
		existence.setCountry(existence1.getCountry());
		existence.setShopId(existence1.getShopId());
		existence.setShopName(existence1.getShopName());
		existence.setState(existence1.getState());
		existence.setStatus(existence1.getStatus());
		existence.setVendorName(existence1.getVendorName());
		existence.setVendorUrl(existence1.getVendorUrl());
		shopAdminRepository.save(existence);
		return "updated succesfully";
	}
	@Override
	public ResponseEntity <String> confirmOtp(@RequestParam String confirmationToken){
		ResponseEntity<String> result;
		VendorOtp token = vendorOtpRepository.findByOtp(confirmationToken);
		if(token!=null) {
		    
			result=ResponseEntity.status(HttpStatus.ACCEPTED).body("USER");
		}
		else {
			result=ResponseEntity.status(HttpStatus.CONFLICT).body("Incorrect Otp");
		}
		return result;
	}
	@Override
	public List<Orders> listOrdersByShop(String shopId){
		return orderRepository.findByShopId(shopId);
	}
	@Override
	public List<Orders> getStatics(String id, String from, String to) throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Date fd = formatter.parse(from);
		Date td = formatter.parse(to);

		return orderRepository.findByDates(fd, td, id);
	}
	@Override
	public List<Orders> getStaticsWhole(String from, String to) throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Date fd = formatter.parse(from);
		Date td = formatter.parse(to);

		return orderRepository.findByDates(fd, td);
	}
	@Override
	public Sliders addSlider(Sliders sliders,MultipartFile file) throws IOException {
		this.uploadImageProfile(file,"event");
		sliders.setBannerUrl(db2+file.getOriginalFilename());
		return slidersRepository.save(sliders);
	}
	@Override
	public Sliders updateSlider(Sliders sliders,MultipartFile file) throws IOException {
		Sliders ex=slidersRepository.findById(sliders.getBanId()).orElse(null);
		if(file!=null) {
			this.uploadImageProfile(file,"event");
			ex.setBannerUrl(db2+file.getOriginalFilename());
		}
		ex.setDate(sliders.getDate());
		ex.setDescription(sliders.getDescription());
		ex.setKeyword(sliders.getKeyword());
		return slidersRepository.save(ex);
	}
	@Override
	public List<Sliders> getBanners(){
		return slidersRepository.findAll();
	}
	@Override
	public String deleteFile(int id) {
		Sliders sliders=slidersRepository.findByBannerId(id);
        slidersRepository.delete(sliders);
        return "deleted";
	}
	@Override
	public SliderMobile addSliders(SliderMobile sliderMobile,MultipartFile file) throws IOException {
		this.uploadImageProfile(file,"event");
		sliderMobile.setBannerUrl(db1+file.getOriginalFilename());
		return sliderMobileRepository.save(sliderMobile);
	}
	@Override
	public List<SliderMobile> getSlidersmobile(){
		return sliderMobileRepository.findAll();
	}
	@Override
	public String deleteMobileSlide(int id) {
		SliderMobile sliderMobile=sliderMobileRepository.findBybanId(id);
		        sliderMobileRepository.delete(sliderMobile);  
		return "deleted";
	}
	@PostConstruct
	private void initAmazon() {
	    BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
	    this.s3Client = AmazonS3ClientBuilder.standard().withRegion(region)
	            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
	}
	public void uploadImageProfile(MultipartFile image,String name) throws IOException {
		 
	    if (!image.isEmpty()) {
	      
	            ObjectMetadata objectMetadata = new ObjectMetadata();
	            objectMetadata.setContentType(image.getContentType());

	            this.s3Client.putObject(new PutObjectRequest(bucketName+"/"+name, image.getOriginalFilename(),
	                    image.getInputStream(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));

	    }
	}
	@Override
	public List<ShopAdmin> getVendors(){
		return shopAdminRepository.findAll();
	}
	@Override
	public String shopUpdateStatus(int shopId, String pstatus) {
		ShopAdmin shopAdmin = shopAdminRepository.findByShopId(shopId);
		if (pstatus.equals("success")) {
			shopAdmin.setStatus(Boolean.TRUE);
		} else {
			shopAdmin.setStatus(Boolean.FALSE);
		}
		shopAdminRepository.save(shopAdmin);
		return "updated";
	}
	@Override
    public VendorTax addTax(VendorTax vendorTax) {
    	return vendorTaxRepository.save(vendorTax);
    }
	@Override
    public StateTax addStateTax(StateTax stateTax) {
    	return stateTaxRepository.save(stateTax);
    }
	@Override
    public List<VendorTax> getVendorTaxs(){
    	return vendorTaxRepository.findAll();
    }
	@Override
    public VendorTax getTax(String country) {
    	return vendorTaxRepository.findByCusId(country);
    }
	@Override
    public String updateTax(int id,StateTax stateTax) {
    	StateTax st=stateTaxRepository.findByTaxId(id);
    	st.setTid(st.getTid());
    	st.setVid(st.getVid());
    	st.setPercentage(stateTax.getPercentage());
    	st.setStateName(stateTax.getStateName());
    	stateTaxRepository.save(st);
    	return "updated";
    }
	@Override
	public String deleteTax(int id) {
		StateTax st=stateTaxRepository.findByTaxId(id);
		stateTaxRepository.delete(st);
		return "deleted";
	}
}
