package com.marvel.model.dto.creatorsresponse200; 
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
public class Data{
	private int offset;
	private int limit;
	private int total;
	private int count;
	private List<Result> results;
}
