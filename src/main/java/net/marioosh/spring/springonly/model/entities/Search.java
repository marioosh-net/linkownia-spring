package net.marioosh.spring.springonly.model.entities;

import java.util.Date;

public class Search {
	private Integer id;

	private String phrase;

	private Date date;

	private int counter;

	public Search() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhrase() {
		return phrase;
	}
	
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

}
