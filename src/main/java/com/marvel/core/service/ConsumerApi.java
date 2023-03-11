package com.marvel.core.service;

import java.util.Map;

import com.marvel.model.dto.creatorsresponse200.ResponseCreators200;
import com.marvel.model.dto.responsemarvel200.ResponseMarvelService;
import com.marvel.model.dto.storiesresponse200.ResponseStories200;

public interface ConsumerApi {
	public ResponseMarvelService getAllCharacters() throws Exception;
	public ResponseCreators200 getAllCreators() throws Exception;
	public ResponseStories200 getAllStories() throws Exception;
}
