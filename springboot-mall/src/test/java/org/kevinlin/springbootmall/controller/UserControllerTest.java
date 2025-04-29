package org.kevinlin.springbootmall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.kevinlin.springbootmall.dao.UserDao;
import org.kevinlin.springbootmall.dto.UserLoginRequest;
import org.kevinlin.springbootmall.dto.UserRegisterRequest;
import org.kevinlin.springbootmall.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDao userDao;

    private ObjectMapper objectMapper = new ObjectMapper();

    private UserLoginRequest userLoginRequest;

    // 註冊新帳號
    @Transactional
    @Test
    void register_success() throws Exception {

        //given
        UserRegisterRequest userRegisterRequest = createUser("test1@gmail.com", "123");


        //when
        RequestBuilder requestBuilder = when_execute(
                getuserRegisterRequestJson(userRegisterRequest),
                "/users/register"
        );


        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.userId", notNullValue()))
                .andExpect(jsonPath("$.e_mail", equalTo("test1@gmail.com")))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));

        // 檢查資料庫中的密碼不為明碼
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        assertNotEquals(userRegisterRequest.getPassword(), user.getPassword());
    }

    @Transactional
    @Test
    void register_invalidEmailFormat() throws Exception {

        //given
        UserRegisterRequest userRegisterRequest = createUser("3gd8e7q34l9", "123");
        String json = getuserRegisterRequestJson(userRegisterRequest);


        //when
        RequestBuilder requestBuilder = when_execute(json, "/users/register");


        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Transactional
    @Test
    void register_emailAlreadyExist() throws Exception {

        //given
        UserRegisterRequest userRegisterRequest = createUser("test2@gmail.com", "123");
        String json = getuserRegisterRequestJson(userRegisterRequest);


        //when
        RequestBuilder requestBuilder = when_execute(json, "/users/register");
        //創建帳號
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));


        //then
        // 再次使用同個 email 註冊
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    void login_success() throws Exception {

        //given
        UserRegisterRequest userRegisterRequest = createUser("test3@gmail.com", "123");
        register(userRegisterRequest);
        // 再測試登入功能
        login(userRegisterRequest.getEmail(), userRegisterRequest.getPassword());
        String getuserRegisterRequestJson = getuserRegisterRequestJson(userRegisterRequest);


        //when
        RequestBuilder requestBuilder = when_execute(
                getuserRegisterRequestJson,
                "/users/login"
        );


        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.userId", notNullValue()))
                .andExpect(jsonPath("$.e_mail", equalTo(userRegisterRequest.getEmail())))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }

    @Test
    void login_wrongPassword() throws Exception {

        //given
        UserRegisterRequest userRegisterRequest = createUser("test4@gmail.com", "123");
        register(userRegisterRequest);
        // 測試密碼輸入錯誤的情況
        login(userRegisterRequest.getEmail(), "unknown");
        String getUserLoginRequestJson = getUserLoginRequestJson();


        //when
        RequestBuilder requestBuilder = when_execute(getUserLoginRequestJson, "/users/login");


        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    void login_invalidEmailFormat() throws Exception {

        //given
        login("hkbudsr324", "123");


        //when
        RequestBuilder requestBuilder = when_execute(getUserLoginRequestJson(), "/users/login");


        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    void login_emailNotExist() throws Exception {

        //given
        login("unknown@gmail.com", "123");


        //when
        RequestBuilder requestBuilder = when_execute(getUserLoginRequestJson(), "/users/login");


        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    private String getuserRegisterRequestJson(UserRegisterRequest userRegisterRequest) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(userRegisterRequest);
        return json;
    }

    private void register(UserRegisterRequest userRegisterRequest) throws Exception {
        String json = getuserRegisterRequestJson(userRegisterRequest);

        RequestBuilder requestBuilder = when_execute(json, "/users/register");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));
    }

    private static RequestBuilder when_execute(String json, String path) {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        return requestBuilder;
    }

    private static UserRegisterRequest createUser(String mail, String password) {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail(mail);
        userRegisterRequest.setPassword(password);
        return userRegisterRequest;
    }

    private static void login(String email, String password) {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail(email);
        userLoginRequest.setPassword(password);
    }

    private String getUserLoginRequestJson() throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(userLoginRequest);
        return json;
    }

}