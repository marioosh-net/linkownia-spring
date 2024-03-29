package net.marioosh.spring.springonly.model.helpers;

import java.util.Set;
import net.marioosh.spring.springonly.model.entities.Tag;
import net.marioosh.spring.springonly.model.entities.User.ListMode;


public class BrowseParams {
	private String search;
	private String sort;
	private Range range;
	private Boolean pub;
	private String shortcut;
	private Integer idGroup;
	private Set<Tag> tags;
	private Integer userId;
	private ListMode mode;
	
	public String getSearch() {
		return search;
	}
	
	public void setSearch(String search) {
		this.search = search;
	}
	
	public String getSort() {
		return sort;
	}
	
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public Range getRange() {
		return range;
	}
	
	public void setRange(Range range) {
		this.range = range;
	}

	public Boolean getPub() {
		return pub;
	}
	
	public void setPub(Boolean pub) {
		this.pub = pub;
	}

	public String getShortcut() {
		return shortcut;
	}
	
	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}
	
	public Integer getIdGroup() {
		return idGroup;
	}
	
	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}
	
	public Set<Tag> getTags() {
		return tags;
	}
	
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public ListMode getMode() {
		return mode;
	}
	
	public void setMode(ListMode mode) {
		this.mode = mode;
	}
}
