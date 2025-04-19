package org.kevinlin.springbootmall.dao;

import org.kevinlin.springbootmall.constant.ProductCategory;
import org.kevinlin.springbootmall.dto.ProductRequest;
import org.kevinlin.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts(ProductCategory category, String search);

    Product getProudctById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
