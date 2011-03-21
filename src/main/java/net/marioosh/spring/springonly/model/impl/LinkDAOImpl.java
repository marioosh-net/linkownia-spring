package net.marioosh.spring.springonly.model.impl;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.marioosh.spring.springonly.model.dao.LinkDAO;
import net.marioosh.spring.springonly.model.entities.Link;
import net.marioosh.spring.springonly.model.helpers.BrowseParams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlQuery;
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
	
	public List<Link> findAll(String search) {
		String s = search != null ? "where address like '%"+search+"%' or name like '%"+search+"%'" : "";
		SqlQuery<Link> query = new MappingSqlQuery<Link>(jdbcTemplate.getDataSource(), "select * from tlink "+s+" order by id desc"){
			@Override
			protected Link mapRow(ResultSet resultset, int i)
					throws SQLException {
				Link link = new Link(resultset.getInt("id"), resultset.getString("address"), resultset.getString("name"));
				link.setLdate(resultset.getDate("ldate"));
				link.setDescription(resultset.getString("description"));
				link.setClicks(resultset.getInt("clicks"));
				return link;
			}
		};
		return query.execute(); 
	}

	public void add(Link link) {
		if(link.getName().isEmpty()) {
			link.setClicks(0);
			Map<String, String> m = pageInfo(link.getAddress());
			if(m.get("title") != null) {
				link.setName(m.get("title"));
			}
			if(m.get("description") != null) {
				link.setDescription(m.get("description"));
			}
		}
		jdbcTemplate.update("insert into tlink (address, name, description, ldate) values(?, ?, ?, ?)", link.getAddress(), link.getName(), link.getDescription(), link.getLdate());
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
		String s = browseParams.getSearch() != null ? "where address like '%"+browseParams.getSearch()+"%' or name like '%"+browseParams.getSearch()+"%'" : "";
		String sql = "select * from tlink "+s+" order by "+sort + " " + limit;
		log.debug("SQL: "+sql);
		SqlQuery<Link> query = new MappingSqlQuery<Link>(jdbcTemplate.getDataSource(), sql){
			@Override
			protected Link mapRow(ResultSet resultset, int i)
					throws SQLException {
				Link link = new Link(resultset.getInt("id"), resultset.getString("address"), resultset.getString("name"));
				link.setLdate(resultset.getDate("ldate"));
				link.setDescription(resultset.getString("description"));
				link.setClicks(resultset.getInt("clicks"));
				return link;
			}
		};
		return query.execute(); 
		
	}

	private Map<String, String> pageInfo(String u) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			u = u.startsWith("http://") || u.startsWith("https://")  ? u : "http://"+u;
			// jericho
			Source source = new Source(new URL(u));
			List<Element> titles = source.getAllElements(HTMLElementName.TITLE);
			if (!titles.isEmpty()) {
				Element title = titles.get(0);
				// return new String(title.getContent().getTextExtractor().toString().getBytes(), "ISO-8859-2");
				map.put("title", title.getTextExtractor().toString());
			}
			
			List<Element> meta = source.getAllElements(HTMLElementName.META);
			if(!meta.isEmpty()) {
				for(Element e: meta) {
					if(e.getAttributeValue("name") != null) {
						if(e.getAttributeValue("name").equalsIgnoreCase("description")) {
							map.put("description", e.getAttributeValue("content"));
						}
					}
				}
			}

		} catch (IOException e) {
			return map;
		}
		return map;
	}
	
}
