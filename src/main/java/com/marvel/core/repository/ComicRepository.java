package com.marvel.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marvel.model.entities.Comic;

public interface ComicRepository extends JpaRepository<Comic, Long> {
	public List<Comic> findByComicCodeIn(List<Long> comicCodes);
	public Optional<Comic> findByComicCode(Long comicCode);
}
