package com.marvel.core.service;

import java.util.List;

import com.marvel.model.dto.responsemarvel200.Result;

public interface CharacterSerieService {
	public void saveCharacterStory(List<Result> lstResult) throws Exception;
}
