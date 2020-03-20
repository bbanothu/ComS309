package com.cychess.service;

import org.springframework.jdbc.core.JdbcTemplate;

import com.cychess.model.User;

/**
 * Inteface used to interact with User information
 * @author bbanothu
 *
 */
public interface UserService {
	
	/**
	 * Returns User from given email
	 * @param email User email
	 * @return User
	 */
	public User findUserByEmail(String email);
	
	/**
	 * Compares a given password to a new password
	 * @param user User
	 * @param password new password
	 * @return comparison results
	 */
	public boolean comparePassword(User user, String password);
	
	/**
	 * Saves a user's information at registration
	 * @param user
	 */
	public void saveUser(User user);
	
	/**
	 * Creates a new user
	 * @param email
	 * @param name
	 * @param username
	 * @param password
	 */
	public void createUser(String email, String name, String username, String password);
	
	/**
	 * Generates new token for user
	 * @return Token string
	 */
	public String generateToken();
}
