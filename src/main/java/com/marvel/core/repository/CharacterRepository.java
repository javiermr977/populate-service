package com.marvel.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.marvel.model.entities.Character;

public interface CharacterRepository extends JpaRepository<Character, Long> {
	public List<Character> findByCharacterCodeIn(List<Long> characterCodes);
	public Optional<Character> findByCharacterCode(Long characterCode);
	
	
}
