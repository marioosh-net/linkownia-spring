package net.marioosh.spring.springonly.model.entities;

import java.util.Set;

public class Tag {
	private Integer id;

	private String tag;
	
	public Tag() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}

}
