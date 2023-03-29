/**
 * Classe permettant de réprésenter une facture
 */

package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bill {
    private Integer billId;
    private String date;
    private Float totalPrice;
    private int fk_customerId;
    private int bills_itemsId;
    private int fk_billId;
    private int fk_itemId;
    private Integer quantity;

    public Bill(Integer billId, Float totalPrice, int fk_customerId) {
        this.billId = billId;
        this.totalPrice = totalPrice;
        this.fk_customerId = fk_customerId;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.date = now.format(formatter);
    }

    public Bill(Float totalPrice, int fk_customerId) {
        this.totalPrice = totalPrice;
        this.fk_customerId = fk_customerId;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.date = now.format(formatter);
    }

    public Bill(Integer quantity, int fk_billId, int fk_itemId) {
        this.quantity = quantity;
        this.fk_billId = fk_billId;
        this.fk_itemId = fk_itemId;
    }

    public Bill(Integer billId){
        this.billId = billId;
    };

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getFk_customerId() {
        return fk_customerId;
    }

    public void setFk_customerId(int fk_customerId) {
        this.fk_customerId = fk_customerId;
    }

    public int getBills_itemsId() {
        return bills_itemsId;
    }

    public void setBills_itemsId(int bills_itemsId) {
        this.bills_itemsId = bills_itemsId;
    }

    public int getFk_billId() {
        return fk_billId;
    }

    public void setFk_billId(int fk_billId) {
        this.fk_billId = fk_billId;
    }

    public int getFk_itemId() {
        return fk_itemId;
    }

    public void setFk_itemId(int fk_itemId) {
        this.fk_itemId = fk_itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
