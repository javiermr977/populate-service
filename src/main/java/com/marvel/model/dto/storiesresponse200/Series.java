package com.marvel.model.dto.storiesresponse200; 
import java.util.ArrayList;
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
public class Series{
    public int available;
    public String collectionURI;
    public ArrayList<Item> items;
    public int returned;
}
