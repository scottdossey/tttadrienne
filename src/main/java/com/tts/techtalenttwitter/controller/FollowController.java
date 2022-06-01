package com.tts.techtalenttwitter.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.tts.techtalenttwitter.model.UserProfile;
import com.tts.techtalenttwitter.service.UserService;

@Controller
public class FollowController {
	@Autowired
	private UserService userService;
	
	/* In order to follow someone we are just going 
	 * to require that the user makes a POST request
	 * to /follow/{username}
	 */
	@PostMapping(value= "/follow/{username}")
	public String follow(@PathVariable(value="username") String username,
			             HttpServletRequest request)  {
		UserProfile loggedInUser = userService.getLoggedInUser();
		UserProfile userToFollow = userService.findByUsername(username);
		
		List<UserProfile> followers = userToFollow.getFollowers();
		followers.add(loggedInUser);
		userToFollow.setFollowers(followers);
		userService.save(userToFollow);
		return "redirect:" + request.getHeader("Referer");
	}
	
	@PostMapping(value= "/unfollow/{username}")
	public String unfollow(@PathVariable(value="username") String username,
			               HttpServletRequest request)  {
		UserProfile loggedInUser = userService.getLoggedInUser();
		UserProfile userToUnfollow = userService.findByUsername(username);
		
		List<UserProfile> followers = userToUnfollow.getFollowers();
		followers.remove(loggedInUser);
		userToUnfollow.setFollowers(followers);
		userService.save(userToUnfollow);
		return "redirect:" + request.getHeader("Referer");
	}
	
}
