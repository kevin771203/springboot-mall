package org.kevinlin.springbootmall.dao;

import org.kevinlin.springbootmall.dto.ProductRequest;
import org.kevinlin.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts();

    Product getProudctById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
