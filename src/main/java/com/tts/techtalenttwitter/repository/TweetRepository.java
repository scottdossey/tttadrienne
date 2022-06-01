package com.tts.techtalenttwitter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.techtalenttwitter.model.Tweet;
import com.tts.techtalenttwitter.model.UserProfile;

@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long> {
	List<Tweet> findAllByOrderByCreatedAtDesc();
	List<Tweet> findAllByUserOrderByCreatedAtDesc(UserProfile user);
	List<Tweet> findAllByUserInOrderByCreatedAtDesc(List<UserProfile> users);
	List<Tweet> findByTags_PhraseOrderByCreatedAtDesc(String phrase);
}
