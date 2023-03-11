package com.marvel.core.facade.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marvel.core.facade.PopulateDataFacade;
import com.marvel.core.service.ConsumerApi;
import com.marvel.core.service.StorageService;
import com.marvel.model.dto.responsemarvel200.ResponseMarvelService;
import com.marvel.model.dto.creatorsresponse200.ResponseCreators200;
import com.marvel.model.dto.response.Response200;

@Component
public class PopulateDataImpl implements PopulateDataFacade{
	@Autowired
	ConsumerApi consumerApi;
	@Autowired
	StorageService storageService;
	
	@Override
	public Response200 processingData() throws Exception{
		
		Response200 response = new Response200();		
		ResponseMarvelService responseMarvel = consumerApi.getAllCharacters();
		ResponseCreators200 responseCreator = consumerApi.getAllCreators();
		com.marvel.model.dto.responsemarvel200.Data dataMarvel = (com.marvel.model.dto.responsemarvel200.Data) responseMarvel.getData();
		com.marvel.model.dto.creatorsresponse200.Data dataCreators = (com.marvel.model.dto.creatorsresponse200.Data) responseCreator.getData();
		storageService.SaveCharacterData(dataMarvel);
		storageService.SaveCreatorsData(dataCreators);
		
		
		response.setAction("SAVE_DATA");
		response.setCodeResponse(200);
		response.setDateExecution(LocalDateTime.now());
		response.setMessage("Successfully updated data");
		response.setPopulatedObjects("[characters, series, comics, stories, creators]");
		response.setStatus("OK");
		
		
		
		// TODO Auto-generated method stub
		return response;
	}

}
