package org.kevinlin.springbootmall.service;

import jakarta.validation.Valid;
import org.kevinlin.springbootmall.dto.UserRegisterRequest;
import org.kevinlin.springbootmall.model.User;

public interface UserService {

    Integer register(@Valid UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);
}
