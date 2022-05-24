package com.Logan50miles.Implementation;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.Referral;
import com.Logan50miles.Entity.ReferralPlan;
import com.Logan50miles.Entity.ReferralSubscription;
import com.Logan50miles.Repository.ReferralPlanRepository;
import com.Logan50miles.Repository.ReferralRepository;
import com.Logan50miles.Repository.ReferralSubscriptionRepository;
import com.Logan50miles.Service.ReferralService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class ReferralImplementation implements ReferralService {

	@Autowired
	private ReferralSubscriptionRepository referralSubscriptionRepository;
	@Autowired
	private ReferralPlanRepository referralPlanRepository;
	@Autowired
	private ReferralRepository referralRepository;
	
	private AmazonS3 s3Client;
	private String db="https://mylogantown.s3.amazonaws.com/event/";

	@Value("${app.awsServices.bucketName}")
	private String bucketName;
	
	
	public void uploadImage(MultipartFile image, String name) throws IOException {
		if (!image.isEmpty()) {
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(image.getContentType());
			this.s3Client.putObject(new PutObjectRequest(bucketName + "/" + name, image.getOriginalFilename(),
					image.getInputStream(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
		}

	}
	public String deleteS3Img(String keyName,String name) {
		final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName + "/" + name, keyName);
    	s3Client.deleteObject(deleteObjectRequest);
    	return "deleted";
	}
	
	@Override
	public ReferralPlan addRefPlan(ReferralPlan referralPlan, MultipartFile file) throws IOException {
		if (file != null){
			this.uploadImage(file, "referral");
			referralPlan.setImageUrl(db + file.getOriginalFilename());
			referralPlan.setImageUrl(file.getOriginalFilename());
		}else {
			referralPlan.setImageUrl(db + "default.jpg");
			referralPlan.setImageUrl(referralPlan.getImageUrl());
		}
		
		return referralPlanRepository.save(referralPlan);
	}
	@Override
	public List<ReferralPlan> getReferralPlan(){
		return referralPlanRepository.findAll();
	}
	@Override
	public String updatePlan(ReferralPlan referralPlan, MultipartFile file) {
		ReferralPlan rp=referralPlanRepository.findByPlanID(referralPlan.getPlanID());
		rp.setPlanID(referralPlan.getPlanID());
		rp.setPlanName(referralPlan.getPlanName());
		rp.setPlanAmount(referralPlan.getPlanAmount());
		rp.setValidty(referralPlan.getValidty());
		rp.setDiscounts(referralPlan.getDiscounts());
		referralPlanRepository.save(rp);
		return "updated";
	}
	@Override
	public List<Referral> getRef(){
		return referralRepository.findAll();
	}
	@Override
	public ReferralSubscription addReferralSubcription(ReferralSubscription referralSubscription) {
		
		return referralSubscriptionRepository.save(referralSubscription);
	}
	@Override
	public List<ReferralSubscription> getList(){
		return referralSubscriptionRepository.findAll();
	}
	@Override
	public ReferralSubscription getSubcription(String cusId) {
		return referralSubscriptionRepository.findByCusId(cusId);
	}
	@Override
	public String updateSubscription(String plan,String cusId,String pstatus) throws ParseException {
		ReferralSubscription rs=referralSubscriptionRepository.findByCusId(cusId);
		ReferralPlan ref = referralPlanRepository.findByPlanName(plan);
		LocalDate today = LocalDate.now().plusMonths(ref.getValidty());
		Date date=new SimpleDateFormat("yyyy-MM-dd").parse(today.toString());
		LocalDate today1 = LocalDate.now();
		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(today1.toString());
		if(pstatus.equals("success")) {
			rs.setPlan(plan);
			rs.setDate(date);
		}
		else {
			rs.setPlan(rs.getPlan());
			rs.setDate(date1);
		}
		rs.setStatus("ACTIVE");
		referralSubscriptionRepository.save(rs);
		return "updated";
	}
	public int addPlan(int planId) {
		double amt=referralPlanRepository.findByPlanID(planId).getPlanAmount();
		System.out.println(amt);
		int status=200;
	    return status;
	}
	@Override
	public String deleteByPlanId(int planID) {
		String img=referralPlanRepository.findByPlanID(planID).getImageUrl();
		deleteS3Img(img,"referral");
		referralPlanRepository.deleteById(planID);
		
		return "deleted";
	}
	
	@Scheduled(cron = "0 0 24 * * * *")
	public void referralPlanExpiryAlert() throws ParseException {
		List<ReferralSubscription> rs = referralSubscriptionRepository.findAll(Sort.by("date").descending());
		for (ReferralSubscription ref : rs){
			LocalDate currentTime = LocalDate.now();
			Date date=new SimpleDateFormat("yyyy-MM-dd").parse(currentTime.toString());
			if (date.after(ref.getDate())){
				ref.setPlan("SILVER");
			}
			referralSubscriptionRepository.save(ref);

		}
	}
	public void getRefCount(String refCod) {
		Referral referral=referralRepository.findByRefCod(refCod);
		referral.setRefCount(referral.getRefCount()+1);
	}
	
	@Override
	public Referral getReferalAmt(String cusId) {
		return referralRepository.findByCusId(cusId);
	}
}
