package org.kevinlin.springbootmall.dao;

import org.kevinlin.springbootmall.dto.ProductRequest;
import org.kevinlin.springbootmall.model.Product;

public interface ProductDao {
    Product getProudctById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
