package com.cychess.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cychess.ServerApplication;
import com.cychess.model.Annoucements;
import com.cychess.model.Role;
import com.cychess.model.User;
import com.cychess.model.UserDAO;
import com.cychess.service.AnnoucementsService;
import com.cychess.service.UserService;

/**
 * Controller class for using information from the server and modifying it
 * @author bbanothu
 *
 */
 
@RestController
public class FunctionController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
    JdbcTemplate jdbcTemplate;
	@Autowired
	private AnnoucementsService AnnoucementsService;
	@Autowired
	private UserService userService;
	
	/**
	 * @return The list of users registered for the game
	 */
    @RequestMapping(value = "/friendslist" , method = RequestMethod.GET)
    public List<Map<String, Object>> userlist() {
    	return new UserDAO().getUserList();
    	
    }
	
    /**
     * Allows admins to post announcements 
     * @param id 	ID of user requesting to post
     * @return		Homepage of admin view
     */
    @RequestMapping(value = "/admin/post" , method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView post1(@RequestParam("id") String id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
    	Annoucements ann = new Annoucements(id);
    	ann.setUserName(user.getUserName());
    	AnnoucementsService.saveAnnoucement(ann);
    	
	    return new ModelAndView("redirect:/admin/home");
    }    
    
    @RequestMapping(value = "/user/settings_username" , method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView change_username(@RequestParam("new") String new_user,@RequestParam("new1") String new_user_1 ) {
    	if(!new_user.equals("")) {
    	if(new_user.equals(new_user_1)) {
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		User user = userService.findUserByEmail(auth.getName());
    		user.setUserName(new_user);
    		new UserDAO().updateUserName(new_user, user.getId());

    	}
    	}
    	
	    return new ModelAndView("redirect:/user/settings_page");
    }
    
    @RequestMapping(value = "/user/settings_email" , method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView change_email(@RequestParam("new") String new_email,@RequestParam("new1") String new_email_1 ) {
    	if(!new_email.equals("")) {
    	if(new_email.equals(new_email_1)) {
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		User user = userService.findUserByEmail(auth.getName());
    		user.setEmail(new_email);
    		new UserDAO().updateEmail(new_email, user.getId());

    	}
    	}
    	
	    return new ModelAndView("redirect:/user/settings_page");
    }
    
    @RequestMapping(value = "/user/settings_password" , method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView change_password(@RequestParam("old") String old_pass, @RequestParam("new") String new_pass,@RequestParam("new1") String new_pass_1 ) {
    	if(new_pass.length() > 4) {
    	if(new_pass.equals(new_pass_1)) {
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		User user = userService.findUserByEmail(auth.getName());
    		if(bCryptPasswordEncoder.matches(old_pass,user.getPassword())) {
    			user.setPassword(bCryptPasswordEncoder.encode(new_pass));
    			new UserDAO().changePassword(user.getUserName(),bCryptPasswordEncoder.encode(new_pass) );
    		}
    	}
    	}
    	
	    return new ModelAndView("redirect:/user/settings_page");
    }
    
    
    /**
     * for client
     * @param old_pass
     * @param new_pass
     * @param new_pass_1
     * @param username
     */
    
    
    @RequestMapping(value = "/user/settings_delete" , method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView deleteUser(@RequestParam("user") String user1,@RequestParam("pass") String pass1 ) {
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		User user = userService.findUserByEmail(auth.getName());
    		if(user.getUserName().equals(user1)) {
    	   		if(bCryptPasswordEncoder.matches(pass1,user.getPassword())) {
    	    		new UserDAO().deleteUserAtId(user.getId());
    	   		}
    		}

    	
    	
	    return new ModelAndView("redirect:/login");
    }
    
    /**
     * Deletes users from the database
     * @param id	ID of user requesting to delete 
     * @return		Homepage of admin view
     */
	@RequestMapping(value = "/admin/deleteContact", method = RequestMethod.GET)
	public ModelAndView deleteContact(@RequestParam("id") String id) {
	    int contactId = new UserDAO().getIdAtUsername(id);    
	    new UserDAO().deleteUserAtId(contactId);
	    return new ModelAndView("redirect:/admin/home");
	}
	
	/**
	 * Deletes announcements from the board
	 * @param id	ID of user requesitng to delete announcement
	 * @return		Homepage of admin view
	 */
	@RequestMapping(value = "/admin/deleteAnnouncement", method = RequestMethod.GET)
	public ModelAndView deleteAnnouncement(@RequestParam("id") String id) {
		int id_num = Integer.parseInt(id);	
	    new UserDAO().deleteAnnouncement(id_num);
	    return new ModelAndView("redirect:/admin/home");
	}
	
	////////////////// CLient Stuff
	


	
	/**
	 * Change password
	 * @param old_pass
	 * @param new_pass
	 * @param new_pass_1
	 * @param email
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "/change_password", method = RequestMethod.POST)
	public String change_password_client(@RequestParam("old") String old_pass, @RequestParam("new") String new_pass,@RequestParam("new1") String new_pass_1, @RequestParam("email") String email ) {
		System.out.println("pass called");
		if (new_pass.length() > 4) {
			if (new_pass.equals(new_pass_1)) {
				User user = userService.findUserByEmail(email);
				if (bCryptPasswordEncoder.matches(old_pass, user.getPassword())) {
					user.setPassword(bCryptPasswordEncoder.encode(new_pass));
					new UserDAO().changePassword(user.getUserName(), bCryptPasswordEncoder.encode(new_pass));
					return "SUCCESS";
				}
			}
		}
		return "FAILED";
	}
    

	/**
	 * Delete Account
	 * @param username
	 * @param password
	 * @param email
	 * @return
	 */

    
	@ResponseBody
	@RequestMapping(value = "/delete_user", method = RequestMethod.POST)
	public String delete_user_client( @RequestParam("new") String username ,@RequestParam("new1") String password, @RequestParam("email") String email ) {
		System.out.println("del called");
		User user = userService.findUserByEmail(email);
		if(user.getUserName().equals(username)) {
	   		if(bCryptPasswordEncoder.matches(password,user.getPassword())) {
	    		new UserDAO().deleteUserAtId(user.getId());
				return "SUCCESS";
	   		}
		}
		return "FAILED";
	}
	
	@ResponseBody
    @RequestMapping(value = "/get_all_Games" , method = RequestMethod.GET)
	public List<Map<String, Object>>  getOnGoingGames( ) {
		 return new UserDAO().getAllGames();
	}
	
    @RequestMapping(value = "/get_gameAtId" , method = RequestMethod.POST)
	public String  getGameAtId(@RequestParam("id") int id) {
		 return new UserDAO().getGameBoardAtId(id);
	}

}