package com.maria_epikhova.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "REQUIREMENT", uniqueConstraints = { @UniqueConstraint(columnNames = "ID") })
public class Requirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    private int frequency;

    private int count;

    private String specialRequirements;

    @OneToOne(mappedBy = "requirement", cascade = CascadeType.ALL)
    private Product product;

    public Requirement(int id, int frequency, int count, String specialRequirements, Product product) {
        this.id = id;
        this.frequency = frequency;
        this.count = count;
        this.specialRequirements = specialRequirements;
        this.product = product;
    }

    public Requirement() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
