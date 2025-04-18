package org.kevinlin.springbootmall.service.impl;

import org.kevinlin.springbootmall.dao.ProductDao;
import org.kevinlin.springbootmall.model.Product;
import org.kevinlin.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdcutServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProudctById(productId);
    }



}
