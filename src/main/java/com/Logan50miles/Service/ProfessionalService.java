package com.Logan50miles.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.FeedComment;
import com.Logan50miles.Entity.Likes;
import com.Logan50miles.Entity.PlayerProfile;
import com.Logan50miles.Entity.Professionals;
import com.Logan50miles.Entity.ProfileComment;
import com.Logan50miles.Util.ResourceNotFoundException;

public interface ProfessionalService {

	Professionals getPlayerByEmailId(String emailId);

	List<Professionals> getAllPlayers();

	ResponseEntity<String> registerationPlayer(Professionals playerdetails, MultipartFile file, MultipartFile file1, MultipartFile file2)throws IOException;

	ResponseEntity<String> loginPlayer(String emailId, String password);

	String deletePlayer(int id);

	ResponseEntity<String> resetPlayerPassword(String emailId, String password) throws ParseException;

	ResponseEntity<String> forgotPassword(String emailId) throws ParseException;

	ResponseEntity<String> confirmOtpVerification(String otp);

	String updatePlayer(Professionals playerdetails, MultipartFile file, MultipartFile file1, MultipartFile file2) throws ResourceNotFoundException, IOException;

	PlayerProfile addProfileFeed(PlayerProfile pp, MultipartFile feed) throws IOException;

	List<PlayerProfile> getAllProfileFeed();

	List<PlayerProfile> getAllProfileFeedByPlayerId(int pid);

	PlayerProfile getProfileFeedByFeedId(int ppid) throws ResourceNotFoundException;

	String deleteProfileFeed(int id);

	PlayerProfile updateProfileFeed(PlayerProfile pp) throws ResourceNotFoundException;

	String feedLike(int id) throws ResourceNotFoundException;

	String feedDislike(int id) throws ResourceNotFoundException;

	FeedComment addComment(FeedComment comment);

	List<FeedComment> getAllCommentByProfileFeedId(int id);

	FeedComment updateFeedComment(FeedComment comment) throws ResourceNotFoundException;

	String deleteFeedComment(int id);

	ProfileComment addProfileComment(ProfileComment comment);

	List<ProfileComment> getAllCommentByPlayerId(int id);

	ProfileComment updateProfileComment(FeedComment comment) throws ResourceNotFoundException;

	String deleteProfileComment(int id);

	List<Professionals> viewProfessionals(int id) throws ResourceNotFoundException;

	List<Professionals> viewProfessionals(String eventtype);

	List<Professionals> getProfessionalsByEventLevel(String eventlevel);

	String approvePlayer(int id, String status) throws ResourceNotFoundException;

	List<FeedComment> getAllFeedComment();

	PlayerProfile updateLike(String type, int ppid, String userid) throws ResourceNotFoundException;

	int getLikebyFeedId(int ppid) throws ResourceNotFoundException;

	List<Likes> getAllLikes();

	List<Likes> getLikesbyFeedId(int ppid);

	Likes getLikesbyFeedIdandUserId(int ppid, String userid);

	
}
