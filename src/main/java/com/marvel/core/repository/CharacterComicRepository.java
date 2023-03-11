package com.marvel.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marvel.model.entities.Character;
import com.marvel.model.entities.CharacterComic;
import com.marvel.model.entities.Comic;

public interface CharacterComicRepository extends JpaRepository<CharacterComic, Long> {
	public Optional<CharacterComic> findByComicAndCharacter(Comic comic, Character character);
}
