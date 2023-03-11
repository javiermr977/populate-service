package com.marvel.model.dto.responsemarvel200; 
import java.util.ArrayList;
import java.util.List; 

import lombok.Data;

@Data
public class Comics{
    public int available;
    public String collectionURI;
    public ArrayList<Item> items;
    public int returned;
}
