package org.kevinlin.springbootmall.dao.impl;

import org.kevinlin.springbootmall.dao.UserDao;
import org.kevinlin.springbootmall.model.User;
import org.kevinlin.springbootmall.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Long createUser(User user) {
        String sql = "INSERT INTO users (user_id ,email, password, created_date, last_modified_date) " +
                "VALUES (:user_id, :email, :password, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("user_id", user.getUserId());
        map.put("email", user.getEmail());
        map.put("password", user.getPassword());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);


        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));


        return user.getUserId();

    }

    @Override
    public User getUserById(Long userId) {
        String sql = "SELECT user_id, email, password, created_date, last_modified_date " +
                "FROM users WHERE user_id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if(userList.size() > 0) {
            return userList.get(0);
        }else {
            return null;
        }

    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT user_id, email, password, created_date, last_modified_date " +
                "FROM users WHERE email = :email";

        Map<String, Object> map = new HashMap<>();
        map.put("email", email);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if(userList.size() > 0) {
            return userList.get(0);
        }else {
            return null;
        }
    }
}
