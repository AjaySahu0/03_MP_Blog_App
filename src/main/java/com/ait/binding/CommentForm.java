package com.ait.binding;

import javax.persistence.Lob;

import lombok.Data;

@Data
public class CommentForm {
	
	private Integer id;
	private String name;
	private String email;
	
	@Lob
	private String content;

}
