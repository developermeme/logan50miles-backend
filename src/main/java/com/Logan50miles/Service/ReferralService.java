package com.Logan50miles.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.Referral;
import com.Logan50miles.Entity.ReferralPlan;
import com.Logan50miles.Entity.ReferralSubscription;


public interface ReferralService {

	ReferralPlan addRefPlan(ReferralPlan referralPlan, MultipartFile file) throws IOException;

	List<ReferralPlan> getReferralPlan();

	String updatePlan(int planId, ReferralPlan referralPlan, MultipartFile file);

	ReferralSubscription addReferralSubcription(ReferralSubscription referralSubscription);

	List<ReferralSubscription> getList();

	String deleteByPlanId(int planID);

	List<Referral> getRef();

	Referral getReferalAmt(String cusId);

	ReferralSubscription getSubcription(String cusId);

	String updateSubscription(String plan, String cusId, String pstatus) throws ParseException;

}
