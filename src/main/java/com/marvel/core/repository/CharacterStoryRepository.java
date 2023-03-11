package com.marvel.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marvel.model.entities.Character;
import com.marvel.model.entities.CharacterStory;
import com.marvel.model.entities.Story;

public interface CharacterStoryRepository extends JpaRepository<CharacterStory, Long> {
	public Optional<CharacterStory> findByCharacterAndStory(Character character, Story story);
}
