package com.marvel.core.service;

import java.util.List;

import com.marvel.model.entities.Comic;
import com.marvel.model.dto.responsemarvel200.Result;

public interface ComicService {
	public void saveComics(List<Result> lstResult) throws Exception;
	public List<Comic> findByComicCodeIn(List<Long> comicCodes) throws Exception;
}
