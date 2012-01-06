package net.marioosh.spring.springonly.model.helpers;

public class UserBrowseParams {
	private String sort;
	private Range range;
	private String login;
	
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
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}

}
