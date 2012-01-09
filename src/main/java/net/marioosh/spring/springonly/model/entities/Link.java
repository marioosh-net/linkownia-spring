package net.marioosh.spring.springonly.model.entities;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.hibernate.validator.constraints.NotEmpty;
import com.ocpsoft.pretty.time.PrettyTime;

public class Link {
	private Integer id;

	@NotEmpty(message="Address may not be empty")
	private String address;

	private String name = "";
	
	private String description = "";
	
	private Date ldate;
	
	private Date dateMod;
	
	private Boolean pub;
	
	private int clicks;
	
	private Set<Tag> tags;
	
	private Integer userId;

	public Link() {
	}
	
	public Link(Integer id, String address, String name) {
		super();
		this.id = id;
		this.address = address;
		this.name = name;
	}

	public Link(String address, String name) {
		super();
		this.address = address;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		// return address;
		if(address != null) {
			return address.startsWith("http://") || address.startsWith("https://") ? address : "http://"+address;
		}
		return null;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getLdate() {
		return ldate;
	}
	
	public String getLdateFormatted() {
		if(ldate != null) {
			return new SimpleDateFormat("dd.MM.yyyy").format(ldate);
		} else {
			return new SimpleDateFormat("dd.MM.yyyy").format(new Date());
		}
	}
	
	public void setLdate(Date ldate) {
		this.ldate = ldate;
	}
	
	public int getClicks() {
		return clicks;
	}
	
	public void setClicks(int clicks) {
		this.clicks = clicks;
	}
	
	public Date getDateMod() {
		return dateMod;
	}
	
	public String getDateModFormatted() {
		// return new SimpleDateFormat("dd.MM.yyyy").format(dateMod);
		PrettyTime p = new PrettyTime();
		return p.format(dateMod);
		
		/*
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	    Date past = dateMod;
	    Date now = new Date();
	    return TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago";
	    */
	}
	
	public void setDateMod(Date dateMod) {
		this.dateMod = dateMod;
	}
	
	public String getHostName() {
		try {
			return new URL(address).getHost();
		} catch (MalformedURLException e) {
			return "";
		}
	}
	
	public Boolean getPub() {
		return pub;
	}
	
	public void setPub(Boolean pub) {
		this.pub = pub;
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
	
	@Override
	public String toString() {
		return "{\n" +
				"id:"+id+",\n "+
				"userId:"+userId+",\n "+
				"pub:"+pub+",\n "+				
				"address:"+address+",\n "+
				"name:"+name+",\n "+
				"description:"+description+",\n "+				
				"dateMod:"+dateMod+",\n "+
				"ldate:"+ldate+",\n "+
				"clicks:"+clicks+",\n "+				
				"tags:"+tags
				+"\n}";
	}
}
