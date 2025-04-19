package org.kevinlin.springbootmall.service.impl;

import org.kevinlin.springbootmall.dao.ProductDao;
import org.kevinlin.springbootmall.dto.ProductQueryParams;
import org.kevinlin.springbootmall.dto.ProductRequest;
import org.kevinlin.springbootmall.model.Product;
import org.kevinlin.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdcutServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Integer countProducts(ProductQueryParams productQueryParams) {
        return productDao.countProducts(productQueryParams);
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        return productDao.getProducts(productQueryParams);
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProudctById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }

}
