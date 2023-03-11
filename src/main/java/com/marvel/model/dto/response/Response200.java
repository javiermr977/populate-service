package com.marvel.model.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Response200 {
	private String action;
	private LocalDateTime dateExecution;
	private String status;
	private String message;
	private String populatedObjects;
	private int codeResponse;
}
