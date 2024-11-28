package com.example.smdassignment4;

public class ShoppingItem {
    private String key;
    private String itemName;
    private int quantity;
    private double price;

    public ShoppingItem() {
        // Default constructor required for Firebase
    }

    public ShoppingItem(String itemName, int quantity, double price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
