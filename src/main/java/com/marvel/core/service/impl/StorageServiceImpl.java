package com.marvel.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marvel.core.service.CharacterComicService;
import com.marvel.core.service.CharacterSerieService;
import com.marvel.core.service.CharacterStoryService;
import com.marvel.core.service.CharactersService;
import com.marvel.core.service.ComicService;
import com.marvel.core.service.CreatorService;
import com.marvel.core.service.SerieService;
import com.marvel.core.service.StorageService;
import com.marvel.core.service.StoryService;
import com.marvel.core.service.StoryTypeService;
import com.marvel.model.entities.Character;
import com.marvel.model.dto.responsemarvel200.Data;
import com.marvel.model.dto.responsemarvel200.ResponseMarvelService;
import com.marvel.model.dto.responsemarvel200.Result;
import com.marvel.model.entities.CharacterComic;
import com.marvel.core.repository.CharacterRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StorageServiceImpl implements StorageService{
	@Autowired
	private CharactersService charactersService;
	@Autowired
	private ComicService comicService;
	@Autowired
	private SerieService serieService;
	@Autowired
	private StoryTypeService storyTypeService;
	@Autowired
	private StoryService storyService;
	@Autowired
	private CharacterComicService characterComicService;
	@Autowired
	private CharacterSerieService characterSerieService;
	@Autowired
	private CharacterStoryService characterStoryService;
	@Autowired
	private CreatorService creatorService;
	
	@Override
	public void SaveCharacterData(Data originData) throws Exception {
		try {
			List<Result> lstResult = originData.getResults();
			charactersService.saveCharacters(lstResult);
			comicService.saveComics(lstResult);
			serieService.saveSerie(lstResult);
			storyTypeService.saveStoryType(lstResult);
			storyService.saveStory(lstResult);
			characterComicService.saveCharacterComic(lstResult);
			characterSerieService.saveCharacterStory(lstResult);
			characterStoryService.saveCharacterStory(lstResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		

	}

	@Override
	public void SaveCreatorsData(com.marvel.model.dto.creatorsresponse200.Data originData) throws Exception {
		try {
			List<com.marvel.model.dto.creatorsresponse200.Result> lstResult = originData.getResults();
			creatorService.saveCreators(lstResult);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}
	
	

}
