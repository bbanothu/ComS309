package com.cychess.controller;


import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cychess.ServerApplication;
import com.cychess.configuration.SecurityConfiguration;
import com.cychess.model.Role;
import com.cychess.model.User;
import com.cychess.model.UserDAO;
import com.cychess.service.UserService;
import com.cychess.sockets.*;

/**
 * The main controller for registering and logging into the server from web/client
 * @author bbanothu
 *
 * @param <LoginForm>	The type of form this process uses.
 */
@Controller
public class LoginController{
	
	@Autowired
	private UserService userService;
	@Autowired
    JdbcTemplate jdbcTemplate;
	

	


	@CrossOrigin
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	//Login for client
	/**
	 * Login for client
	 * @param email		User's email
	 * @param password	User's password
	 * @return			Whether or not login was successful
	 */
	@ResponseBody
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public String signin(@RequestParam("email") String email, @RequestParam("password") String password) {
		System.out.println("loggedin");
		User user = userService.findUserByEmail(email);
		//Email does exists && passwords match
		if(user != null && userService.comparePassword(user, password)) {
			String token = new UserDAO().getTokenAtEmail(email);


			
			return "SUCCESS " + token + " " + user.getUserName() + " " + new UserDAO().getUserRole(user.getId());
		}else if(user == null) {
			return "EMAIL FAILED";
		}else if(!userService.comparePassword(user, password)) {
			return "PASSWORD FAILED";
		}
		return "FAILED";
	}
	

	
	/**
	 * Registration page
	 * @return		Registration page view
	 */
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	
	//Registration for client
	/**
	 * Registration for client
	 * @param email		User's email
	 * @param name		User's name
	 * @param username	User's desired username
	 * @param password	User's desired password
	 * @return			Whether or not login was successful
	 */
	@ResponseBody
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@RequestParam("email") String email, @RequestParam("name") String name, @RequestParam("username") String username, @RequestParam("password") String password) {
		//User userEmail = userService.findUserByEmail(email);
		int userEmail = new UserDAO().checkIfEmailExists(email);
		int userExists = new UserDAO().checkIfNameExists(username);
		//Email does exists && passwords match
		if(userEmail == 0 && userExists == 0) {
			userService.createUser(email, name, username, password);
			//return SUCCESS and a TOKEN
			return "SUCCESS " + new UserDAO().getTokenAtUsername(username);
		}else if(userExists == 1) {
			//Username already exists
			return "USERNAME EXISTS";
		}else if(userEmail == 1) {
			//Email already exists
			return "EMAIL EXISTS";
		}
		return "FAILED";
	}
	
	/**
	 * Success/Failure page for registration
	 * @param user			User attempting to register
	 * @param bindingResult	Rejects or accepts user's registration attempt
	 * @return				Success/Failure Page
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			//Needs to be redone
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");
			
		}
		return modelAndView;
	}
	

}
