package com.marvel.model.dto.creatorsresponse200; 
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
public class Comics{
    private int available;
    private String collectionURI;
    private ArrayList<Item> items;
    private int returned;
}
