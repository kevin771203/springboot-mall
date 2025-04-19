package org.kevinlin.springbootmall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.kevinlin.springbootmall.constant.ProductCategory;
import org.kevinlin.springbootmall.dto.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    // 查詢商品
    @Test
    public void getProduct_success() throws Exception {

        //given
        Integer productId = 1;

        //when
        RequestBuilder requestBuilder = when_execute(productId);

        //then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", equalTo("蘋果（澳洲）")))
                .andExpect(jsonPath("$.category", equalTo("FOOD")))
                .andExpect(jsonPath("$.imageUrl", notNullValue()))
                .andExpect(jsonPath("$.price", notNullValue()))
                .andExpect(jsonPath("$.stock", notNullValue()))
                .andExpect(jsonPath("$.description", notNullValue()))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }


    @Test
    public void getProduct_notFound() throws Exception {

        //given
        Integer productId = 20000;

        //when
        RequestBuilder requestBuilder = when_execute(productId);

        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    // 創建商品
    @Transactional
    @Test
    public void createProduct_success() throws Exception {

        //given
        ProductRequest productRequest = setProductRequest(
                "test food product",
                ProductCategory.FOOD,
                "http://test.com",
                100,
                2
        );

        //when
        RequestBuilder requestBuilder = when_execute(productRequest);

        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.productName", equalTo("test food product")))
                .andExpect(jsonPath("$.category", equalTo("FOOD")))
                .andExpect(jsonPath("$.imageUrl", equalTo("http://test.com")))
                .andExpect(jsonPath("$.price", equalTo(100)))
                .andExpect(jsonPath("$.stock", equalTo(2)))
                .andExpect(jsonPath("$.description", nullValue()))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }


    @Transactional
    @Test
    public void createProduct_illegalArgument() throws Exception {

        //given
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("test food product");

        //when
        RequestBuilder requestBuilder = when_execute(productRequest);

        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    // 更新商品
    @Transactional
    @Test
    public void updateProduct_success() throws Exception {

        //given
        Integer productId = 3;
        ProductRequest productRequest = setProductRequest(
                "test food product",
                ProductCategory.FOOD,
                "http://test.com",
                100,
                2
        );

        //when
        RequestBuilder requestBuilder = when_execute(productRequest, MockMvcRequestBuilders
                .put("/products/{productId}", productId));

        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.productName", equalTo("test food product")))
                .andExpect(jsonPath("$.category", equalTo("FOOD")))
                .andExpect(jsonPath("$.imageUrl", equalTo("http://test.com")))
                .andExpect(jsonPath("$.price", equalTo(100)))
                .andExpect(jsonPath("$.stock", equalTo(2)))
                .andExpect(jsonPath("$.description", nullValue()))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }

    private RequestBuilder when_execute(ProductRequest productRequest, MockHttpServletRequestBuilder productId) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = productId
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        return requestBuilder;
    }

    @Transactional
    @Test
    public void updateProduct_illegalArgument() throws Exception {

        //given
        Integer productId = 3;
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("test food product");

        //when
        RequestBuilder requestBuilder = when_execute(productRequest, MockMvcRequestBuilders
                .put("/products/{productId}", productId));

        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));

    }

    @Transactional
    @Test
    public void updateProduct_productNotFound() throws Exception {

        //given
        Integer productId = 20000;
        ProductRequest productRequest = setProductRequest(
                "test food product",
                ProductCategory.FOOD,
                "http://test.com",
                100,
                2
        );

        //when
        RequestBuilder requestBuilder = when_execute(productRequest, MockMvcRequestBuilders
                .put("/products/{productId}", productId));

        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    // 刪除商品
    @Transactional
    @Test
    public void deleteProduct_success() throws Exception {

        //given
        Integer productId = 5;

        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/{productId}", productId);

        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));
    }

    @Transactional
    @Test
    public void deleteProduct_deleteNonExistingProduct() throws Exception {

        //given
        Integer productId = 20000;

        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/{productId}", productId);

        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));
    }

    // 查詢商品列表
    @Test
    public void getProducts() throws Exception {
        
        //given
        String getAllProduct = "/products";
        
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(getAllProduct);
        
        //then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(5)));
    }

    @Test
    public void getProducts_filtering() throws Exception {
        
        //given
        String search = "B";
        String category = "CAR";
        
        //when
        RequestBuilder requestBuilder = when_execute("search", search, "category", category);

        //then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(2)));
    }

    @Test
    public void getProducts_sorting() throws Exception {
        
        //given
        String orderBy = "price";
        String sort = "desc";
        
        //when
        RequestBuilder requestBuilder = when_execute("orderBy", orderBy, "sort", sort);

        //then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(5)))
                .andExpect(jsonPath("$.results[0].productId", equalTo(6)))
                .andExpect(jsonPath("$.results[1].productId", equalTo(5)))
                .andExpect(jsonPath("$.results[2].productId", equalTo(7)))
                .andExpect(jsonPath("$.results[3].productId", equalTo(4)))
                .andExpect(jsonPath("$.results[4].productId", equalTo(2)));
    }

    @Test
    public void getProducts_pagination() throws Exception {
        
        //given
        String limit = "2";
        String offset = "2";
        
        //when
        RequestBuilder requestBuilder = when_execute("limit", limit, "offset", offset);

        //then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(2)))
                .andExpect(jsonPath("$.results[0].productId", equalTo(5)))
                .andExpect(jsonPath("$.results[1].productId", equalTo(4)));
    }
    
    private static ProductRequest setProductRequest(String testFoodProduct, ProductCategory productCategory, String url, int price, int stock) {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName(testFoodProduct);
        productRequest.setCategory(productCategory);
        productRequest.setImageUrl(url);
        productRequest.setPrice(price);
        productRequest.setStock(stock);
        return productRequest;
    }

    private static RequestBuilder when_execute(Integer productId) {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/{productId}", productId);
        return requestBuilder;
    }

    private RequestBuilder when_execute(ProductRequest productRequest) throws JsonProcessingException {
        RequestBuilder requestBuilder = when_execute(productRequest, MockMvcRequestBuilders
                .post("/products"));
        return requestBuilder;
    }

    private static RequestBuilder when_execute(String name1, String variable1, String name2, String variable2) {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param(name1, variable1)
                .param(name2, variable2);
        return requestBuilder;
    }
}