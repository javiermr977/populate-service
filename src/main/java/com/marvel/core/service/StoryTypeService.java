package com.marvel.core.service;

import java.util.List;


import com.marvel.model.entities.StoryType;
import com.marvel.model.dto.responsemarvel200.Result;

public interface StoryTypeService {
	public void saveStoryType(List<Result> lstResult) throws Exception;
	public List<StoryType> getAll() throws Exception;
}
