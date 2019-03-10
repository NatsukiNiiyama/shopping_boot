package com.example.demo.dba;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LoginUserEntity;

@Repository
public class LoginUserDBAccessJDBC {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public List<LoginUserEntity> findAll() {

		return jdbcTemplate.query("SELECT * FROM LOGIN_USER ORDER BY userId",
				new BeanPropertyRowMapper<LoginUserEntity>(LoginUserEntity.class));
	}

	public LoginUserEntity findOne(String id) {

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", id);

		try {
			return jdbcTemplate.queryForObject("SELECT * FROM LOGIN_USER WHERE userId = :userId", param,
					new BeanPropertyRowMapper<LoginUserEntity>(LoginUserEntity.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public LoginUserEntity save(LoginUserEntity entity) {

		LoginUserEntity selectEntity = this.findOne(entity.getUserId());
		SqlParameterSource param = new BeanPropertySqlParameterSource(entity);

		if (selectEntity == null) {
			jdbcTemplate.update(
					"INSERT INTO LOGIN_USER (userId, userName, password) VALUES (:userId, :userName, :password)",
					param);
		} else {
			jdbcTemplate.update("UPDATE LOGIN_USER SET userId = :userId, userName = :userName, password = :password",
					param);
		}

		return entity;
	}

	public void delete(String id) {

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", id);

		jdbcTemplate.update("DELETE FROM LOGIN_USER WHERE userId = :userId", param);
	}

	public void deleteAll() {
		jdbcTemplate.update("DELETE FROM LOGIN_USER", new HashMap<>());
	}
}
