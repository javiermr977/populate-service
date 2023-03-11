package com.marvel.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marvel.model.entities.Story;

public interface StoryRepository extends JpaRepository<Story, Long> {
	public List<Story> findByStoryCodeIn(List<Long> storyCodes);
	public Optional<Story> findByStoryCode(Long storyCode);
}
