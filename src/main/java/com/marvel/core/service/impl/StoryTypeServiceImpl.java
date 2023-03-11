package com.marvel.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marvel.core.service.StoryTypeService;
import com.marvel.model.dto.responsemarvel200.Item;
import com.marvel.model.entities.StoryType;
import com.marvel.model.dto.responsemarvel200.Result;
import com.marvel.core.repository.StoryTypeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StoryTypeServiceImpl implements StoryTypeService{
	@Autowired
	private StoryTypeRepository storyTypeRepository;

	@Override
	public void saveStoryType(List<Result> lstResult) throws Exception {
		// TODO Auto-generated method stub
		List<StoryType> storyEntityList = null;
		try {
			storyEntityList = builStoryTypeEntityList(lstResult);
			storyEntityList = filterList(storyEntityList);
			if(!storyEntityList.isEmpty()) {
				storyTypeRepository.saveAll(storyEntityList);
			}
		} catch (Exception e) {
			
		}
		
	}
	
	private List<StoryType> builStoryTypeEntityList(List<Result> characters){
		List<StoryType> storyTypeEntityList = null;
		List<com.marvel.model.dto.responsemarvel200.Stories> stories = null;
		List<Item> itemStoryList = null;
		try {
			itemStoryList = new ArrayList<Item>();
			
			// FILTRAMOS LOS PERSONAJES QUE TIENEN STORIES
			stories = characters
				.stream()
				.filter(it -> it.getStories().getAvailable() > 0)
				.map( it -> it.getStories())
				.collect(Collectors.toList());
			
			// RECORREMOS Y AGREGAMOS A UNA SOLA LISTA
			for(com.marvel.model.dto.responsemarvel200.Stories itemStory: stories) {
				itemStoryList.addAll(itemStory.getItems());
			}
			
			// RECORREMOS Y CONSTRUIMOS UNA LISTA DE ENTIDADES STORY TYPES
			storyTypeEntityList = itemStoryList
				.stream()
				.map(it -> it.getType())
				.filter(it -> !Objects.isNull(it))
				.filter(it -> !it.equals(""))
				.distinct()
				.map(it -> {
					StoryType storyTypeEntity = new StoryType();
					storyTypeEntity.setName(it);
					
					return storyTypeEntity;
				}).collect(Collectors.toList());
			
		} catch (Exception e) {
			throw e;
		}		
		return storyTypeEntityList;
	}

	private List<StoryType> filterList(List<StoryType> storyEntityList){
		List<String> storyTypeNames = null;
		List<StoryType> storyTypesInDataBase = null;
		List<StoryType> storyTypeListFiltered = null;
		try {
			storyTypeListFiltered = new ArrayList<>();
			
			// OBTENEMOS TODOS LOS NOMBRES DE LOS STORY TYPE
			storyTypeNames = storyEntityList
					.stream()
					.map(it -> it.getName())
					.collect(Collectors.toList());
			
			// BUSCAMOS EN BASE DE DATOS
			storyTypesInDataBase = storyTypeRepository.findByNameIn(storyTypeNames);
			
			// REUTILIZAMOS LA VARIABLE Y OBTENEMOS LOS NOMBRES DE LOS REGISTROS QUE OBTUVIMOS DE LA BASE DE DATOS
			storyTypeNames = storyTypesInDataBase
					.stream()
					.map(it -> it.getName())
					.collect(Collectors.toList());
			
			// FILTRAMOS Y AGREGAMOS A UNA NUEVA LISTA LOS STORY TYPES QUE NO ESTAN EN BASE DE DATOS
			for(StoryType itemSerie: storyEntityList) {
				if(!storyTypeNames.contains(itemSerie.getName())) {
					storyTypeListFiltered.add(itemSerie);
				}
			}			
	
		} catch (Exception e) {
			throw e;
		}
		return storyTypeListFiltered;
	}

	@Override
	public List<StoryType> getAll() throws Exception {
		try {
			return storyTypeRepository.findAll();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
