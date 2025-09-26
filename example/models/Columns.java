package org.example.models;




import java.math.BigDecimal;


public class Columns {
    private int id;
    private String name;
    private BigDecimal price;
    private int quantity;
    private String sku;


    public Columns(int id, String name, BigDecimal price, int quantity, String sku) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sku = sku;
    }



}
