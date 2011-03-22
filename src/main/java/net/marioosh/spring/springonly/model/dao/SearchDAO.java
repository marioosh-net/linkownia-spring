package net.marioosh.spring.springonly.model.dao;

import java.util.List;
import net.marioosh.spring.springonly.model.entities.Link;
import net.marioosh.spring.springonly.model.entities.Search;
import net.marioosh.spring.springonly.model.helpers.SearchBrowseParams;

public interface SearchDAO {
	public List<Search> findAll(SearchBrowseParams browseParams);
	public int countAll(SearchBrowseParams browseParams);
	public Search get(Integer id);
	public void add(Search search);
	public void delete(Integer id);
	public void trigger(String search);
}
