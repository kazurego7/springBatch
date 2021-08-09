package com.example.batchprocessing.repository;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ActorRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public void updateTimestamp(Timestamp lastUpdate, Integer actorId) {
		String sql = "UPDATE actor SET last_update = ? WHERE actor_id = ?";
		jdbcTemplate.update(sql, lastUpdate, actorId);
	}
}
