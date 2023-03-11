package com.marvel.model.dto.creatorsresponse200; 
import java.util.ArrayList;
import java.util.Date;
import java.util.List; 

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Result{
	private int id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String suffix;
	private String fullName;
	private Date modified;
	private Thumbnail thumbnail;
	private String resourceURI;
	private Comics comics;
	private Series series;
	private Stories stories;
	private Events events;
	private ArrayList<Url> urls;
}
