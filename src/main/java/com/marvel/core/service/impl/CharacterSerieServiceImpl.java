package com.marvel.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marvel.core.service.CharacterSerieService;
import com.marvel.model.dto.responsemarvel200.Result;
import com.marvel.core.repository.CharacterSerieRepository;
import com.marvel.core.service.CharactersService;
import com.marvel.core.service.SerieService;
import com.marvel.model.entities.CharacterSerie;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CharacterSerieServiceImpl implements CharacterSerieService{
	@Autowired
	private CharactersService characterService;
	@Autowired
	private SerieService serieService;
	@Autowired
	private CharacterSerieRepository characterSerieRepository;
	
	private static final String URI_SERIE = "http://gateway.marvel.com/v1/public/series/";

	@Override
	public void saveCharacterStory(List<Result> lstResult) throws Exception {
		// TODO Auto-generated method stub
		List<CharacterSerie> detailEntityList = null;
		try {
			detailEntityList = builEntityList(lstResult);
			if(!detailEntityList.isEmpty()) {
				characterSerieRepository.saveAll(detailEntityList);
			}
		} catch (Exception e) {
			
		}

	}
	
	private List<CharacterSerie> builEntityList(List<Result> characters) throws Exception{
		List<CharacterSerie> detailEntityList = null;
		try {
			detailEntityList = new ArrayList<>();
			
			// FILTRAMOS LOS CHARACTER QUE TIENE COMICS DISPONIBLES
			characters = characters
					.stream()
					.filter( it -> it.getSeries().getAvailable() > 0)
					.collect(Collectors.toList());
			
			// RECORREMOS LOS CHARACTERS PARA CREAR EL DETALLE
			for(Result character: characters) {
				List<com.marvel.model.entities.Serie> seriesFromDataBase = null;
				List<Long> serieCodes = null;
				com.marvel.model.entities.Character characterFromDataBase = null;
				
				// OBTENEMOS EL CHARACTER DE BASE DE DATOS
				characterFromDataBase = characterService.findByCharacterCode(Long.parseLong(character.getId().toString()));
								
				if(!Objects.isNull(characterFromDataBase)) {
					serieCodes = character
							.getSeries()
							.getItems()
							.stream()
							.map(it -> {
								String id = it.getResourceURI();
								id = id.replace(URI_SERIE, "");		
								return Long.parseLong(id);
							}).collect(Collectors.toList());
					
					// OBTENEMOS LAS SERIES DE LA BASE DE DATOS 
					seriesFromDataBase = serieService.findBySerieCodes(serieCodes);
					
					// RECORREMOS LOS COMICS OBTENIDOS PARA CREAR LOS DETALLES
					for(com.marvel.model.entities.Serie serieFromDataBase: seriesFromDataBase) {
						if(!existInDataBase(serieFromDataBase, characterFromDataBase)) {
							CharacterSerie characterComic = new CharacterSerie();
							characterComic.setCharacter(characterFromDataBase);
							characterComic.setSerie(serieFromDataBase);
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
	
	private Boolean existInDataBase(com.marvel.model.entities.Serie serie, com.marvel.model.entities.Character character) {
		try {
			return characterSerieRepository.findByCharacterAndSerie(character, serie).isPresent();
		} catch (Exception e) {
			throw e;
		}
	}

}
