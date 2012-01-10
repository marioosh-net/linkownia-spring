package net.marioosh.spring.springonly.model.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import net.marioosh.spring.springonly.model.dao.UserDAO;
import net.marioosh.spring.springonly.model.entities.User;
import net.marioosh.spring.springonly.model.helpers.UserBrowseParams;
import net.marioosh.spring.springonly.model.helpers.UserRowMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

/**
 * 
 * @author marioosh
 *
 */
@Repository("userDAO")
public class UserDAOImpl implements UserDAO, UserDetailsService {

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

	@Override
	public List<User> findAll(UserBrowseParams browseParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countAll(UserBrowseParams browseParams) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User get(Integer id) {
		String sql = "select * from tuser where id = ?";
		try {
			User user = (User)jdbcTemplate.queryForObject(sql, new Object[] { id }, new UserRowMapper());
			log.info(user);
			return user;
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public User get(String login) {
		String sql = "select * from tuser where login = ?";
		try {
			User user = (User)jdbcTemplate.queryForObject(sql, new Object[] { login }, new UserRowMapper());
			log.info(user);
			return user;
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void add(User user) {
		jdbcTemplate.update("insert into tuser (login, pass, join_date) values(?, ?, ?)", user.getLogin(), DigestUtils.md5DigestAsHex(user.getPass().getBytes()), new Date());
	}

	@Override
	public Integer addOrUpdate(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int update(User user) {
		Object[] params = {user.getLogin(), user.getMode().ordinal(), user.getPass(), user.getRole().ordinal(), user.getId()};
		int[] types = {Types.VARCHAR, Types.SMALLINT, Types.VARCHAR, Types.SMALLINT, Types.SMALLINT};
		int rows = jdbcTemplate.update("update tuser set login = ?, mode = ?, pass = ?, role = ? where id = ?", params, types);
		log.debug("Updated "+rows +" rows.");
		return rows;
	}

	@Override
	public UserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException, DataAccessException {
		final User user = get(login);
		if(user != null) {
			return new UserDetails() {
				
				private static final long serialVersionUID = 1L;
	
				@Override
				public boolean isEnabled() {
					return true;
				}
				
				@Override
				public boolean isCredentialsNonExpired() {
					return true;
				}
				
				@Override
				public boolean isAccountNonLocked() {
					return true;
				}
				
				@Override
				public boolean isAccountNonExpired() {
					return true;
				}
				
				@Override
				public String getUsername() {
					return user.getLogin();
				}
				
				@Override
				public String getPassword() {
					return user.getPass();
				}
				
				@Override
				public Collection<GrantedAuthority> getAuthorities() {
					List<GrantedAuthority> c = new ArrayList();
					c.add(new GrantedAuthorityImpl(user.getRole().name()));
					return c;
				}
			};
		}
		throw new UsernameNotFoundException("User not found");
	}

}
