package com.vageesh.springboot.myfirstwebapp.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class WelcomeController {
	
	/*private AuthenticationService authenticationservice= new AuthenticationService();
	
	
	public LoginController(AuthenticationService authenticationservice) {
		super();
		this.authenticationservice = authenticationservice;
	}

	
	 * @GetMapping("login") public String gotoLoginPage() { return "login"; }
	 */
	
	@GetMapping("/") 
	public String gotoWelcomePage(ModelMap model) {
		model.put("name", getLoggedInUserName());
		return "welcome"; 
		}
	
	private String getLoggedInUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

		/*
		 * @PostMapping("login") //login?name=Ranga RequestParam public String
		 * gotoWelcomePage(@RequestParam String name,
		 * 
		 * @RequestParam String password, ModelMap model) {
		 * 
		 * if(authenticationservice.authenticate(name,password)) {
		 * 
		 * model.put("name",name); model.put("password",password);
		 * 
		 * return "welcome"; } model.put("errorMessage","invalid credentials!"); return
		 * "login";
		 * 
		 * }
		 */
}