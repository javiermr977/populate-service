package com.marvel.api.populateservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marvel.core.facade.PopulateDataFacade;
import com.marvel.model.dto.response.Response200;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/v1/marvel-local/")
public class PopulateServiceController {
	@Autowired
	PopulateDataFacade populateDataFacade;
	
	@GetMapping(value = "/populate")
	public ResponseEntity<Response200> populateSchema(){
		ResponseEntity<Response200> response = null;
		try {
			response = new ResponseEntity<>(populateDataFacade.processingData(), HttpStatus.OK);
		} catch (Exception e) {
			
		}

		return response;
	}
}
