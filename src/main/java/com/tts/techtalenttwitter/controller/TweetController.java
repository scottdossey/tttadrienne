package com.tts.techtalenttwitter.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tts.techtalenttwitter.model.Tweet;
import com.tts.techtalenttwitter.model.TweetDisplay;
import com.tts.techtalenttwitter.model.UserProfile;
import com.tts.techtalenttwitter.service.TweetService;
import com.tts.techtalenttwitter.service.UserService;

@Controller
public class TweetController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private TweetService tweetService;
	
	@GetMapping(value= {"/tweets", "/"})
	public String getFeed(@RequestParam(value="filter", required=false) String filter, Model model) {
		UserProfile loggedInUser = userService.getLoggedInUser();
	
		List<TweetDisplay> tweets = null;
		if (filter == null) {
			filter="all";
		}
		if (filter.equalsIgnoreCase("following")) {
			List<UserProfile> following = loggedInUser.getFollowing();
			tweets = tweetService.findAllByUsers(following);
			model.addAttribute("filter", "following");
		} else { //filter="all"
			tweets = tweetService.findAll();
			model.addAttribute("filter", "all");
		}
		
		model.addAttribute("tweetList", tweets);
		return "feed";
	}
	
	@GetMapping(value= {"/tweets/{tag}"})
	public String getTweetsByTag(@PathVariable(value="tag") String tag, Model model) {
		List<TweetDisplay> tweets = tweetService.findAllWithTag(tag);
		model.addAttribute("tweetList", tweets);
		model.addAttribute("tag", tag);
		return "taggedTweets";
	}
	
	@GetMapping(value= "/tweets/new")
	public String getTweetForm(Model model) {
		model.addAttribute("tweet", new Tweet());
		return "newTweet";
	}
	
	@PostMapping(value= "/tweets")
	public String submitTweetForm(@Valid Tweet tweet, BindingResult bindingResult, Model model) {
		UserProfile user=userService.getLoggedInUser();
		if (!bindingResult.hasErrors()) {
			tweet.setUser(user);
			tweetService.save(tweet);
			model.addAttribute("successMessage", "Tweet successfully created!");
			model.addAttribute("tweet", new Tweet());
		}
		return "newTweet";
	}	
}
