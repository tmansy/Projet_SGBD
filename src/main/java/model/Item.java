/**
 * Classe permettant de réprésenter un article
 */

package model;

public class Item {
    private Integer itemId;
    private String name;
    private String brand;
    private float price;
    private Integer stock;
    private Integer quantity;
    private String lastname;

    public Item(Integer itemId, String name, String brand, float price, Integer stock) {
        this.itemId = itemId;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
    }

    public Item(String name, String brand, float price, Integer stock) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
    }

    public Item(String name, String brand, Float price, Integer quantity, String lastname) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.lastname = lastname;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
