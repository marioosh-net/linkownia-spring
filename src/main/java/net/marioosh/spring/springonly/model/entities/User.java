package net.marioosh.spring.springonly.model.entities;

import org.hibernate.validator.constraints.NotEmpty;

public class User {
	private Integer id;
	
	@NotEmpty
	private String login;
	
	@NotEmpty
	private String pass;
	
	private UserRole role;
	
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

	@Override
	public String toString() {
		return "{"+id+","+login+","+role+"}";
	}

	public enum UserRole {
		ROLE_USER, ROLE_ADMIN, ROLE_XXX
	}
}