package com.marvel.model.dto.responsemarvel200; 
import java.util.ArrayList;
import java.util.Date;
import java.util.List; 
import lombok.Data;

@Data
public class Result{
    public Integer id;
    public String name;
    public String description;
    public Date modified;
    public Thumbnail thumbnail;
    public String resourceURI;
    public Comics comics;
    public Series series;
    public Stories stories;
    public Events events;
    public ArrayList<Url> urls;
}
