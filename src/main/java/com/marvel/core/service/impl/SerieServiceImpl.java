package com.marvel.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marvel.core.service.SerieService;
import com.marvel.model.dto.responsemarvel200.Item;
import com.marvel.model.entities.Serie;
import com.marvel.core.repository.SerieRepository;
import com.marvel.model.dto.responsemarvel200.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SerieServiceImpl implements SerieService{
	@Autowired
	private SerieRepository serieRepository;
	
	private static final String URI_SERIE = "http://gateway.marvel.com/v1/public/series/";
	
	@Override
	public void saveSerie(List<Result> lstResult) throws Exception {
		// TODO Auto-generated method stub
		List<Serie> serieEntityList = null;
		try {
			serieEntityList = builSerieEntityList(lstResult);
			serieEntityList = filterList(serieEntityList);
			if(!serieEntityList.isEmpty()) {
				serieRepository.saveAll(serieEntityList);
			}
		} catch (Exception e) {
			
		}
	}
	
	private List<Serie> builSerieEntityList(List<Result> characters){
		List<Serie> serieEntityList = null;
		List<com.marvel.model.dto.responsemarvel200.Series> series = null;
		List<Item> itemSerieList = null;
		try {
			itemSerieList = new ArrayList<Item>();
			
			// FILTRAMOS LOS PERSONAJES QUE TIENEN SERIES
			series = characters
				.stream()
				.filter(it -> it.getSeries().getAvailable() > 0)
				.map( it -> it.getSeries())
				.collect(Collectors.toList());
			
			// RECORREMOS Y AGREGAMOS A UNA SOLA LISTA
			for(com.marvel.model.dto.responsemarvel200.Series itemSerie: series) {
				itemSerieList.addAll(itemSerie.getItems());
			}
			
			// RECORREMOS Y CONSTRUIMOS UNA LISTA DE ENTIDADES SERIES
			serieEntityList = itemSerieList
				.stream()
				.distinct()
				.map(it -> {
					Serie serieEntity = new Serie();
					String id = it.getResourceURI();
					id = id.replace(URI_SERIE, "");					
					serieEntity.setSerieCode(Long.parseLong(id));
					serieEntity.setName(it.getName());
					
					return serieEntity;
				}).collect(Collectors.toList());
			
		} catch (Exception e) {
			throw e;
		}		
		return serieEntityList;
	}

	private List<Serie> filterList(List<Serie> serieEntityList){
		List<Long> serieCodes = null;
		List<Serie> seriesInDataBase = null;
		List<Serie> serieListFiltered = null;
		List<Serie> serieEntityListFiltered = null;
		try {
			serieListFiltered = new ArrayList<>();
			serieEntityListFiltered = new ArrayList<>();
			
			// OBTENEMOS TODOS LOS CODIGOS DE LOS SERIES
			serieCodes = serieEntityList
					.stream()
					.map(it -> it.getSerieCode())
					.distinct()
					.collect(Collectors.toList());
			
			// FILTRAMOS LOS DATOS REPETIDOS
			for(Long serieCode: serieCodes) {
				Serie serieAux = serieEntityList
					.stream()
					.filter( it -> it.getSerieCode() == serieCode)
					.findFirst()
					.orElse(null);
				
				if(!Objects.isNull(serieAux)) {
					serieEntityListFiltered.add(serieAux);
				}
			}
			
			// BUSCAMOS EN BASE DE DATOS
			seriesInDataBase = serieRepository.findBySerieCodeIn(serieCodes);
			
			// REUTILIZAMOS LA VARIABLE Y OBTENEMOS LOS CODIGOS DE LOS REGISTROS QUE OBTUVIMOS DE LA BASE DE DATOS
			serieCodes = seriesInDataBase
					.stream()
					.map(it -> it.getSerieCode())
					.collect(Collectors.toList());
			
			// FILTRAMOS Y AGREGAMOS A UNA NUEVA LISTA LAS SERIES QUE NO ESTAN EN BASE DE DATOS
			for(Serie itemSerie: serieEntityListFiltered) {
				if(!serieCodes.contains(itemSerie.getSerieCode())) {
					serieListFiltered.add(itemSerie);
				}
			}			
	
		} catch (Exception e) {
			throw e;
		}
		return serieListFiltered;
	}

	@Override
	public List<Serie> findBySerieCodes(List<Long> SerieCodes) throws Exception {
		// TODO Auto-generated method stub
		try {
			return serieRepository.findBySerieCodeIn(SerieCodes);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
