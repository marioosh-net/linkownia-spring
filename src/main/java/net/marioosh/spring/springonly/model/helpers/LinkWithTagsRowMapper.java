package net.marioosh.spring.springonly.model.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import net.marioosh.spring.springonly.model.entities.Link;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

/**
 * RowMapper - mapuje rekord w bazie danych na na obiekt
 * tutaj, obiektem jest Link
 * 
 * @author marioosh
 *
 */
public class LinkWithTagsRowMapper implements RowMapper<Object[]> {
	private Logger log = Logger.getLogger(getClass());
	public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
		Link link = new Link(rs.getInt("id"), rs.getString("address"), rs.getString("name"));
		link.setDateMod(rs.getDate("date_mod"));
		link.setPub(rs.getBoolean("pub"));
		link.setUserId((Integer)rs.getObject("user_id"));
		link.setLdate(rs.getDate("ldate"));
		link.setDescription(rs.getString("description"));
		link.setClicks(rs.getInt("clicks"));
		String tags = rs.getString("tags");
		return new Object[]{link, tags != null ? tags.split(",") : null};
	}

}
