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

import com.example.demo.entity.ItemEntity;

@Repository
public class ItemDBAccessJDBC {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public List<ItemEntity> findAll() {

		return jdbcTemplate.query("SELECT * FROM ITEM ORDER BY id",
				new BeanPropertyRowMapper<ItemEntity>(ItemEntity.class));
	}

	public ItemEntity findOne(int id) {

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		try {
			return jdbcTemplate.queryForObject("SELECT * FROM ITEM WHERE id = :id", param,
					new BeanPropertyRowMapper<ItemEntity>(ItemEntity.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public ItemEntity save(ItemEntity entity) {

		ItemEntity selectEntity = this.findOne(entity.getId());
		SqlParameterSource param = new BeanPropertySqlParameterSource(entity);

		if (selectEntity == null) {
			jdbcTemplate.update("INSERT INTO ITEM (id,name,imageUrl,price) VALUES (:id,:name,:imageUrl,:price)", param);
		} else {
			jdbcTemplate.update("UPDATE ITEM SET id=:id, name=:name, imageUrl=:imageUrl, price=:price", param);
		}

		return entity;
	}

	public void delete(int id) {

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		jdbcTemplate.update("DELETE FROM ITEM WHERE id = :id", param);
	}

	public void deleteAll() {
		jdbcTemplate.update("DELETE FROM ITEM", new HashMap<>());
	}
}
