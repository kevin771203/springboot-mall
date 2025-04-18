package org.kevinlin.springbootmall.service;

import org.kevinlin.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);
}
