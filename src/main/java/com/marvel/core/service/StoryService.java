package com.marvel.core.service;

import java.util.List;

import com.marvel.model.entities.Story;
import com.marvel.model.dto.responsemarvel200.Result;

public interface StoryService {
	public void saveStory(List<Result> lstResult) throws Exception;
	public List<Story> findByStoryCodes(List<Long> StoryCode)  throws Exception;
}
