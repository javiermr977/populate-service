package com.marvel.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marvel.core.repository.CreatorRepository;
import com.marvel.core.repository.CreatorStoriesRepository;
import com.marvel.core.repository.StoryRepository;
import com.marvel.core.service.CreatorStoriesService;
import com.marvel.model.entities.Creator;
import com.marvel.model.entities.CreatorStory;
import com.marvel.model.entities.Story;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CreatorStoriesServiceImpl implements CreatorStoriesService {
	@Autowired
	private CreatorStoriesRepository creatorStoriesRepository;
	@Autowired
	private StoryRepository storyRepository;

	@Override
	public void saveCreatorsStories(Creator creator, List<Long> lstStoriesCode) throws Exception {
		// TODO Auto-generated method stub
		List<CreatorStory> lstCreatorStory = new ArrayList<>();
		List<Story> lstStories = storyRepository.findByStoryCodeIn(lstStoriesCode);
		for(Story story : lstStories) {
			CreatorStory creatorStory = new CreatorStory();
			
			creatorStory.setCreator(creator);
			creatorStory.setStory(story);
			
			List<CreatorStory> lstCreStDB = creatorStoriesRepository.findByCreator(creator);
			
			for(CreatorStory cs : lstCreStDB) {
				if(story.getStoryCode() != cs.getStory().getStoryCode()) {
					creatorStoriesRepository.save(creatorStory);
				}
				
			}
			
		}
		
	}

}
