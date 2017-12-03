package com.blackangel.baskettogether.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.blackangel.baskettogether.common.dao.BaseDaoJdbc;
import com.blackangel.baskettogether.common.exception.UserException;
import com.blackangel.baskettogether.common.util.EncryptUtil;
import com.blackangel.baskettogether.user.domain.User;

public class UserDaoJdbc extends BaseDaoJdbc implements UserDao {


	private RowMapper<User> rowMapper = new RowMapper<User>() {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();

			user.set_id(rs.getLong(1));
			user.setUserId(rs.getString(2));
			user.setPassword(rs.getString(3));
			user.setSalt(rs.getString(4));
			user.setNickname(rs.getString(5));
			user.setCountry(rs.getString(6));
			user.setPhone(rs.getString(7));
			user.setRegType(rs.getInt(8));
			user.setSnsId(rs.getString(9));
			user.setPhotoUrl(rs.getString(10));
			user.setRegDts(rs.getDate(11));
			user.setDeviceId(rs.getString(12));
			user.setDeviceType(rs.getString(13));
			user.setLastLoginAt(rs.getTimestamp(14));

			return user;
		}
	};

	@Override
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);

		simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("User")
				.usingColumns("userId", "password", "salt", "nickname", "snsId", "regType")
				.usingGeneratedKeyColumns("_id");
	}

	@Override
	public long add(User user) {
		return simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(user)).longValue();
	}

	@Override
	public User get(String userId) throws UserException {
		try {
			User user = JdbcTemplate.queryForObject("select _id, userId, password, salt, nickname, country, phone, regType, snsId, photoUrl"
					+ ", regDts, deviceId, deviceType, lastLoginAt from User where userId = ?", new Object[]{userId}, rowMapper);

			return user;
		} catch (DataAccessException e) {
			throw new UserException("Not exist user");
		}
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
