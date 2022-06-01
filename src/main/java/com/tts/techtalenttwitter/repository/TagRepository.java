package com.tts.techtalenttwitter.repository;

import org.springframework.data.repository.CrudRepository;

import com.tts.techtalenttwitter.model.Tag;

public interface TagRepository extends CrudRepository<Tag, Long> {
	Tag findByPhrase(String phrase);
}
