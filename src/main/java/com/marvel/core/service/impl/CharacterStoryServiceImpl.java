package com.marvel.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marvel.core.service.CharacterStoryService;
import com.marvel.model.dto.responsemarvel200.Result;
import com.marvel.core.repository.CharacterStoryRepository;
import com.marvel.core.service.CharactersService;
import com.marvel.core.service.StoryService;
import com.marvel.model.entities.CharacterStory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CharacterStoryServiceImpl implements CharacterStoryService{
	@Autowired
	private CharactersService characterService;
	@Autowired
	private StoryService storyService;
	@Autowired
	private CharacterStoryRepository characterStoryRepository;
	
	private static final String URI_STORY = "http://gateway.marvel.com/v1/public/stories/";
	
	@Override
	public void saveCharacterStory(List<Result> lstResult) throws Exception {
		// TODO Auto-generated method stub
		List<CharacterStory> detailEntityList = null;
		try {
			detailEntityList = builEntityList(lstResult);
			if(!detailEntityList.isEmpty()) {
				characterStoryRepository.saveAll(detailEntityList);
			}
		} catch (Exception e) {
			
		}
	}
	
	private List<CharacterStory> builEntityList(List<Result> characters) throws Exception {
		List<CharacterStory> detailEntityList = null;
		try {
			detailEntityList = new ArrayList<>();
			
			// FILTRAMOS LOS CHARACTER QUE TIENE COMICS DISPONIBLES
			characters = characters
					.stream()
					.filter( it -> it.getStories().getAvailable() > 0)
					.collect(Collectors.toList());
			
			// RECORREMOS LOS CHARACTERS PARA CREAR EL DETALLE
			for(Result character: characters) {
				List<com.marvel.model.entities.Story> storiesFromDataBase = null;
				List<Long> storyCodes = null;
				com.marvel.model.entities.Character characterFromDataBase = null;
				
				// OBTENEMOS EL CHARACTER DE BASE DE DATOS
				characterFromDataBase = characterService.findByCharacterCode(Long.parseLong(character.getId().toString()));
								
				if(!Objects.isNull(characterFromDataBase)) {
					storyCodes = character
							.getStories()
							.getItems()
							.stream()
							.map(it -> {
								String id = it.getResourceURI();
								id = id.replace(URI_STORY, "");		
								return Long.parseLong(id);
							}).collect(Collectors.toList());
					
					// OBTENEMOS LAS STORY DE LA BASE DE DATOS 
					storiesFromDataBase = storyService.findByStoryCodes(storyCodes);
					
					// RECORREMOS LOS STORY OBTENIDOS PARA CREAR LOS DETALLES
					for(com.marvel.model.entities.Story storyFromDataBase: storiesFromDataBase) {
						if(!existInDataBase(storyFromDataBase, characterFromDataBase)) {
							CharacterStory characterStory = new CharacterStory();
							characterStory.setCharacter(characterFromDataBase);
							characterStory.setStory(storyFromDataBase);
							detailEntityList.add(characterStory);
						}
					}
				}
				
			}			
		} catch (Exception e) {
			throw e;
		}		
		return detailEntityList;
	}
	
	public Boolean existInDataBase(com.marvel.model.entities.Story story, com.marvel.model.entities.Character character) {
		try {
			return characterStoryRepository.findByCharacterAndStory(character, story).isPresent();
		} catch (Exception e) {
			throw e;
		}
	}

}
