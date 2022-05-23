package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.ReferralPlan;

public interface ReferralPlanRepository extends JpaRepository<ReferralPlan, Integer>{

	ReferralPlan findByPlanID(int planId);

}
