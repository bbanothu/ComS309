package com.cychess.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
 * Controller class for website navigation
 * @author bbanothu
 *
 */
@RestController
public class WebsiteController {

	@Autowired
    JdbcTemplate jdbcTemplate;
	@Autowired
	private AnnoucementsService AnnoucementsService;
	@Autowired
	private UserService userService;
	
	/**
	 * Homepage for admin view
	 * @return			Page with all admin controls
	 */
	@CrossOrigin
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		
        List<Map<String, Object>> listContact = new UserDAO().getUserList();
        modelAndView.addObject("listContact", listContact);
        
        List<Map<String, Object>> announcements = new UserDAO().getAnnouncements();
        modelAndView.addObject("announcements", announcements);
        
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
	

	/**
	 * Homepage for regular user
	 * @return			Page with user controls
	 */
	@CrossOrigin
	@RequestMapping(value="/user/home", method = RequestMethod.GET)
	public ModelAndView home_1(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + " (" + user.getEmail() + ")");
		
		
        modelAndView.addObject("username", user.getUserName());
        modelAndView.addObject("name", user.getName());
        modelAndView.addObject("email", user.getEmail());
        
        modelAndView.addObject("total_games", user.getTotalGames());
        modelAndView.addObject("rank", user.getRank());
        modelAndView.addObject("games_won", user.getGameWon());
        modelAndView.addObject("games_lost", user.getGameLost());

	   Set<Role> r =  user.getRoles();
	   
		if(r.iterator().next().getId() == 1) {
			modelAndView.addObject("admin_page",false);
		}else {
			modelAndView.addObject("admin_page",true);
		}
		modelAndView.setViewName("user/home");
		return modelAndView;
	}
	
	@CrossOrigin
	@RequestMapping(value="/user/userlist", method = RequestMethod.GET)
	public ModelAndView users(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + " (" + user.getEmail() + ")");
		
        List<Map<String, Object>> listContact = new UserDAO().getUserList();
        modelAndView.addObject("listContact", listContact);
        
	   Set<Role> r =  user.getRoles();
	   
		if(r.iterator().next().getId() == 1) {
			modelAndView.addObject("admin_page",false);
		}else {
			modelAndView.addObject("admin_page",true);
		}
		modelAndView.setViewName("user/userlist");
		return modelAndView;
	}
	
	@CrossOrigin
	@RequestMapping(value="/user/announcements", method = RequestMethod.GET)
	public ModelAndView announcements(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + " (" + user.getEmail() + ")");
		
        List<Map<String, Object>> announcements = new UserDAO().getAnnouncements();
        modelAndView.addObject("announcements", announcements);
        
	   Set<Role> r =  user.getRoles();
	   
		if(r.iterator().next().getId() == 1) {
			modelAndView.addObject("admin_page",false);
		}else {
			modelAndView.addObject("admin_page",true);
		}
		modelAndView.setViewName("/user/announcements");
		return modelAndView;
	}
	@CrossOrigin
	@RequestMapping(value="/user/settings_page", method = RequestMethod.GET)
	public ModelAndView user_settings_page(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + " (" + user.getEmail() + ")");
		
        
	   Set<Role> r =  user.getRoles();
	   
		if(r.iterator().next().getId() == 1) {
			modelAndView.addObject("admin_page",false);
		}else {
			modelAndView.addObject("admin_page",true);
		}
		modelAndView.setViewName("/user/settings");
		return modelAndView;
	}
}
