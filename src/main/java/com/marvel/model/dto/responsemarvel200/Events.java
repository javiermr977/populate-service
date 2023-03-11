package com.marvel.model.dto.responsemarvel200; 
import java.util.ArrayList;
import java.util.List; 

@lombok.Data
public class Events{
    public int available;
    public String collectionURI;
    public ArrayList<Item> items;
    public int returned;
}
