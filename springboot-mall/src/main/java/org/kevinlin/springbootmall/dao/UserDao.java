package org.kevinlin.springbootmall.dao;

import org.kevinlin.springbootmall.model.User;

public interface UserDao {

    Long createUser(User user);

    User getUserById(Long userId);

    User getUserByEmail(String email);
}
