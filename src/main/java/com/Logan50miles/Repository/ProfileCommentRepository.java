package com.Logan50miles.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.ProfileComment;

public interface ProfileCommentRepository extends JpaRepository<ProfileComment, Integer> {

	List<ProfileComment> findByPid(int id);

}
