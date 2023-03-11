package com.marvel.core.service;

import java.util.List;

import com.marvel.model.dto.responsemarvel200.Result;

public interface CharacterComicService {
	public void saveCharacterComic(List<Result> lstResult) throws Exception;
}
