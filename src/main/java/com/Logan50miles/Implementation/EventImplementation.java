package com.Logan50miles.Implementation;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.BookingEvents;
import com.Logan50miles.Entity.Events;
import com.Logan50miles.Repository.BookingEventsRepository;
import com.Logan50miles.Repository.EventsRepository;
import com.Logan50miles.Service.EventService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.Logan50miles.Util.ResourceNotFoundException;

@Service
public class EventImplementation implements EventService{

	@Autowired
	private EventsRepository eventsRepository;
	@Autowired
	private BookingEventsRepository bookingEventsRepository;
	
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

    
	
	@Override
	public List<Events> getEvents() {
		return eventsRepository.findAll();
	}
	
	@Override
	public Events addEvents(Events events, MultipartFile file) throws IOException {
		if(file!=null) {
			this.uploadImage(file,"event");
			events.setUrl(image+file.getOriginalFilename());
		}
		return eventsRepository.save(events);
	}
	
	@Override
	public Events getEvent(int id) throws ResourceNotFoundException {
		return eventsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource Not found"));
	}
	
	@Override
	public Events updateStatus(int id) throws ResourceNotFoundException {
		Events e = eventsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource Not found"));
		if(e.isStatus()==Boolean.TRUE) {
			e.setStatus(Boolean.FALSE);
		}
		else {
			e.setStatus(Boolean.TRUE);
		}
		return eventsRepository.save(e);
	}
	
	@Override
	public String deleteEvents(int id){
		eventsRepository.deleteById(id);
		return "Event Deleted Successfully";
	}
	
	@Override
	public BookingEvents addBookingEvents(BookingEvents bookingEvents, MultipartFile file, MultipartFile file1) throws IOException{
		if(file!=null) {
			this.uploadImage(file, "event");
			bookingEvents.setIdurl(image+file.getOriginalFilename());
		}
		if(file1!=null) {
			this.uploadImage(file1, "event");
			bookingEvents.setVideourl(image+file1.getOriginalFilename());	
		}
		bookingEvents.setStatus("WAITING FOR APPROVAL");
		bookingEvents.setRegistrationnumber(createCode());		
		return bookingEventsRepository.save(bookingEvents);
	}
	
	@Override
	public List<BookingEvents> getBookings(){
		return bookingEventsRepository.findAll();
	}
	
	@Override
	public List<BookingEvents> getByPhone(String phone) {
		return bookingEventsRepository.findByMobile(phone);
	}
	
	@Override
	public String deleteBookingEvents(int id) {
		bookingEventsRepository.deleteById(id);
		return "Booking Events Deleted";
	}
	
	 public String createCode(){
	        int codeLength=4;
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
