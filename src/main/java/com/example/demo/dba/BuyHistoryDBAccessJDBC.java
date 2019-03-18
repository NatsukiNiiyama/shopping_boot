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

import com.example.demo.entity.BuyHistoryEntity;

@Repository
public class BuyHistoryDBAccessJDBC {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public List<BuyHistoryEntity> findAll() {

		return jdbcTemplate.query("SELECT * FROM BUY_HISTORY ORDER BY historyId",
				new BeanPropertyRowMapper<BuyHistoryEntity>(BuyHistoryEntity.class));
	}

	public BuyHistoryEntity findOneByHistoryId(Integer historyId) {

		SqlParameterSource param = new MapSqlParameterSource().addValue("historyId", historyId);

		try {
			return jdbcTemplate.queryForObject("SELECT * FROM BUY_HISTORY WHERE historyId = :historyId", param,
					new BeanPropertyRowMapper<BuyHistoryEntity>(BuyHistoryEntity.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<BuyHistoryEntity> findByUserId(String userId) {

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);

		return jdbcTemplate.query("SELECT * FROM BUY_HISTORY WHERE userId = :userId ORDER BY historyId", param,
				new BeanPropertyRowMapper<BuyHistoryEntity>(BuyHistoryEntity.class));
	}

	public BuyHistoryEntity save(BuyHistoryEntity entity) {

		BuyHistoryEntity historyEntity = this.findOneByHistoryId(entity.getHistoryId());
		SqlParameterSource param = new BeanPropertySqlParameterSource(entity);

		if (historyEntity == null) {
			jdbcTemplate.update(
					"INSERT INTO BUY_HISTORY (historyId,userId,itemId,count) VALUES (nextval('CUSTOMER_CODE_SEQ'),:userId,:itemId,:count)",
					param);
		} else {
			jdbcTemplate.update(
					"UPDATE BUY_HISTORY SET historyId = :historyId, userId = :userId, itemId = :itemId, count = :count",
					param);
		}

		return entity;
	}

	public void delete(String historyId) {

		SqlParameterSource param = new MapSqlParameterSource().addValue("historyId", historyId);

		jdbcTemplate.update("DELETE FROM BUY_HISTORY WHERE historyId = :historyId", param);
	}

	public void deleteAll() {
		jdbcTemplate.update("DELETE FROM BUY_HISTORY", new HashMap<>());
	}
}
