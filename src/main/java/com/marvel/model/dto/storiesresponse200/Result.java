package com.marvel.model.dto.storiesresponse200;

import java.util.Date;

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
    public int id;
    public String title;
    public String description;
    public String resourceURI;
    public String type;
    public Date modified;
    public Object thumbnail;
    public Creators creators;
    public Characters characters;
    public Series series;
    public Comics comics;
    public Events events;
    public OriginalIssue originalIssue;
}
