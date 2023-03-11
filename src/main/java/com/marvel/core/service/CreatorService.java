package com.marvel.core.service;

import java.util.List;

import com.marvel.model.dto.creatorsresponse200.Result;

public interface CreatorService {
	public void saveCreators(List<Result> lstResult) throws Exception;
}
