package com.marvel.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marvel.model.entities.Character;
import com.marvel.model.entities.CharacterSerie;
import com.marvel.model.entities.Serie;

public interface CharacterSerieRepository extends JpaRepository<CharacterSerie, Long> {
	public Optional<CharacterSerie> findByCharacterAndSerie(Character character, Serie serie);
}
