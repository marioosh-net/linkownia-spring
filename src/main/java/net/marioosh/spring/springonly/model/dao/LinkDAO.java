package net.marioosh.spring.springonly.model.dao;

import java.util.List;
import net.marioosh.spring.springonly.model.entities.Link;
import net.marioosh.spring.springonly.model.helpers.BrowseParams;

public interface LinkDAO {
	public List<Link> findAll(String search);
	public List<Link> findAll(BrowseParams browseParams);
	public Link get(Integer id);
	public void add(Link link);
	public void delete(Integer id);
	
	/**
	 * clicks++
	 * @param id
	 */
	public void click(Integer id);
}
