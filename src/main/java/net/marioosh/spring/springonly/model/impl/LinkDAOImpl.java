package net.marioosh.spring.springonly.model.impl;

import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import net.marioosh.spring.springonly.model.dao.LinkDAO;
import net.marioosh.spring.springonly.model.entities.Link;
import net.marioosh.spring.springonly.model.entities.Tag;
import net.marioosh.spring.springonly.model.helpers.BrowseParams;
import net.marioosh.spring.springonly.model.helpers.LinkRowMapper;
import net.marioosh.spring.springonly.utils.WebUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * wybierz linki z tagiem 'allegro'
 * select * from tlink where id in (select tl.link_id from tlinktag tl, ttag t where tl.tag_id = t.id and t.tag = 'allegro');
 * 
 * wybierz linki z tagiem 'allegro' lub 'dupa','jas','wacek'
 * select * from tlink where id in (select tl.link_id from tlinktag tl, ttag t where tl.tag_id = t.id and t.tag in ('allegro','dupa','jas','wacek'));
 * 
 * @author marioosh
 *
 */
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

	public Link get(String address) {
		String sql = "select * from tlink where address = ?";
		try {
			List<Link> links = jdbcTemplate.query(sql, new String[] { address }, new BeanPropertyRowMapper<Link>(Link.class));
			if(links.size() > 0) {
				return links.get(0);				
			}
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			return null;
		}
		return null;
	}
	
	public void add(Link link) {

		// jesli name lub descriptione jest empty to pociagnij z stronki
		if((link.getName() == null || link.getName().isEmpty()) || (link.getDescription() == null || link.getDescription().isEmpty())) {
			
			Map<String, String> m = WebUtils.pageInfo(link.getAddress());
			if(m.get("title") != null && (link.getName() == null || link.getName().isEmpty())) {
				link.setName(m.get("title"));
			}
			if(m.get("description") != null && (link.getDescription() == null || link.getDescription().isEmpty())) {
				link.setDescription(m.get("description"));
			}
		}
		
		// jesli name pozostaje empty, to uzyj adresu
        if(link.getName() == null || link.getName().isEmpty()) {
            link.setName(link.getAddress());
        }
		
		jdbcTemplate.update("insert into tlink (address, name, description, ldate, date_mod, clicks) values(?, ?, ?, ?, ?, 0)", link.getAddress(), link.getName(), link.getDescription(), new Date(), link.getLdate());
	}

	public Integer addOrUpdate(Link link) {
		Link l = get(link.getAddress());
		if(l != null) {
			// update
			if(link.getDescription() != null && !link.getDescription().isEmpty()) {
				l.setDescription(link.getDescription());
			}
			if(link.getName() != null && !link.getName().isEmpty()) {
				l.setName(link.getName());
			}			
			l.setDateMod(new Date());
			update(l);
			return l.getId();
		} else {
			add(link);
			return jdbcTemplate.queryForInt("select currval('tlink_id_seq')");
		}
		
		
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
		
		String s = "";
		if(browseParams.getSearch() != null) {
			String q = browseParams.getSearch();
			q = q.replaceAll("[ ]{2,}", " ");
			s = "and ";
			int i = 0;
			for(String p: q.split(" ")) {
				if(i == 0) {
					s += "upper(address) like upper('%" + p + "%') or upper(name) like upper('%" + p + "%') ";
				} else {
					s += "or upper(address) like upper('%" + p + "%') or upper(name) like upper('%" + p + "%') ";
				}
				i++;
			}
		}		
		
		if(browseParams.getPub() != null) {
			s += "and pub = " + browseParams.getPub() + " "; 
		}
		
		if(browseParams.getTags() != null && !browseParams.getTags().isEmpty()) {
			String tags = "";
			int i = 0;
			for(Tag tag: browseParams.getTags()) {
				tags += (i > 0 ? ", ": "") + tag.getTag();
				i++;
			}
			s += "and id in (select tl.link_id from tlinktag tl, ttag t where tl.tag_id = t.id and t.tag in ("+ tags +"))";
		}
		
		String sql = "select * from tlink where 1 = 1 "+s+" order by "+sort + " " + limit;
		log.debug("LINKS SQL: " + sql);
		// return jdbcTemplate.query(sql, new LinkRowMapper());
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Link>(Link.class));
		
	}

	public int countAll(BrowseParams browseParams) {
		String s = "";
		if(browseParams.getSearch() != null) {
			String q = browseParams.getSearch();
			q = q.replaceAll("[ ]{2,}", " ");
			s = "and ";
			int i = 0;
			for(String p: q.split(" ")) {
				if(i == 0) {
					s += "upper(address) like upper('%" + p + "%') or upper(name) like upper('%" + p + "%') ";
				} else {
					s += "or upper(address) like upper('%" + p + "%') or upper(name) like upper('%" + p + "%') ";
				}
				i++;
			}
		}				
		// String s = browseParams.getSearch() != null ? "and (upper(address) like upper('%"+browseParams.getSearch()+"%') or upper(name) like upper('%"+browseParams.getSearch()+"%'))" : "";
		
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
		Object[] params = {link.getAddress(), link.getName(), link.getDescription(), link.getClicks(), link.getLdate(), link.getDateMod(), link.getId()};
		int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP, Types.SMALLINT};
		int rows = jdbcTemplate.update("update tlink set address = ?, name = ?, description = ?, clicks = ?, ldate = ?, date_mod = ? where id = ?", params, types);
		log.debug("Updated "+rows +" rows.");
		return rows;
	}

}
