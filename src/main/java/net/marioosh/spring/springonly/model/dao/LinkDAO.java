package net.marioosh.spring.springonly.model.dao;

import java.util.List;
import net.marioosh.spring.springonly.model.entities.Link;

public interface LinkDAO {
	public List<Link> findAll(String search);
	public void add(Link link);
	public void delete(Integer id);
}
