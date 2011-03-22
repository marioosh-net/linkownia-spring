package net.marioosh.spring.springonly.model.impl;

import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import net.marioosh.spring.springonly.model.dao.SearchDAO;
import net.marioosh.spring.springonly.model.entities.Search;
import net.marioosh.spring.springonly.model.helpers.SearchBrowseParams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("searchDAO")
public class SearchDAOImpl implements SearchDAO {

	private Logger log = Logger.getLogger(getClass());
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * @Autowired - autowired by Spring's dependency injection facilities
	 * dataSourca from matching bean in the Spring container
	 */
	@Autowired
	public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	public void add(Search search) {
		jdbcTemplate.update("insert into tsearch (phrase, counter, date) values(?, ?, ?)", search.getPhrase(), search.getCounter(), search.getDate());
	}

	public int countAll(SearchBrowseParams browseParams) {
		String sql = "select count(*) from tsearch " + (browseParams.getPhrase() != null ? "where phrase = '"+browseParams.getPhrase()+"'" : "");
		return jdbcTemplate.queryForInt(sql);
	}

	public void delete(Integer id) {
		jdbcTemplate.update("delete from tsearch where id = "+id);
	}

	public List<Search> findAll(SearchBrowseParams browseParams) {
		String sort = "id desc";
		if(browseParams.getSort() != null) {
			sort = browseParams.getSort();
		}
		String limit = "";
		if(browseParams.getRange() != null) {
			 limit = "limit " + browseParams.getRange().getMax() + " offset " + browseParams.getRange().getStart(); 
		}
		String s = browseParams.getPhrase() != null ? "where phrase = '"+browseParams.getPhrase()+"'" : "";
		String sql = "select * from tsearch "+s+" order by "+sort + " " + limit;
		
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Search>(Search.class));

	}

	public Search get(Integer id) {
		String sql = "select * from tsearch where id = ?";
		try {
			Search search = (Search)jdbcTemplate.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper<Search>(Search.class));
			return search;
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void trigger(String phrase) {
		String sql = "select * from tsearch where phrase = ?";
		try {
			Search search = (Search)jdbcTemplate.queryForObject(sql, new Object[] { phrase }, new BeanPropertyRowMapper<Search>(Search.class));
			jdbcTemplate.update("update tsearch set counter = counter + 1 where id = " + search.getId());
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			Search s = new Search();
			s.setCounter(1);
			s.setDate(new Date());
			s.setPhrase(phrase);
			add(s);
		}		
	}

}
