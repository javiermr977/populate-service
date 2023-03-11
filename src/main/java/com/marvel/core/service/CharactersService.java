package com.marvel.core.service;

import java.util.List;

import com.marvel.model.entities.Character;
import com.marvel.model.dto.responsemarvel200.Result;

public interface CharactersService {
	public void saveCharacters(List<Result> lstResult) throws Exception;
	public Character findByCharacterCode(Long characterCode) throws Exception;
}
