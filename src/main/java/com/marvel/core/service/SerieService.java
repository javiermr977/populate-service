package com.marvel.core.service;

import java.util.List;

import com.marvel.model.entities.Serie;
import com.marvel.model.dto.responsemarvel200.Result;

public interface SerieService {
	public void saveSerie(List<Result> lstResult) throws Exception;
	public List<Serie> findBySerieCodes(List<Long> SerieCodes)throws Exception;
}
