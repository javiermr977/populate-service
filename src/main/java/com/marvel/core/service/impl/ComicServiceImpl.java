package com.marvel.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.marvel.core.service.ComicService;
import com.marvel.model.dto.responsemarvel200.Item;
import com.marvel.model.entities.Comic;
import com.marvel.model.dto.responsemarvel200.Result;
import com.marvel.core.repository.ComicRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ComicServiceImpl implements ComicService{
	@Autowired
	private ComicRepository comicRepository;
	
	private static final String URI_COMIC = "http://gateway.marvel.com/v1/public/comics/";
	
	@Override
	public void saveComics(List<Result> lstResult) throws Exception {
		// TODO Auto-generated method stub
		List<Comic> comicEntityList = null;
		try {
			comicEntityList = builComicEntityList(lstResult);
			comicEntityList = filterList(comicEntityList);

			if(!comicEntityList.isEmpty()) {
				comicRepository.saveAll(comicEntityList);
			}
		} catch (Exception e) {
			//throw new CustomException(Constants.MSG_CONFLIC_ERROR, HttpStatus.CONFLICT);
		}
	}
	
	private List<Comic> builComicEntityList(List<Result> characters){
		List<Comic> comicEntityList = null;
		List<com.marvel.model.dto.responsemarvel200.Comics> comics = null;
		List<Item> itemComicList = null;
		try {
			itemComicList = new ArrayList<Item>();
			
			// FILTRAMOS LOS PERSONAJES QUE TIENEN COMICS
			comics = characters
				.stream()
				.filter(it -> it.getComics().getAvailable() > 0)
				.map( it -> it.getComics())
				.collect(Collectors.toList());
			
			// RECORREMOS Y AGREGAMOS A UNA SOLA LISTA
			for(com.marvel.model.dto.responsemarvel200.Comics itemComic: comics) {
				itemComicList.addAll(itemComic.getItems());
			}
			
			// RECORREMOS Y CONSTRUIMOS UNA LISTA DE ENTIDADES COMICS
			comicEntityList = itemComicList
				.stream()
				.distinct()
				.map(it -> {
					Comic comicEntity = new Comic();
					String id = it.getResourceURI();
					id = id.replace(URI_COMIC, "");					
					comicEntity.setComicCode(Long.parseLong(id));
					comicEntity.setName(it.getName());
					
					return comicEntity;
				}).collect(Collectors.toList());
			
		} catch (Exception e) {
			throw e;
		}		
		return comicEntityList;
	}
	
	private List<Comic> filterList(List<Comic> comicEntityList){
		List<Long> comicCodes = null;
		List<Comic> comicsInDataBase = null;
		List<Comic> comicListFiltered = null;
		List<Comic> comicEntityListFiltered = null;
		try {
			comicListFiltered = new ArrayList<>();
			comicEntityListFiltered = new ArrayList<>();
			
			// OBTENEMOS TODOS LOS CODIGOS DE LOS COMICS
			comicCodes = comicEntityList
					.stream()
					.map(it -> it.getComicCode())
					.distinct()
					.collect(Collectors.toList());
			
			// FILTRAMOS LOS DATOS REPETIDOS
			for(Long comicCode: comicCodes) {
				Comic comicAux = comicEntityList
						.stream()
						.filter( it -> it.getComicCode() == comicCode)
						.findFirst().orElse(null);
				if(!Objects.isNull(comicAux)) {
					comicEntityListFiltered.add(comicAux);
				}
			}
			
			// BUSCAMOS EN BASE DE DATOS
			comicsInDataBase = comicRepository.findByComicCodeIn(comicCodes);
			
			// REUTILIZAMOS LA VARIABLE Y OBTENEMOS LOS CODIGOS DE LOS REGISTROS QUE OBTUVIMOS DE LA BASE DE DATOS
			comicCodes = comicsInDataBase
					.stream()
					.map(it -> it.getComicCode())
					.collect(Collectors.toList());
			
			// FILTRAMOS Y AGREGAMOS A UNA NUEVA LISTA LOS COMICS QUE NO ESTAN EN BASE DE DATOS
			for(Comic itemComic: comicEntityListFiltered) {
				if(!comicCodes.contains(itemComic.getComicCode())) {
					comicListFiltered.add(itemComic);
				}
			}			
			
		} catch (Exception e) {
			throw e;
		}
		return comicListFiltered;
	}

	@Override
	public List<Comic> findByComicCodeIn(List<Long> comicCodes) throws Exception {
		// TODO Auto-generated method stub
		try {
			return comicRepository.findByComicCodeIn(comicCodes);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
