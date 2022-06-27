package com.Logan50miles.Controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.FeedComment;
import com.Logan50miles.Entity.PlayerProfile;
import com.Logan50miles.Entity.Professionals;
import com.Logan50miles.Entity.ProfileComment;
import com.Logan50miles.Service.ProfessionalService;
import com.Logan50miles.Util.ResourceNotFoundException;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("Players")
public class ProfessionalController {

	@Autowired
	private ProfessionalService professionalService;
	
	@GetMapping("get/Player/byEmailid")
	public Professionals getPlayerByEmailId(String emailId) {
		return professionalService.getPlayerByEmailId(emailId);
	}

	@GetMapping("get/all/Player")
	public List<Professionals> getAllPlayers(){
		return professionalService.getAllPlayers();
	}

	@PostMapping("Player/registeration")
	public ResponseEntity<String> registerationPlayer(Professionals playerdetails, MultipartFile file)throws IOException {
		return professionalService.registerationPlayer(playerdetails, file);
	}

	@PostMapping("Player/login")
	public ResponseEntity<String> loginPlayer(String emailId, String password){
		return professionalService.loginPlayer(emailId, password);
	}

	@DeleteMapping("delete/player/byID")
	public String deletePlayer(int id) {
		return professionalService.deletePlayer(id);
	}

	@PostMapping("reset/admin/password")
	public ResponseEntity<String> resetPlayerPassword(String emailId, String password) throws ParseException {
		return professionalService.resetPlayerPassword(emailId, password);
	}

	@PostMapping("forgot/password/admin")
	public ResponseEntity<String> forgotPassword(String emailId) throws ParseException {
		return professionalService.forgotPassword(emailId);
	}

	@PostMapping("confirm/OTP/verification")
	public ResponseEntity<String> confirmOtpVerification(String otp) {
		return professionalService.confirmOtpVerification(otp);
	}

	@PutMapping("update/player")
	public String updatePlayer(Professionals playerdetails, MultipartFile file) throws ResourceNotFoundException, IOException {
		return professionalService.updatePlayer(playerdetails, file);
	}

	@PostMapping("add/profile/feed")
	public PlayerProfile addProfileFeed(PlayerProfile pp, MultipartFile feed) throws IOException {
		return professionalService.addProfileFeed(pp, feed);
	}

	@GetMapping("get/all/feeds")
	public List<PlayerProfile> getAllProfileFeed() {
		return professionalService.getAllProfileFeed();
	}

	@GetMapping("get/all/feeds/byPlayerId")
	public List<PlayerProfile> getAllProfileFeedByPlayerId(int pid) {
		return professionalService.getAllProfileFeedByPlayerId(pid);
	}

	@GetMapping("get/feed/byId")
	public PlayerProfile getProfileFeedByFeedId(int ppid) throws ResourceNotFoundException {
		return professionalService.getProfileFeedByFeedId(ppid);
	}

	@DeleteMapping("delete/profile/feed")
	public String deleteProfileFeed(int id) {
		return professionalService.deleteProfileFeed(id);
	}

	@PutMapping("update/profile/feed")
	public PlayerProfile updateProfileFeed(PlayerProfile pp) throws ResourceNotFoundException {
		return professionalService.updateProfileFeed(pp);
	}

	@PostMapping("profile/feed/like")
	public String feedLike(int ppid) throws ResourceNotFoundException {
		return professionalService.feedLike(ppid);
	}
	
	@PostMapping("profile/feed/dislike")
	public String feedDislike(int id) throws ResourceNotFoundException{
		return professionalService.feedDislike(id);
	}

	@PostMapping("add/feed/comment")
	public FeedComment addComment(FeedComment comment) {
		return professionalService.addComment(comment);
	}

	@GetMapping("get/all/comment/feed")
	public List<FeedComment> getAllCommentByProfileFeedId(int id){
		return professionalService.getAllCommentByProfileFeedId(id);
	}

	@PutMapping("update/feed/comment")
	public FeedComment updateFeedComment(FeedComment comment) throws ResourceNotFoundException{
		return professionalService.updateFeedComment(comment);
	}

	@DeleteMapping("delete/feed/comment")
	public String deleteFeedComment(int id) {
		return professionalService.deleteFeedComment(id);
	}

	@PostMapping("add/profile/comment")
	public ProfileComment addProfileComment(ProfileComment comment) {
		return professionalService.addProfileComment(comment);
	}

	@GetMapping("get/all/comment/profile")
	public List<ProfileComment> getAllCommentByPlayerId(int id){
		return professionalService.getAllCommentByPlayerId(id);
	}

	@PutMapping("update/profile/comment")
	public ProfileComment updateProfileComment(FeedComment comment) throws ResourceNotFoundException{
		return professionalService.updateProfileComment(comment);
	}

	@DeleteMapping("delete/profile/comment")
	public String deleteProfileComment(int id) {
		return professionalService.deleteProfileComment(id);
	}
	
	@GetMapping("view/professionals")
	public List<Professionals> viewProfessionals() {
		return professionalService.viewProfessionals();
	}
	
}
