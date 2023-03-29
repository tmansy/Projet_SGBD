/**
 * Classe permettant de réprésenter un article d'une facture
 */

package model;

import java.text.DecimalFormat;
import java.util.Objects;

public class CartItem {
    private String name;
    private String brand;
    private Float price;
    private Integer quantity;
    private Float totalPrice;
    private String lastname;

    public CartItem(String name, String brand, Float price, Integer quantity) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }

    public CartItem(String name, String brand, Float price, Integer quantity, String lastname) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.lastname = lastname;
    }

    public CartItem(){}

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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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

    public Float getTotalPrice() {
        DecimalFormat df = new DecimalFormat("#.00");
        String totalPriceString = df.format(quantity * price);
        totalPriceString = totalPriceString.replace(",", ".");
        return Float.parseFloat(totalPriceString);
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CartItem)) {
            return false;
        }
        CartItem other = (CartItem) o;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, brand, price, quantity, totalPrice, getTotalPrice());
    }
}
