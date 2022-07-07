package com.springbatch.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Data
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer customerId;
    private Integer itemId;
    private String itemName;
    private Integer itemPrice;
    private String purchaseDate;
}
