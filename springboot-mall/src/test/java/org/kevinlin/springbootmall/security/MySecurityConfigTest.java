package org.kevinlin.springbootmall.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class MySecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCors() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .options("/products")
                .with(httpBasic("user2@gmail.com", "123456"))
                .header("Access-Control-Request-Method", "GET")
                .header("Origin", "http://example.com");

        mockMvc.perform(requestBuilder)
                .andExpect(header().exists("Access-Control-Allow-Origin"))
                .andExpect(header().string("Access-Control-Allow-Origin", "http://example.com"))
                .andExpect(header().exists("Access-Control-Allow-Methods"))
                .andExpect(header().string("Access-Control-Allow-Methods", "GET"))
                .andExpect(status().is(200));
    }


    @Test
    public void getProducts_noCsrfToken_fail() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .with(httpBasic("user2@gmail.com", "123456"));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(403));
    }
}