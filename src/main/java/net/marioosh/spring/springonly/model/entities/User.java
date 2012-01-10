package net.marioosh.spring.springonly.model.entities;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class User {
	private Integer id;
	
	@NotEmpty
	private String login;
	
	@NotEmpty
	private String pass;
	
	private UserRole role = UserRole.ROLE_USER;
	
	private ListMode mode = ListMode.ALL;
	
	private Date joinDate;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}

	public UserRole getRole() {
		return role;
	}
	
	public void setRole(UserRole role) {
		this.role = role;
	}

	public ListMode getMode() {
		return mode;
	}
	
	public void setMode(ListMode mode) {
		this.mode = mode;
	}
	
	public Date getJoinDate() {
		return joinDate;
	}
	
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
	@Override
	public String toString() {
		return "{"+id+","+login+","+role+","+joinDate+"}";
	}

	public enum UserRole {
		ROLE_USER, ROLE_ADMIN
	}
	
	public enum ListMode {
		ALL, PUBLIC, MY_OWN
	}
}
