package net.marioosh.spring.springonly;

import javax.validation.Valid;
import net.marioosh.spring.springonly.model.entities.Link;

public class LinkForm {
	
	@Valid
	private Link link;
	
	private String tags;

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
}
