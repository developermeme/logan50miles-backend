package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.ReferralSubscription;

public interface ReferralSubscriptionRepository extends JpaRepository<ReferralSubscription, Integer> {

	ReferralSubscription findByCusId(String phone);

}
