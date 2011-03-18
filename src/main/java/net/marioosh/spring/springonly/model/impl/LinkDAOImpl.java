package net.marioosh.spring.springonly.model.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import net.marioosh.spring.springonly.model.dao.LinkDAO;
import net.marioosh.spring.springonly.model.entities.Link;
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
				return link;
			}
		};
		return query.execute(); 
	}

	public void add(Link link) {
		jdbcTemplate.update("insert into tlink (address, name, description, ldate) values(?, ?, ?, ?)", link.getAddress(), link.getName(), link.getDescription(), link.getLdate());
	}

	public void delete(Integer id) {
		jdbcTemplate.update("delete from tlink where id = "+id);
	}

}
