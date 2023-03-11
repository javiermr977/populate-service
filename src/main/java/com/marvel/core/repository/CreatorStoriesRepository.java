package com.marvel.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marvel.model.entities.Creator;
import com.marvel.model.entities.CreatorStory;
import com.marvel.model.entities.Story;

public interface CreatorStoriesRepository extends JpaRepository<CreatorStory, Long> {
	public List<CreatorStory> findByCreator(Creator creator);
}
