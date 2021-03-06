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
import com.Logan50miles.Entity.Mailconfiguration;
import com.Logan50miles.Repository.BookingEventsRepository;
import com.Logan50miles.Repository.EventsRepository;
import com.Logan50miles.Repository.MailConfigurationRepository;
import com.Logan50miles.Service.EventService;
import com.Logan50miles.Util.Mailer;
import com.Logan50miles.Util.QRCodeGenerator;
import com.Logan50miles.Util.ResourceNotFoundException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.zxing.WriterException;

@Service
public class EventImplementation implements EventService{

	@Autowired
	private EventsRepository eventsRepository;
	@Autowired
	private BookingEventsRepository bookingEventsRepository;
	@Autowired
	private MailConfigurationRepository mailConfigurationRepository;
	
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
		events.setAvailableplayers(events.getNoofplayers());
		events.setParticipatedplayers(0);
		return eventsRepository.save(events);
	}
	
	@Override
	public Events updateEvents(Events events, MultipartFile file) throws IOException, ResourceNotFoundException {
		Events ex = eventsRepository.findById(events.getEid()).orElseThrow(()-> new ResourceNotFoundException("Resource Not found"));
		if(file!=null) {
			this.uploadImage(file,"event");
			ex.setUrl(image+file.getOriginalFilename());
		}
		else {
			ex.setUrl(ex.getUrl());
		}
		if(events.getTitle()!=null) {
			ex.setTitle(events.getTitle());
		}
		else {
			ex.setTitle(ex.getTitle());
		}
		if(events.getDate()!=null) {
			ex.setDate(events.getDate());
		}
		else {
			ex.setDate(ex.getDate());
		}
		if(events.getDetails()!=null) {
			ex.setDetails(events.getDetails());
		}
		else {
			ex.setDetails(ex.getDetails());
		}
		if(events.getEntry()!=0) {
			ex.setEntry(events.getEntry());
		}
		else {
			ex.setEntry(ex.getEntry());
		}
		if(events.getLocation()!=null) {
			ex.setLocation(events.getLocation());
		}
		else {
			ex.setLocation(ex.getLocation());
		}
		if(events.getTime()!=null) {
			ex.setTime(events.getTime());
		}
		else {
			ex.setTime(ex.getTime());
		}
		if(events.getEventtype()!=null) {
			ex.setEventtype(events.getEventtype());
		}
		else {
			ex.setEventtype(ex.getEventtype());
		}
		if(events.getNoofplayers()!=0) {
			ex.setNoofplayers(events.getNoofplayers());
			ex.setAvailableplayers(ex.getNoofplayers()-ex.getParticipatedplayers());

		}
		else {
			ex.setNoofplayers(ex.getNoofplayers());
		}
		
		ex.setParticipatedplayers(ex.getParticipatedplayers());	
		ex.setEid(ex.getEid());
		ex.setStatus(ex.isStatus());
		return eventsRepository.save(ex);
		
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
	public BookingEvents addBookingEvents(BookingEvents bookingEvents, MultipartFile file, MultipartFile file1) throws IOException, ResourceNotFoundException, WriterException{
		if(file!=null) {
			this.uploadImage(file, "event");
			bookingEvents.setIdurl(image+file.getOriginalFilename());
		}
		if(file1!=null) {
			this.uploadImage(file1, "event");
			bookingEvents.setVideourl(image+file1.getOriginalFilename());	
		}
		bookingEvents.setRegistrationnumber(createCode());	
		Events e = eventsRepository.findById(bookingEvents.getEid()).orElseThrow(()-> new ResourceNotFoundException("Resource Not found"));
		e.setAvailableplayers(e.getAvailableplayers()-1);
		e.setParticipatedplayers(e.getParticipatedplayers()+1);
		eventsRepository.save(e);
		image = QRCodeGenerator.getQRCodeImage(String.valueOf(bookingEvents.getRegistrationnumber()),250,250);
		String text = "<h4><u>Hi "+bookingEvents.getName()+"</u></h4>"+
				"<h4>"+bookingEvents.getTitle()+" Event has Registered Successfully. </h4>>"+
				"<div style=\"border:1px solid black; padding:10px ; text-align:center;\">"+
				"<h3><u> Event Information<u></h3>"+
				image+
				"<table border='0' align='center'>"+
				"<tr></tr>"+
				"<tr><td><h5>Event Registration No </h5></td> <td><h5> : "+bookingEvents.getRegistrationnumber()+"</h5></td></tr>"+
				"<tr><td><h5>Event Name </h5></td> <td><h5> : "+bookingEvents.getDetails()+"</h5></td></tr>"+
				"<tr><td><h5>Event Date </h5></td> <td><h5> : "+bookingEvents.getDate()+"</h5></td></tr>"+
				"</table></div>";
		Mailconfiguration m = mailConfigurationRepository.findAll().stream().filter(x -> x.getType().equals("general"))
				.findAny().orElse(null);		
		Mailer mail = new Mailer();
    	mail.sendMail("LOGAN50MILES PLAYER APPROVAL", text , bookingEvents.getMail(), m.getEmail(), m.getPassword());   	
		
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
    
    @Override
    public String updatePayment(int id, String tid) throws ResourceNotFoundException {
    	BookingEvents b = bookingEventsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource Not found"));
    	b.setTnxid(tid); 
    	b.setStatus("Paid");
    	bookingEventsRepository.save(b);
    	return "Payment Updated";
    }
}
