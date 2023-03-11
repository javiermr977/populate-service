package com.marvel.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marvel.catalog.ConstantsApplication;
import com.marvel.core.repository.CharacterComicRepository;
import com.marvel.core.repository.CreatorRepository;
import com.marvel.core.service.CreatorService;
import com.marvel.core.service.CreatorStoriesService;
import com.marvel.model.dto.creatorsresponse200.Result;
import com.marvel.model.dto.responsemarvel200.Item;
import com.marvel.model.entities.Character;
import com.marvel.model.entities.CharacterComic;
import com.marvel.model.entities.Comic;
import com.marvel.model.entities.Creator;
import com.marvel.model.entities.Story;
import com.marvel.model.entities.StoryType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CreatorServiceImpl implements CreatorService{
	@Autowired
	private CreatorRepository repository;
	
	@Autowired
	private CreatorStoriesService creatorStoriesService;
	
	@Override
	public void saveCreators(List<Result> lstResult) throws Exception {
		// TODO Auto-generated method stub
		List<Creator> creatorEntityList = null;
		try {
			creatorEntityList = lstResult.stream().map(it -> {
				Creator creator = new Creator();
				
				creator.setCreatorCode(Long.valueOf(it.getId()));
				creator.setFirtsName(it.getFirstName());
				creator.setLastName(it.getLastName());
				creator.setMiddleName(it.getMiddleName());
				
				List<com.marvel.model.dto.creatorsresponse200.Item> lstItems = null;
				List<Long> lstStoryCode = new ArrayList<>();
				if(it.getStories().getAvailable() > 0) {
					lstItems = it.getStories().getItems();
					for(com.marvel.model.dto.creatorsresponse200.Item story : lstItems) {
						String uri = story.getResourceURI();
						String storyCode = uri.replace(ConstantsApplication.RESOURCE_URI_STORY, "");
						lstStoryCode.add(Long.valueOf(storyCode));
					}
				}
				try {
					creatorStoriesService.saveCreatorsStories(creator, lstStoryCode);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
				return creator;
			}).collect(Collectors.toList());
			creatorEntityList = filterList(creatorEntityList);

			if(!creatorEntityList.isEmpty()) {
				Iterable<Creator> creatorListIterable = (Iterable<Creator>) creatorEntityList;
				repository.saveAll(creatorListIterable);
			}
		} catch (Exception e) {
			//throw new CustomException(Constants.MSG_CONFLIC_ERROR, HttpStatus.CONFLICT);
		}
	}
	
	
	private List<Creator> filterList(List<Creator> creatorsEntityList) {
		List<Long> creatorsCodes = null;
		List<Creator> creatorsInDataBase = null;
		List<Creator> creatorsListFiltered = null;
		try {
			creatorsListFiltered = new ArrayList<>();

			// OBTENEMOS TODOS LOS CODIGOS DE LOS PERSONAJES
			creatorsCodes = creatorsEntityList.stream().map(it -> it.getCreatorCode()).collect(Collectors.toList());

			// BUSCAMOS EN BASE DE DATOS
			creatorsInDataBase = repository.findByCreatorCodeIn(creatorsCodes);

			// REUTILIZAMOS LA VARIABLE Y OBTENEMOS LOS CODIGOS DE LOS REGISTROS QUE
			// OBTUVIMOS DE LA BASE DE DATOS
			creatorsCodes = creatorsInDataBase.stream().map(it -> it.getCreatorCode())
					.collect(Collectors.toList());

			// FILTRAMOS Y AGREGAMOS A UNA NUEVA LISTA LOS PERSONAJES QUE NO ESTAN EN BASE
			// DE DATOS
			for (Creator itemCreator : creatorsEntityList) {
				if (!creatorsCodes.contains(itemCreator.getCreatorCode())) {
					creatorsListFiltered.add(itemCreator);
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return creatorsListFiltered;
	}

	

}
