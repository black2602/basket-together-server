package com.blackangel.baskettogether.common.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class BaseDaoJdbc {
	protected JdbcTemplate JdbcTemplate;
	protected SimpleJdbcInsert simpleJdbcInsert;
	
	public void setDataSource(DataSource dataSource) {
		JdbcTemplate = new JdbcTemplate(dataSource);
	}
}
