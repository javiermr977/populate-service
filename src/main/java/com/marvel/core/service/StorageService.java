package com.marvel.core.service;


public interface StorageService {
	public void SaveCharacterData(com.marvel.model.dto.responsemarvel200.Data originData) throws Exception;
	public void SaveCreatorsData(com.marvel.model.dto.creatorsresponse200.Data originData) throws Exception;
}
