package com.ait.binding;

import javax.persistence.Lob;

import lombok.Data;

@Data
public class PostForm {
	
	private Integer postId;
	private String title;
	private String description;
	
	@Lob
	private String content;

}
