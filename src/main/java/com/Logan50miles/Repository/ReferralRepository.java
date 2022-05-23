package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.Referral;

public interface ReferralRepository extends JpaRepository<Referral, Integer>{

	Referral findByRefCod(String refCod);

	Referral findByCusId(String cusId);

}
