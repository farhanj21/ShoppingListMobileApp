package com.example.smdassignment4;

public class ShoppingItem {
    private String itemName;
    private int quantity;
    private double price;

    // Default constructor needed for Firestore
    public ShoppingItem() {}

    public ShoppingItem(String itemName, int quantity, double price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
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
