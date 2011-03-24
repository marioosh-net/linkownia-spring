package net.marioosh.spring.springonly.model.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import net.marioosh.spring.springonly.model.dao.LinkDAO;
import net.marioosh.spring.springonly.model.entities.Link;
import net.marioosh.spring.springonly.model.helpers.BrowseParams;
import net.marioosh.spring.springonly.model.helpers.LinkRowMapper;
import net.marioosh.spring.springonly.utils.WebUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("linkDAO")
public class LinkDAOImpl implements LinkDAO {

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

	/**
	 * BeanPropertyRowMapper - mapuje nazwy kolumn w bazie na nazwy pol w beanie
	 */
	public Link get(Integer id) {
		String sql = "select * from tlink where id = ?";
		try {
			Link link = (Link)jdbcTemplate.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper<Link>(Link.class));
			return link;
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			return null;
		}
	}	
	
	public void add(Link link) {
		if(link.getName().isEmpty()) {
			
			Map<String, String> m = WebUtils.pageInfo(link.getAddress());
			if(m.get("title") != null && (link.getName() == null || link.getName().isEmpty())) {
				link.setName(m.get("title"));
			}
			if(m.get("description") != null && (link.getDescription() == null || link.getDescription().isEmpty())) {
				link.setDescription(m.get("description"));
			}
            if(link.getName() == null || link.getName().isEmpty()) {
                link.setName(link.getAddress());
            }
		}
		jdbcTemplate.update("insert into tlink (address, name, description, ldate, clicks) values(?, ?, ?, ?, 0)", link.getAddress(), link.getName(), link.getDescription(), link.getLdate());
	}

	public void delete(Integer id) {
		jdbcTemplate.update("delete from tlink where id = "+id);
	}

	public List<Link> findAll(BrowseParams browseParams) {

		String sort = "id desc";
		if(browseParams.getSort() != null) {
			sort = browseParams.getSort();
		}
		String limit = "";
		if(browseParams.getRange() != null) {
			 limit = "limit " + browseParams.getRange().getMax() + " offset " + browseParams.getRange().getStart(); 
		}
		String s = browseParams.getSearch() != null ? "and (address like '%"+browseParams.getSearch()+"%' or name like '%"+browseParams.getSearch()+"%')" : "";
		
		if(browseParams.getPub() != null) {
			s += "and pub = " + browseParams.getPub(); 
		}
		String sql = "select * from tlink where 1 = 1 "+s+" order by "+sort + " " + limit;
		
		// return jdbcTemplate.query(sql, new LinkRowMapper());
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Link>(Link.class));
		
	}

	public int countAll(BrowseParams browseParams) {
		String s = browseParams.getSearch() != null ? "and (address like '%"+browseParams.getSearch()+"%' or name like '%"+browseParams.getSearch()+"%')" : "";
		
		if(browseParams.getPub() != null) {
			s += "and pub = " + browseParams.getPub(); 
		}
		
		String sql = "select count(*) from tlink where 1 = 1 "+s;
		return jdbcTemplate.queryForInt(sql);
	}

	public void click(Integer id) {
		String sql = "update tlink set clicks = clicks + 1 where id = ?";
		jdbcTemplate.update(sql, new Object[]{id});
	}
	
	public int update(Link link) {
		Object[] params = {link.getAddress(), link.getName(), link.getDescription(), link.getClicks(), link.getId()};
		int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.SMALLINT};
		int rows = jdbcTemplate.update("update tlink set address = ?, name = ?, description = ?, clicks = ? where id = ?", params, types);
		log.debug("Updated "+rows +" rows.");
		return rows;
	}
	
}
