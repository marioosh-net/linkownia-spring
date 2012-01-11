package net.marioosh.spring.springonly.model.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import net.marioosh.spring.springonly.model.entities.User;
import net.marioosh.spring.springonly.model.entities.User.ListMode;
import net.marioosh.spring.springonly.model.entities.User.UserRole;
import org.springframework.jdbc.core.RowMapper;


public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setLogin(rs.getString("login"));
		user.setPass(rs.getString("pass"));
		user.setRole(UserRole.values()[rs.getInt("role")]);
		user.setMode(ListMode.values()[rs.getInt("mode")]);
		user.setJoinDate(rs.getDate("join_date"));
		user.setLoginDate(rs.getDate("login_date"));
		return user;
	}

}
