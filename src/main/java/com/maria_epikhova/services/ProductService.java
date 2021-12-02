package com.maria_epikhova.services;

import java.util.List;
import java.util.stream.Collectors;

import com.framework.annotations.Autowired;
import com.framework.annotations.Component;
import com.maria_epikhova.dto.products.ProductDto;
import com.maria_epikhova.models.Product;
import com.maria_epikhova.repos.ProductRepo;

@Component
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public boolean createProduct(ProductDto dto, int userId) {
        productRepo.addProduct(dto, userId);
        return true;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepo.getAllProducts();
        return products.stream().map(ProductDto::fromEntity).collect(Collectors.toList());
    }

    public Object updateProduct(ProductDto data) {
        return null;
    }
}
