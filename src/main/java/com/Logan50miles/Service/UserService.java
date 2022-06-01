package com.Logan50miles.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.Players;
import com.Logan50miles.Entity.User;
import com.Logan50miles.Util.ResourceNotFoundException;

public interface UserService {

	ResponseEntity<String> userLogin(String email, String password) throws ParseException;

	ResponseEntity<String> userRegistration(User user, MultipartFile file) throws IOException, ParseException;

	List<User> getAllUser();

	User getUserById(int userId);

	User getUserByEmailId(String emailId);

	String updateUser(User user, MultipartFile file) throws IOException;

	String deleteUser(int id);

	ResponseEntity<String> confirmRegistration(String confirmationToken);

	ResponseEntity<String> forgotPassword(String email) throws ParseException;

	ResponseEntity<String> updatePassword(String email, String password) throws ParseException;

	Players addPlayers(Players p, MultipartFile pimg) throws IOException;

	Players updatePlayers(Players p, MultipartFile pimg) throws IOException, ResourceNotFoundException;

	String deletePlayers(int id);

	List<Players> getAllPlayers();

	Players getPlayerbyId(int id) throws ResourceNotFoundException;

}
