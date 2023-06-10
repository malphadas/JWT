package com.jwt.jwtauth.Controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "Hello, User!";
	}

	@GetMapping("/secured")
	public String secured(Principal principal){
		return "Hello, " + principal.getName();
	}
}

