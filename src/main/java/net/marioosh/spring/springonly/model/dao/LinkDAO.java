package net.marioosh.spring.springonly.model.dao;

import java.util.List;
import net.marioosh.spring.springonly.model.entities.Link;
import net.marioosh.spring.springonly.model.helpers.BrowseParams;

public interface LinkDAO {
	public List<Link> findAll(String search);
	public List<Link> findAll(BrowseParams browseParams);
	public int countAll(BrowseParams browseParams);
	public int countAll(String search);
	public Link get(Integer id);
	public void add(Link link);
	public void delete(Integer id);
	public int update(Link link);
	
	/**
	 * clicks++
	 * @param id
	 */
	public void click(Integer id);
}
