package com.maria_epikhova.repos;

import java.util.List;

import com.framework.annotations.Component;
import com.maria_epikhova.dto.products.ProductDto;
import com.maria_epikhova.exceptions.InvalidRequestException;
import com.maria_epikhova.models.Product;
import com.maria_epikhova.models.Requirement;
import com.maria_epikhova.models.User;
import com.maria_epikhova.utils.HibernateUtil;

import org.hibernate.Session;

@Component(name = "ProductRepo")
public class ProductRepo {
    public int addProduct(ProductDto dto, int userId) {
        Session session = HibernateUtil.getSession();

        session.beginTransaction();

        Requirement requirement = new Requirement();
        requirement.setCount(dto.getRequirement().getCount());
        requirement.setFrequency(dto.getRequirement().getFrequency());
        requirement.setSpecialRequirements(dto.getRequirement().getSpecialRequirements());

        session.save(requirement);

        User user = session.load(User.class, userId);

        Product product = new Product();

        product.setColor(dto.getColor());
        product.setDescription(dto.getDescription());
        product.setGender(dto.getGender());
        product.setSize(dto.getSize());
        product.setType(dto.getType());
        product.setImageUrl(dto.getImageUrl());
        product.setRequirement(requirement);
        product.setUser(user);
        product.setName(dto.getName());
        session.save(product);

        session.getTransaction().commit();

        return product.getProductId();
    }

    public List<Product> getAllProducts() {
        Session session = HibernateUtil.getSession();

        session.beginTransaction();
        List<Product> products = session.createQuery("SELECT a FROM Product a", Product.class).getResultList();
        session.getTransaction().commit();
        return products;
    }

    public void deleteProduct(int id) throws InvalidRequestException {
        Session session = HibernateUtil.getSession();

        session.beginTransaction();
        Product product = session.load(Product.class, id);

        if (product == null) {
            throw new InvalidRequestException("Product not found");
        }

        session.delete(product);
        session.getTransaction().commit();
    }
}
