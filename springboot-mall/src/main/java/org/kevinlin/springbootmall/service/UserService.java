package org.kevinlin.springbootmall.service;

import jakarta.validation.Valid;
import org.kevinlin.springbootmall.dto.UserLoginRequest;
import org.kevinlin.springbootmall.dto.UserRegisterRequest;
import org.kevinlin.springbootmall.model.User;

public interface UserService {

    Long register(UserRegisterRequest userRegisterRequest);

    User getUserById(Long userId);

    User login(UserLoginRequest userLoginRequest);
}
