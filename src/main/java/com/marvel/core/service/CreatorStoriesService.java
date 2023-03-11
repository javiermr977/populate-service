package com.marvel.core.service;

import java.util.List;

import com.marvel.model.dto.creatorsresponse200.Result;
import com.marvel.model.entities.Creator;

public interface CreatorStoriesService {
	public void saveCreatorsStories(Creator creator, List<Long> lstStories) throws Exception;
}
