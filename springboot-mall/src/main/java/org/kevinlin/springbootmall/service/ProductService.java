package org.kevinlin.springbootmall.service;

import jakarta.validation.Valid;
import org.kevinlin.springbootmall.constant.ProductCategory;
import org.kevinlin.springbootmall.dto.ProductRequest;
import org.kevinlin.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductCategory category,String search);

    Product getProductById(Integer productId);

    Integer createProduct(@Valid ProductRequest productRequest);

    void updateProduct(Integer productId, @Valid ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
