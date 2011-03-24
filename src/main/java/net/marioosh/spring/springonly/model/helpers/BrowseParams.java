package net.marioosh.spring.springonly.model.helpers;


public class BrowseParams {
	private String search;
	private String sort;
	private Range range;
	private Boolean pub = true;
	private String shortcut;
	private Integer idGroup;
	
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
}
