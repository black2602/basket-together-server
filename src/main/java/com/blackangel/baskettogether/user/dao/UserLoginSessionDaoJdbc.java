package com.blackangel.baskettogether.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.blackangel.baskettogether.common.dao.BaseDaoJdbc;
import com.blackangel.baskettogether.user.domain.UserLoginSession;

public class UserLoginSessionDaoJdbc extends BaseDaoJdbc implements UserLoginSessionDao {
	
	private RowMapper<UserLoginSession> rowMapper = new RowMapper<UserLoginSession>() {

		@Override
		public UserLoginSession mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserLoginSession userLoginSession = new UserLoginSession(
					rs.getLong(1), rs.getString(2), rs.getString(3), rs.getLong(4), rs.getTimestamp(5));
			
			return userLoginSession;
		}
	};
	
	@Override
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
		
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("UserLoginSession")
				.usingColumns("pbk", "pvk")
				.usingGeneratedKeyColumns("session_id");
	
	}
	@Override
	public long add(UserLoginSession userLoginSession) {
		long sessionId = simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(userLoginSession)).longValue();
		return sessionId;
	}
	
	@Override
	public UserLoginSession get(long sessionId) {
		return JdbcTemplate.queryForObject("select * from UserLoginSession where session_id = ?", new Object[]{sessionId}, rowMapper);
	}
	
	@Override
	public int count() {
		return JdbcTemplate.queryForInt("select count(*) from UserLoginSession");
	}
	@Override
	public void deleteAll() {
		JdbcTemplate.update("delete from UserLoginSession");
		
	}

}
