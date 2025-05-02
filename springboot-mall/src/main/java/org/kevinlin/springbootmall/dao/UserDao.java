package org.kevinlin.springbootmall.dao;

import org.kevinlin.springbootmall.model.Users;

public interface UserDao {

    Long createUser(Users user);

    Users getUserById(Long userId);

    Users getUserByEmail(String email);
}
