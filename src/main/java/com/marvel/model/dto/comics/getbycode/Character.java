package com.marvel.model.dto.comics.getbycode;

import java.io.Serializable;

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
public class Character implements Serializable {
	private Long characterCode;
	private String name;
	private String description;
	private static final long serialVersionUID = 5562364737733387510L;
}
