package com.Logan50miles.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.FeedComment;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Integer>{

	List<FeedComment> findByPpid(int id);

}
