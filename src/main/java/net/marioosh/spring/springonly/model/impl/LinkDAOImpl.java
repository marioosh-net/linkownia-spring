package net.marioosh.spring.springonly.model.impl;

import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import net.marioosh.spring.springonly.model.dao.LinkDAO;
import net.marioosh.spring.springonly.model.entities.Link;
import net.marioosh.spring.springonly.model.entities.Tag;
import net.marioosh.spring.springonly.model.entities.User.ListMode;
import net.marioosh.spring.springonly.model.helpers.BrowseParams;
import net.marioosh.spring.springonly.model.helpers.LinkRowMapper;
import net.marioosh.spring.springonly.model.helpers.LinkWithTagsRowMapper;
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
 * wybierz linki od razu z tagami
 * select l.id, array(select tag from ttag t, tlinktag lt where t.id = lt.tag_id and lt.link_id = l.id) from tlink l 
 * 
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
	
	public Object[] getWithTags(Integer id) {
		String sql = "select l.*, array_to_string(array(select tag from ttag t, tlinktag lt where t.id = lt.tag_id and lt.link_id = l.id),',') as tags from tlink l where l.id = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new Object[] { id }, new LinkWithTagsRowMapper());
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			return null;
		}		
	}
	
	@Override
	public Link get(Integer id, Integer userId) {
		String sql = "select * from tlink where id = ? and user_id = ?";
		try {
			Link link = (Link)jdbcTemplate.queryForObject(sql, new Object[] { id, userId }, new BeanPropertyRowMapper<Link>(Link.class));
			return link;
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Link get(String address, Integer userId) {
		String sql = "select * from tlink where address = ?";
		if(userId != null) {
			sql += " and user_id = ?";
			try {
				List<Link> links = jdbcTemplate.query(sql, new Object[] { address, userId }, new BeanPropertyRowMapper<Link>(Link.class));
				if(links.size() > 0) {
					return links.get(0);				
				}
			} catch (org.springframework.dao.EmptyResultDataAccessException e) {
				return null;
			}			
		} else {
			try {
				List<Link> links = jdbcTemplate.query(sql, new Object[] { address}, new BeanPropertyRowMapper<Link>(Link.class));
				if(links.size() > 0) {
					return links.get(0);				
				}
			} catch (org.springframework.dao.EmptyResultDataAccessException e) {
				return null;
			}						
		}
		return null;
	}
	
	public void add(Link link) {
		log.info("add: "+link);
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
        
        if(link.getPub() == null) {
        	link.setPub(true);
        }
		
		jdbcTemplate.update("insert into tlink (address, name, description, ldate, date_mod, clicks, pub, user_id) values(?, ?, ?, ?, ?, 0, ?, ?)", link.getAddress(), link.getName(), link.getDescription(), new Date(), link.getLdate(), link.getPub(), link.getUserId());
	}

	public Integer addOrUpdate(Link link) {
		log.info("addOrUpdate: "+link);
		Link l = get(link.getAddress(), link.getUserId());
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
			s = " and ";
			int i = 0;
			
			String[] split = q.split(" ");
			for(String p: split) {			
				if(i == 0) {
					s += "(upper(address) like upper('%" + p + "%') or upper(name) like upper('%" + p + "%') "
					// wyszukiwanie w tagach
					+"or exists (select tag from ttag t, tlinktag lt where t.id = lt.tag_id and lt.link_id = l.id and upper(t.tag) like upper('%"+p+"%'))"
					//+"or '"+p+"' in (select tag from ttag t, tlinktag lt where t.id = lt.tag_id and lt.link_id = l.id)"
					;
				} else {
					s += "or upper(address) like upper('%" + p + "%') or upper(name) like upper('%" + p + "%') "
					// wyszukiwanie w tagach
					+"or exists (select tag from ttag t, tlinktag lt where t.id = lt.tag_id and lt.link_id = l.id and upper(t.tag) like upper('%"+p+"%'))"
					//+"or '"+p+"' in (select tag from ttag t, tlinktag lt where t.id = lt.tag_id and lt.link_id = l.id)"
					;
				}
				if(i == split.length - 1) {
					s += ")";
				}
				i++;
			}
		}		
		
		if(browseParams.getPub() != null) {
			s += " and pub = " + browseParams.getPub() + " "; 
		}
		
		ListMode mode = browseParams.getMode();
		if(browseParams.getUserId() != null) {
			if(mode != null) {
				if(mode.equals(ListMode.ALL)) {
					s += " and (user_id = " + browseParams.getUserId() + " or pub = true) ";
				} else if(mode.equals(ListMode.MY_OWN)) {
					s += " and (user_id = " + browseParams.getUserId() + ") ";
				} else if(mode.equals(ListMode.PUBLIC)) {
					s += " and (pub = true) ";
				}
			} else {
				s += " and (user_id = " + browseParams.getUserId() + " or pub = true) ";
			}
		} else {
			s += " and pub = true ";			
		}
		
		if(browseParams.getTags() != null && !browseParams.getTags().isEmpty()) {
			String tags = "";
			int i = 0;
			for(Tag tag: browseParams.getTags()) {
				tags += (i > 0 ? ", ": "") + "'"+tag.getTag()+"'";
				i++;
			}
			s += " and id in (select tl.link_id from tlinktag tl, ttag t where tl.tag_id = t.id and t.tag in ("+ tags +")) ";
		}
		
		String sql = "select * from tlink l where 1 = 1 "+s+" order by "+sort + " " + limit;
		log.info("LinkDAO.findAll() SQL: " + sql);
		// return jdbcTemplate.query(sql, new LinkRowMapper());
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Link>(Link.class));
		
	}
	
	@Override
	public List<Object[]> findAllWithTags(BrowseParams browseParams) {

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
			s = " and ";
			int i = 0;
			
			String[] split = q.split(" ");
			for(String p: split) {			
				if(i == 0) {
					s += "(upper(address) like upper('%" + p + "%') or upper(name) like upper('%" + p + "%') "
					// wyszukiwanie w tagach
					+"or exists (select tag from ttag t, tlinktag lt where t.id = lt.tag_id and lt.link_id = l.id and upper(t.tag) like upper('%"+p+"%'))"
					//+"or '"+p+"' in (select tag from ttag t, tlinktag lt where t.id = lt.tag_id and lt.link_id = l.id)"
					;
				} else {
					s += "or upper(address) like upper('%" + p + "%') or upper(name) like upper('%" + p + "%') "
					// wyszukiwanie w tagach
					+"or exists (select tag from ttag t, tlinktag lt where t.id = lt.tag_id and lt.link_id = l.id and upper(t.tag) like upper('%"+p+"%'))"
					//+"or '"+p+"' in (select tag from ttag t, tlinktag lt where t.id = lt.tag_id and lt.link_id = l.id)"
					;
				}
				if(i == split.length - 1) {
					s += ")";
				}
				i++;
			}
		}		
		
		if(browseParams.getPub() != null) {
			s += " and pub = " + browseParams.getPub() + " "; 
		}
		
		ListMode mode = browseParams.getMode();
		if(browseParams.getUserId() != null) {
			if(mode != null) {
				if(mode.equals(ListMode.ALL)) {
					s += " and (user_id = " + browseParams.getUserId() + " or pub = true) ";
				} else if(mode.equals(ListMode.MY_OWN)) {
					s += " and (user_id = " + browseParams.getUserId() + ") ";
				} else if(mode.equals(ListMode.PUBLIC)) {
					s += " and (pub = true) ";
				}
			} else {
				s += " and (user_id = " + browseParams.getUserId() + " or pub = true) ";
			}
		} else {
			s += " and pub = true ";			
		}
		
		if(browseParams.getTags() != null && !browseParams.getTags().isEmpty()) {
			String tags = "";
			int i = 0;
			for(Tag tag: browseParams.getTags()) {
				tags += (i > 0 ? ", ": "") + "'"+tag.getTag()+"'";
				i++;
			}
			s += " and id in (select tl.link_id from tlinktag tl, ttag t where tl.tag_id = t.id and t.tag in ("+ tags +")) ";
		}
		
		String sql = "select l.*, array_to_string(array(select tag from ttag t, tlinktag lt where t.id = lt.tag_id and lt.link_id = l.id),',') as tags from tlink l where 1 = 1 "+s+" order by "+sort + " " + limit;
		log.info("LinkDAO.findAllWithTags() SQL: " + sql);
		// return jdbcTemplate.query(sql, new LinkRowMapper());
		// return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Link>(Link.class));		
		return jdbcTemplate.query(sql, new LinkWithTagsRowMapper());
		
	}

	public int countAll(BrowseParams browseParams) {
		String s = "";
		if(browseParams.getSearch() != null) {
			String q = browseParams.getSearch();
			q = q.replaceAll("[ ]{2,}", " ");
			s = " and ";
			int i = 0;
			
			String[] split = q.split(" ");
			for(String p: split) {
				if(i == 0) {
					s += "(upper(address) like upper('%" + p + "%') or upper(name) like upper('%" + p + "%') "
					// wyszukiwanie w tagach
					+"or exists (select tag from ttag t, tlinktag lt where t.id = lt.tag_id and lt.link_id = l.id and upper(t.tag) like upper('%"+p+"%'))"
					//+"or '"+p+"' in (select tag from ttag t, tlinktag lt where t.id = lt.tag_id and lt.link_id = l.id)"
					;
				} else {
					s += "or upper(address) like upper('%" + p + "%') or upper(name) like upper('%" + p + "%') "
					// wyszukiwanie w tagach
					+"or exists (select tag from ttag t, tlinktag lt where t.id = lt.tag_id and lt.link_id = l.id and upper(t.tag) like upper('%"+p+"%'))"
					//+"or '"+p+"' in (select tag from ttag t, tlinktag lt where t.id = lt.tag_id and lt.link_id = l.id)"
					;
				}
				if(i == split.length - 1) {
					s += ")";
				}
				i++;
			}
		}				
		// String s = browseParams.getSearch() != null ? "and (upper(address) like upper('%"+browseParams.getSearch()+"%') or upper(name) like upper('%"+browseParams.getSearch()+"%'))" : "";
		
		if(browseParams.getPub() != null) {
			s += " and pub = " + browseParams.getPub() + " "; 
		}
		
		ListMode mode = browseParams.getMode();
		if(browseParams.getUserId() != null) {
			if(mode != null) {
				if(mode.equals(ListMode.ALL)) {
					s += " and (user_id = " + browseParams.getUserId() + " or pub = true) ";
				} else if(mode.equals(ListMode.MY_OWN)) {
					s += " and (user_id = " + browseParams.getUserId() + ") ";
				} else if(mode.equals(ListMode.PUBLIC)) {
					s += " and (pub = true) ";
				}
			} else {
				s += " and (user_id = " + browseParams.getUserId() + " or pub = true) ";
			}
		} else {
			s += " and pub = true ";
		}
		
		if(browseParams.getTags() != null && !browseParams.getTags().isEmpty()) {
			String tags = "";
			int i = 0;
			for(Tag tag: browseParams.getTags()) {
				tags += (i > 0 ? ", ": "") + "'"+tag.getTag()+"'";
				i++;
			}
			s += " and id in (select tl.link_id from tlinktag tl, ttag t where tl.tag_id = t.id and t.tag in ("+ tags +")) ";
		}
		
		String sql = "select count(*) from tlink l where 1 = 1 "+s;
		log.debug("SQL: "+sql);
		return jdbcTemplate.queryForInt(sql);
	}

	public void click(Integer id) {
		String sql = "update tlink set clicks = clicks + 1 where id = ?";
		jdbcTemplate.update(sql, new Object[]{id});
	}
	
	public int update(Link link) {
		log.info("update: "+link);
		Object[] params = {link.getAddress(), link.getName(), link.getDescription(), link.getClicks(), link.getLdate(), link.getDateMod(), link.getUserId(), link.getId()};
		int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP, Types.SMALLINT, Types.SMALLINT};
		int rows = jdbcTemplate.update("update tlink set address = ?, name = ?, description = ?, clicks = ?, ldate = ?, date_mod = ?, user_id = ?, pub = "+link.getPub()+" where id = ?", params, types);
		log.debug("Updated "+rows +" rows.");
		return rows;
	}

}
