package com.cychess.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cychess.model.Role;
import com.cychess.model.User;
import com.cychess.repository.RoleRepository;
import com.cychess.repository.UserRepository;

/**
 * Implementation of the UserService class used for logging in 
 * @author bbanothu
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    /**
     * Finds user from email address
     */
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	
	/**
	 * Creates a user from phone login
	 */
	@Override
	public void createUser(String email, String name, String username, String password) {
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setUserName(username);
		user.setPassword(bCryptPasswordEncoder.encode(password));
		user.setActive(1);
		user.setToken(generateToken());
		Role userRole = roleRepository.findByRole("USER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	/**
	 * Saves a user's password info for registration.
	 */
	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
		user.setToken(generateToken());
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	/**
	 * Checks whether the user's password is correct when logging in.
	 */
	@Override
	public boolean comparePassword(User user, String password) {
		if( bCryptPasswordEncoder.matches(password, user.getPassword()) ) {
			return true;
		}
		return false;
	}
	
	/**
	 * Generates the token for the user to allow the user to open the app without having to log back in.
	 */
	@Override
	public String generateToken() {
		StringBuffer randString = new StringBuffer();
		String charList = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*";
		System.out.println(charList.length()-1);
		int length = 20;
		
		Random r = new Random();
		for(int i = 0; i < length; i++) {
			int num = r.nextInt(charList.length()-1);
			char c = charList.charAt(num);
			randString.append(c);
		}
		return randString.toString();
	}
}
