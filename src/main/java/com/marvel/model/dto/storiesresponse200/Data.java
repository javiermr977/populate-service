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
public class Data{
    public int offset;
    public int limit;
    public int total;
    public int count;
    public ArrayList<Result> results;
}
