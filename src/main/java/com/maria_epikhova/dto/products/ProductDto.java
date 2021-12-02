package com.maria_epikhova.dto.products;

import com.maria_epikhova.models.Product;
import com.maria_epikhova.models.Product.ClothesSize;
import com.maria_epikhova.models.Product.ClothesType;
import com.maria_epikhova.models.Product.Gender;

public class ProductDto {
    private String name;
    private double price;
    private ClothesSize size;
    private ClothesType type;
    private Gender gender;
    private Requirement requirement;
    private String color;
    private String description;
    private String imageUrl;

    public class Requirement {
        private int frequency;

        private int count;

        private String specialRequirements;

        public Requirement(int frequency, int count, String specialRequirements) {
            this.frequency = frequency;
            this.count = count;
            this.specialRequirements = specialRequirements;
        }

        public Requirement() {
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getSpecialRequirements() {
            return specialRequirements;
        }

        public void setSpecialRequirements(String specialRequirements) {
            this.specialRequirements = specialRequirements;
        }
    }

    public ProductDto() {

    }

    public ProductDto(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ClothesSize getSize() {
        return size;
    }

    public void setSize(ClothesSize size) {
        this.size = size;
    }

    public ClothesType getType() {
        return type;
    }

    public void setType(ClothesType type) {
        this.type = type;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static ProductDto fromEntity(Product productEnitity) {
        ProductDto product = new ProductDto();

        Requirement requirement = product.new Requirement(productEnitity.getRequirement().getFrequency(),
                productEnitity.getRequirement().getCount(), productEnitity.getRequirement().getSpecialRequirements());
        product.setColor(productEnitity.getColor());
        product.setDescription(productEnitity.getDescription());
        product.setGender(productEnitity.getGender());
        product.setSize(productEnitity.getSize());
        product.setType(productEnitity.getType());
        product.setImageUrl(productEnitity.getImageUrl());
        product.setRequirement(requirement);

        return product;
    }
}
