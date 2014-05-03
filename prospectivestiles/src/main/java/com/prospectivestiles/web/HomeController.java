package com.prospectivestiles.web;

/*import org.apache.log4j.Logger;*/
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.UserEntityService;


@Controller
/*@RequestMapping("/")*/
public class HomeController {

//	private static Logger logger = Logger.getLogger(HomeController.class);
	
	
	private UserEntityService userEntityService;
	
	public UserEntityService getUserEntityService() {
		return userEntityService;
	}
	@Autowired
	public void setUserEntityService(UserEntityService userEntityService) {
		this.userEntityService = userEntityService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showHome() {

		return "welcome";
	}
	
	@RequestMapping("/welcome")
	public String logoutUser() {
		
		return "welcome";
	}

	
	@RequestMapping(value="/login")
	public String showLogin() {
		return "login";
	}
	
	@RequestMapping("/denied")
	public String showDenied() {
		return "denied";
	}
	
	@RequestMapping(value="/adminpage", method = RequestMethod.GET)
	public String showAdmin(Model model) {
		List<UserEntity> users = userEntityService.getAllUserEntities();
		model.addAttribute("users", users);
		return "adminpage";
	}
	
	
	
	
	/*@RequestMapping("/loggedout")
	public String showLoggedOut() {
		return "loggedout";
	}*/
	
}
