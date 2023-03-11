package com.marvel.core.service.impl;

import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.marvel.core.service.ConsumerApi;
import com.marvel.model.dto.creatorsresponse200.ResponseCreators200;
import com.marvel.model.dto.responsemarvel200.ResponseMarvelService;
import com.marvel.model.dto.storiesresponse200.ResponseStories200;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class ConsumerApiImpl implements ConsumerApi{
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${config.marvel-api.ts}")
	String ts;
	
	@Value("${config.marvel-api.publickey}")
	String publickey;
	
	@Value("${config.marvel-api.privatekey}")
	String privatekey;
	
	@Value("${data-access.marvel-url}")
	String marvelUrl;
	
	@Value("${data-access.characters-path}")
	String charactersPath;
	
	@Value("${data-access.creators-path}")
	String creatorsPath;
	
	@Value("${data-access.stories-path}")
	String storiesPath;
	
	

	@Override
	public ResponseMarvelService getAllCharacters() throws Exception{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<ResponseMarvelService> responseService = null;
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		
		log.info("Parametros: " + ts + ", " + publickey);
		String hash =createHash();
		params.add("ts", ts);
		params.add("apikey", publickey);
		params.add("hash", hash);
		
		
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(marvelUrl+charactersPath) // rawValidURl = http://example.com/hotels
	            .queryParams((LinkedMultiValueMap<String, String>) params); // The allRequestParams must have been built for all the query params
	    UriComponents uriComponents = builder.build().encode(); // encode() is to ensure that characters like {, }, are preserved and not encoded. Skip if not needed.
	    
		
		try {
			responseService = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, ResponseMarvelService.class);
			log.info("Code: " + responseService.getBody().code);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		
		
		
		// TODO Auto-generated method stub
		return responseService.getBody();
	}
	
	@Override
	public ResponseCreators200 getAllCreators() throws Exception{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<ResponseCreators200> responseService = null;
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		
		log.info("Parametros: " + ts + ", " + publickey);
		String hash =createHash();
		params.add("ts", ts);
		params.add("apikey", publickey);
		params.add("hash", hash);
		
		
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(marvelUrl+creatorsPath) // rawValidURl = http://example.com/hotels
	            .queryParams((LinkedMultiValueMap<String, String>) params); // The allRequestParams must have been built for all the query params
	    UriComponents uriComponents = builder.build().encode(); // encode() is to ensure that characters like {, }, are preserved and not encoded. Skip if not needed.
	    
		
		try {
			responseService = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, ResponseCreators200.class);
			log.info("Code: " + responseService.getBody().getCode());
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		
		
		
		// TODO Auto-generated method stub
		return responseService.getBody();
	} 
	
	@Override
	public ResponseStories200 getAllStories() throws Exception{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<ResponseStories200> responseService = null;
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		
		log.info("Parametros: " + ts + ", " + publickey);
		String hash =createHash();
		params.add("ts", ts);
		params.add("apikey", publickey);
		params.add("hash", hash);
		
		
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(marvelUrl+storiesPath) // rawValidURl = http://example.com/hotels
	            .queryParams((LinkedMultiValueMap<String, String>) params); // The allRequestParams must have been built for all the query params
	    UriComponents uriComponents = builder.build().encode(); // encode() is to ensure that characters like {, }, are preserved and not encoded. Skip if not needed.
	    
		
		try {
			responseService = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, ResponseStories200.class);
			log.info("Code: " + responseService.getBody().getCode());
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		
		
		
		// TODO Auto-generated method stub
		return responseService.getBody();
	} 
	
	private String createHash() throws Exception {
		try {
			String hash = "%s%s%s";
			hash = String.format(hash, ts, privatekey, publickey);
			hash = DigestUtils.md5Hex(hash);
			return hash;
		} catch (Exception e) {
			throw e;
		}
	} 

}
