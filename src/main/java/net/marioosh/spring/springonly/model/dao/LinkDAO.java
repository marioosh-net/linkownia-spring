package net.marioosh.spring.springonly.model.dao;

import java.util.List;
import net.marioosh.spring.springonly.model.entities.Link;
import net.marioosh.spring.springonly.model.helpers.BrowseParams;

public interface LinkDAO {
	public List<Link> findAll(BrowseParams browseParams);
	public int countAll(BrowseParams browseParams);
	public Link get(Integer id);
	public Link get(Integer id, Integer userId);
	public Link get(String address, Integer userId);
	public void add(Link link);
	public Integer addOrUpdate(Link link);
	public void delete(Integer id);
	public int update(Link link);
	
	/**
	 * clicks++
	 * @param id
	 */
	public void click(Integer id);
}
