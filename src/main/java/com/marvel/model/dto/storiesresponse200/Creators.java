package com.marvel.model.dto.storiesresponse200; 
import java.util.ArrayList;
import java.util.List;

import com.marvel.model.dto.creatorsresponse200.Item;

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
public class Creators{
    public int available;
    public String collectionURI;
    public ArrayList<Item> items;
    public int returned;
}
