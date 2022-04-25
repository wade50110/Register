package com.example.register.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.register.Service.AppUserService;
import com.example.register.Service.UserRoleService;
import com.example.register.entity.AppUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class RegisterController {

	@Autowired
	AppUserService appUserService;
	@Autowired
	UserRoleService userRoleService;
	
	 @PostMapping("/register")
	 public Map getUser(@RequestBody String jsonData) throws JsonMappingException, JsonProcessingException {
		 Map<String,Object> req = new HashMap<String,Object>();
		 System.out.println(jsonData);
		 HashMap<String,Object> obj = new ObjectMapper().readValue(jsonData, HashMap.class);
		 String username = (String)obj.get("username");
		 String pwd = (String)obj.get("password");
		 pwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
		 Optional<AppUser> user = appUserService.getByUsername(username);
		 if (user.isPresent()) {
			 req.put("errorMessage", "已有帳號重複");
			 return req;
		 } else {
			 AppUser appUser = new AppUser();
			 appUser.setUsername(username);
			 appUser.setPassword(pwd);
			 appUserService.insertOrUpdateUser(appUser);
			 userRoleService.insertUserRole(username);
		 }
		 
		return req;
	 }
	 
	 @RolesAllowed({"HR_MANAGER", "HR_STAFF", "PRODUCT_MANAGER"})
	 @PostMapping("/register")
	 public String TestSession(String name) {
		  String message = "TestSession";
	      System.out.println(message);
	      return message;
	 }
	 
}
