package com.Logan50miles.Controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.Referral;
import com.Logan50miles.Entity.ReferralPlan;
import com.Logan50miles.Entity.ReferralSubscription;
import com.Logan50miles.Repository.ReferralSubscriptionRepository;
import com.Logan50miles.Service.ReferralService;

@RestController
@RequestMapping("/referral")
public class ReferralController {

	@Autowired
	private ReferralService referralService;

	@Autowired
	ReferralSubscriptionRepository referralSubscriptionRepository;
	
	/*@Autowired
	public void setReferralService(ReferralService referralService) {
	this.referralService=referralService;	
	}*/
	
	@PostMapping("add/referralplan")
	public ReferralPlan addRefPlan(ReferralPlan referralPlan, MultipartFile file) throws IOException {
		return referralService.addRefPlan(referralPlan, file);
	}

	@GetMapping("get/referralplan") 
	public List<ReferralPlan> getReferralPlan() {
		return referralService.getReferralPlan();
	}

	@PutMapping("update/Plan")
	public String updatePlan(ReferralPlan referralPlan, MultipartFile file) {
		return referralService.updatePlan(referralPlan, file);
	}

	@PostMapping("add/ReferralSubcription")
	public ReferralSubscription addReferralSubcription(ReferralSubscription referralSubscription) {
		return referralService.addReferralSubcription(referralSubscription);
	}
	
	@GetMapping("get/List")
	public List<ReferralSubscription> getList() {
		return referralService.getList();
	}

	@DeleteMapping("delete/Plan")
	public String deleteByPlanId(int planID) {
		return referralService.deleteByPlanId(planID);
	}
	
	@GetMapping("get/referral") 
	public List<Referral> getRef(){
		return referralService.getRef();
	}

	@GetMapping("get/user/referral") 
	public Referral getReferalAmt(String cusId) {
		return referralService.getReferalAmt(cusId);
	}
	@GetMapping("get/user/referralsub") 
	public ReferralSubscription getSubcription(String cusId) { 
		return referralService.getSubcription(cusId); 
	} 
	
	@PostMapping("updatesubcription") 
	public String updateSubscription(String plan, String cusId, String pstatus) throws ParseException{ 
		return referralService.updateSubscription(plan, cusId, pstatus); 
	} 

}
