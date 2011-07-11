package net.marioosh.spring.springonly.model.impl;

import java.util.List;
import javax.sql.DataSource;
import net.marioosh.spring.springonly.model.dao.TagDAO;
import net.marioosh.spring.springonly.model.entities.Tag;
import net.marioosh.spring.springonly.model.helpers.TagBrowseParams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("tagDAO")
public class TagDAOImpl implements TagDAO {

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

	public void add(Tag tag) {
		jdbcTemplate.update("insert into ttag (tag) values(?)", tag.getTag());
	}

	public int countAll(TagBrowseParams browseParams) {
		String sql = "select count(*) from ttag t, tlinktag lt where 1 = 1 " + (browseParams.getTag() != null ? " and t.tag = '"+browseParams.getTag()+"' and t.id = tl.tag_id " : " ") + (browseParams.getLinkId() != null ? " and tl.link_id = "+browseParams.getLinkId() + " and t.id = tl.tag_id " : "");
		return jdbcTemplate.queryForInt(sql);
	}

	public void delete(Integer id) {
		jdbcTemplate.update("delete from ttag where id = "+id);
		jdbcTemplate.update("delete from tlinktag where tag_id = "+id);
	}

	public List<Tag> findAll(TagBrowseParams browseParams) {
		String sort = "t.tag";
		if(browseParams.getSort() != null) {
			sort = browseParams.getSort();
		}
		String limit = "";
		if(browseParams.getRange() != null) {
			 limit = "limit " + browseParams.getRange().getMax() + " offset " + browseParams.getRange().getStart(); 
		}
		String s = "";
		boolean usedTlinktag = false;
		if(browseParams.getTag() != null) {
			s = " and t.tag = '"+browseParams.getTag()+"' and t.id = tl.tag_id ";
			usedTlinktag = true;
		}
		// String s = browseParams.getTag() != null ? " and t.tag = '"+browseParams.getTag()+"' and t.id = tl.tag_id " : "";
		
		if(browseParams.getLinkId() != null) {
			s += " and tl.link_id = "+browseParams.getLinkId() + " and t.id = tl.tag_id ";
			usedTlinktag = true;
		}
		//s += browseParams.getLinkId() != null ? " and tl.link_id = "+browseParams.getLinkId() + " and t.id = tl.tag_id ": "";
		
		String sql = "";
		if(usedTlinktag) {
			sql = "select * from ttag t, tlinktag tl where 1 = 1 "+s+" order by "+sort + " " + limit;
		} else {
			sql = "select * from ttag t where 1 = 1 "+s+" order by "+sort + " " + limit;
		}
		
		log.info(sql);
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Tag>(Tag.class));

	}

	public Tag get(Integer id) {
		String sql = "select * from ttag where id = ?";
		try {
			Tag tag = (Tag)jdbcTemplate.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper<Tag>(Tag.class));
			return tag;
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void connect(String[] tags, Integer linkId) {
		log.info("CONNECT: link "+ linkId + ", tags: "+tags);
		for(String tag: tags) {
			Integer id;
			try {
				id = jdbcTemplate.queryForInt("select id from ttag where tag = ?", tag);
			} catch (org.springframework.dao.EmptyResultDataAccessException e) {
				jdbcTemplate.update("insert into ttag (tag) values(?)", tag);
				id = jdbcTemplate.queryForInt("select currval('ttag_id_seq')");
			}	
			connect(id, linkId);
		}
	}
	
	public void connect(Integer tagId, Integer linkId) {
		try {
			if(jdbcTemplate.queryForInt("select count(*) from tlinktag where tag_id = ? and link_id = ?", tagId, linkId) == 0) {
				jdbcTemplate.update("insert into tlinktag (tag_id, link_id) values(?, ?)", tagId, linkId);
				log.debug("CONNECT");
			} else {
				log.debug("NOT CONNECT");
			}
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			// polacz jesli jeszcze nie jest polaczone
			log.debug("CONNECT");
			jdbcTemplate.update("insert into tlinktag (tag_id, link_id) values(?, ?)", tagId, linkId);
		}	
		// jdbcTemplate.update("insert into tlinktag (tag_id, link_id) values(?, ?)", tagId, linkId);
	}
	
	public void disconnect(Integer tagId, Integer linkId) {
		jdbcTemplate.update("delete from tlinktag where tag_id = ? and link_id = ?", tagId, linkId);
	}
}
