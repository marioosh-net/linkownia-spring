package net.marioosh.spring.springonly.model.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import net.marioosh.spring.springonly.model.entities.Link;
import org.springframework.jdbc.core.RowMapper;

/**
 * RowMapper - mapuje rekord w bazie danych na na obiekt
 * tutaj, obiektem jest Link
 * 
 * @author marioosh
 *
 */
public class LinkRowMapper implements RowMapper<Link> {

	public Link mapRow(ResultSet rs, int rowNum) throws SQLException {
		Link link = new Link(rs.getInt("id"), rs.getString("address"), rs.getString("name"));
		link.setLdate(rs.getDate("ldate"));
		link.setDescription(rs.getString("description"));
		link.setClicks(rs.getInt("clicks"));
		return link;
	}

}
