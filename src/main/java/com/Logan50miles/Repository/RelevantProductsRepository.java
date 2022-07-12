package com.Logan50miles.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.RelevantProducts;

public interface RelevantProductsRepository extends JpaRepository<RelevantProducts, Integer> {
    List<RelevantProducts> findByMcId(int mcId);
}
