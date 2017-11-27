package com.blackangel.baskettogether.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.blackangel.baskettogether.user.domain.User;
import com.blackangel.baskettogether.user.domain.User.UserRegType;

public class UserDaoJdbc implements UserDao {
	private JdbcTemplate JdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;
	private RowMapper<User> rowMapper = new RowMapper<User>() {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			
			user.set_id(rs.getLong(1));
			user.setUserId(rs.getString(2));
			user.setPassword(rs.getString(3));
			user.setNickname(rs.getString(4));
			user.setCountry(rs.getString(5));
			user.setPhone(rs.getString(6));
			user.setRegType(rs.getInt(7));
			user.setSnsId(rs.getString(8));
			user.setPhotoUrl(rs.getString(9));
			user.setRegDts(rs.getDate(10));
			user.setDeviceId(rs.getString(11));
			user.setDeviceType(rs.getString(12));
			user.setLastLoginAt(rs.getTimestamp(13));
			
			return user;
		}
	};
	
	public void setDataSource(DataSource dataSource) {
		JdbcTemplate = new JdbcTemplate(dataSource);
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("User")
				.usingColumns("userId", "password", "nickname", "regType")
				.usingGeneratedKeyColumns("_id");
	}
	
	@Override
	public long add(User user) {
		return simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(user)).longValue();
	}

	@Override
	public User get(String userId) {
		User user = JdbcTemplate.queryForObject("select _id, userId, password, nickname, country, phone, regType, snsId, photoUrl"
				+ ", regDts, deviceId, deviceType, lastLoginAt from User where userId = ?", new Object[]{userId}, rowMapper);
		
		return user;
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(User user) {
		JdbcTemplate.update("update User set lastLoginAt = ?, deviceId = ?, deviceType = ? where userId = ?", 
				user.getLastLoginAt(), user.getDeviceId(), user.getDeviceType(), user.getUserId());
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		JdbcTemplate.update("delete from User");
	}

	@Override
	public User get(String userId, String password) {
		User user = JdbcTemplate.queryForObject("select _id, userId, nickname, country, phone, regType, snsId, photoUrl"
				+ ", regDts, deviceId, deviceType from User where userId = ? and password = ?", new Object[]{userId, password}, rowMapper);
		
		return user;
	}

}
