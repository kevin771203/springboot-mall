package org.kevinlin.springbootmall.service.impl;

import org.kevinlin.springbootmall.dao.UserDao;
import org.kevinlin.springbootmall.dto.UserLoginRequest;
import org.kevinlin.springbootmall.dto.UserRegisterRequest;
import org.kevinlin.springbootmall.model.User;
import org.kevinlin.springbootmall.service.UserService;
import org.kevinlin.springbootmall.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    public Long register(UserRegisterRequest userRegisterRequest) {

        //檢查 email 是否存在
        User existingUser = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (existingUser != null) {
            log.warn("User with email {} already exists", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }



        //使用MD5生成密碼
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);
        
        User user = new User();
        user.setUserId(Long.parseLong(idGenerator.generateId()));
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(userRegisterRequest.getPassword());

        //創建 user
        return userDao.createUser(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {

        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        //檢查 email 是否存在
        if (user == null) {
            log.warn("User with email {} not found", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //使用MD5生成密碼
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        //檢查 password 是否正確
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }else {
            log.warn("User with email {} and password not match", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
