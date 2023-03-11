package com.marvel.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.marvel.core.service.CharacterComicService;
import com.marvel.model.dto.responsemarvel200.Result;

import com.marvel.core.repository.CharacterComicRepository;
import com.marvel.core.service.CharactersService;
import com.marvel.core.service.ComicService;
import com.marvel.model.entities.CharacterComic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CharacterComicServiceImpl implements CharacterComicService {
	@Autowired
	private CharactersService characterService;
	@Autowired
	private ComicService comicService;
	@Autowired
	private CharacterComicRepository characterComicRepository;
	
	private static final String URI_COMIC = "http://gateway.marvel.com/v1/public/comics/";
	
	@Override
	public void saveCharacterComic(List<Result> lstResult) throws Exception {
		// TODO Auto-generated method stub
		List<CharacterComic> detailEntityList = null;
		try {
			detailEntityList = builEntityList(lstResult);
			if(!detailEntityList.isEmpty()) {
				characterComicRepository.saveAll(detailEntityList);
			}
		} catch (Exception e) {
			//throw new CustomException(Constants.MSG_CONFLIC_ERROR, HttpStatus.CONFLICT);
		}
	}
	
	private List<CharacterComic> builEntityList(List<Result> characters) throws Exception{
		List<CharacterComic> detailEntityList = null;
		try {
			detailEntityList = new ArrayList<>();
			
			// FILTRAMOS LOS CHARACTER QUE TIENE COMICS DISPONIBLES
			characters = characters
					.stream()
					.filter( it -> it.getComics().getAvailable() > 0)
					.collect(Collectors.toList());
			
			// RECORREMOS LOS CHARACTERS PARA CREAR EL DETALLE
			for(Result character: characters) {
				List<com.marvel.model.entities.Comic> comicsFromDataBase = null;
				List<Long> comicCodes = null;
				com.marvel.model.entities.Character characterFromDataBase = null;
				
				// OBTENEMOS EL CHARACTER DE BASE DE DATOS
				characterFromDataBase = characterService.findByCharacterCode(Long.parseLong(character.getId().toString()));
								
				if(!Objects.isNull(characterFromDataBase)) {
					comicCodes = character
							.getComics()
							.getItems()
							.stream()
							.map(it -> {
								String id = it.getResourceURI();
								id = id.replace(URI_COMIC, "");		
								return Long.parseLong(id);
							}).collect(Collectors.toList());
					
					// OBTENEMOS LOS COMICS DE LA BASE DE DATOS 
					comicsFromDataBase = comicService.findByComicCodeIn(comicCodes);
					
					// RECORREMOS LOS COMICS OBTENIDOS PARA CREAR LOS DETALLES
					for(com.marvel.model.entities.Comic comicFromDataBase: comicsFromDataBase) {
						if(!existInDataBase(comicFromDataBase, characterFromDataBase)) {
							CharacterComic characterComic = new CharacterComic();
							characterComic.setCharacter(characterFromDataBase);
							characterComic.setComic(comicFromDataBase);
							detailEntityList.add(characterComic);
						}
					}
				}
				
			}			
		} catch (Exception e) {
			throw e;
		}		
		return detailEntityList;
	}

	private Boolean existInDataBase(com.marvel.model.entities.Comic comic, com.marvel.model.entities.Character character) {
		try {
			return characterComicRepository.findByComicAndCharacter(comic, character).isPresent();
		} catch (Exception e) {
			throw e;
		}
	}

}
