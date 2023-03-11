package com.marvel.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.marvel.core.repository.CharacterRepository;
import com.marvel.core.service.CharactersService;
import com.marvel.model.dto.responsemarvel200.Result;
import com.marvel.model.entities.Character;
import com.marvel.model.entities.CharacterComic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CharactersServiceImpl implements CharactersService{
	@Autowired
	private CharacterRepository repository;
	
	@Override
	public void saveCharacters(List<Result> lstResult) throws Exception {
		// TODO Auto-generated method stub
	
		List<com.marvel.model.entities.Character> lstCharacters = lstResult.stream().map(ch -> {
			com.marvel.model.entities.Character character = new com.marvel.model.entities.Character();
			character.setCharacterCode(Long.valueOf(ch.getId()));
			character.setName(ch.getName());
			character.setDescription(ch.getDescription());
			if(Objects.nonNull(ch.getThumbnail())) {
				character.setImage(ch.getThumbnail().getPath());
				character.setImageExt(ch.getThumbnail().getExtension());
			}
			
			CharacterComic characterComics = new CharacterComic();
			
			return character;
		}).collect(Collectors.toList());
		lstCharacters = filterList(lstCharacters);
		Iterable<Character> characterListIterable = (Iterable<Character>) lstCharacters;
		repository.saveAll(characterListIterable);
		
	}
	
	private List<Character> filterList(List<Character> characterEntityList) {
		List<Long> characterCodes = null;
		List<Character> charactersInDataBase = null;
		List<Character> characterListFiltered = null;
		try {
			characterListFiltered = new ArrayList<>();

			// OBTENEMOS TODOS LOS CODIGOS DE LOS PERSONAJES
			characterCodes = characterEntityList.stream().map(it -> it.getCharacterCode()).collect(Collectors.toList());

			// BUSCAMOS EN BASE DE DATOS
			charactersInDataBase = repository.findByCharacterCodeIn(characterCodes);

			// REUTILIZAMOS LA VARIABLE Y OBTENEMOS LOS CODIGOS DE LOS REGISTROS QUE
			// OBTUVIMOS DE LA BASE DE DATOS
			characterCodes = charactersInDataBase.stream().map(it -> it.getCharacterCode())
					.collect(Collectors.toList());

			// FILTRAMOS Y AGREGAMOS A UNA NUEVA LISTA LOS PERSONAJES QUE NO ESTAN EN BASE
			// DE DATOS
			for (Character itemCharacter : characterEntityList) {
				if (!characterCodes.contains(itemCharacter.getCharacterCode())) {
					characterListFiltered.add(itemCharacter);
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return characterListFiltered;
	}

	@Override
	public Character findByCharacterCode(Long characterCode) throws Exception {
		try {
			return repository.findByCharacterCode(characterCode).orElse(null);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
