package com.sai.ecommerce.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data   //helpful in getter and setters
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private int id;

    private String name;


}
