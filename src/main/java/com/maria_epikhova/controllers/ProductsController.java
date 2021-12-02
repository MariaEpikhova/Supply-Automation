package com.maria_epikhova.controllers;

import java.util.List;

import com.framework.annotations.Autowired;
import com.framework.annotations.Controller;
import com.framework.annotations.Entrypoint;
import com.framework.annotations.Secured;
import com.framework.models.Request;
import com.maria_epikhova.auth.JwtUtils;
import com.maria_epikhova.dto.products.ProductDto;
import com.maria_epikhova.services.ProductService;

@Controller(path = "/products/")
public class ProductsController {
    @Autowired
    public ProductService productService;

    @Entrypoint(actionType = "ADD")
    @Secured
    boolean addProduct(Request<ProductDto> req) {
        String userId = (String) JwtUtils.decodeJWT(req.getToken()).get("user_id");

        return true;
    }

    @Entrypoint(actionType = "GET_ALL")
    @Secured
    List<ProductDto> getAllProducts(Request<Void> req) {
        return productService.getAllProducts();
    }

    @Entrypoint(actionType = "UPDATE")
    @Secured
    void updateProduct(Request<ProductDto> req) {
        productService.updateProduct(req.getData());
    }
}
