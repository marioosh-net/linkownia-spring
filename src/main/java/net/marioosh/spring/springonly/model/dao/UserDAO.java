package net.marioosh.spring.springonly.model.dao;

import java.util.List;
import net.marioosh.spring.springonly.model.entities.User;
import net.marioosh.spring.springonly.model.helpers.UserBrowseParams;

public interface UserDAO {
	public List<User> findAll(UserBrowseParams browseParams);
	public int countAll(UserBrowseParams browseParams);	
	public User get(Integer id);
	public User get(String login);
	public void add(User user);
	public Integer addOrUpdate(User user);
	public void delete(Integer id);
	public int update(User user);
}
