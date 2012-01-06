package net.marioosh.spring.springonly.model.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import net.marioosh.spring.springonly.model.entities.User;
import org.springframework.jdbc.core.RowMapper;


public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setLogin(rs.getString("login"));
		user.setPass(rs.getString("pass"));
		for(User.UserRole role: User.UserRole.values()) {
			if(role.ordinal() == rs.getInt("role")) {
				user.setRole(role);
				break;
			}
		}
		return user;
	}

}
