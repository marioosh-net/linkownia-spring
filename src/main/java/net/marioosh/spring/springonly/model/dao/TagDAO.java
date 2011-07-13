package net.marioosh.spring.springonly.model.dao;

import java.util.List;
import java.util.Set;
import net.marioosh.spring.springonly.model.entities.Search;
import net.marioosh.spring.springonly.model.entities.Tag;
import net.marioosh.spring.springonly.model.helpers.TagBrowseParams;

public interface TagDAO {
	public List<Tag> findAll(TagBrowseParams browseParams);
	public int countAll(TagBrowseParams browseParams);
	public Tag get(Integer id);
	public void add(Tag tag);
	public void delete(Integer id);
	
	/**
	 * polacz zataguj linka istniejacym tagiem
	 * @param tagId
	 * @param linkId
	 */
	public void connect(Integer tagId, Integer linkId);
	
	/**
	 * zataguj linka tagami z listy
	 * @param tags
	 * @param linkId
	 */
	public void connect(Set<String> tags, Integer linkId);
	
	/**
	 * odtaguj linka
	 * @param tagId
	 * @param linkId
	 */
	public void disconnect(Integer tagId, Integer linkId);
	
}
