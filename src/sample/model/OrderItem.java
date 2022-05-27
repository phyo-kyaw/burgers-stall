package sample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


public class OrderItem extends BaseOrderItem {

    //@JsonIgnoreProperties("orderItems")
    //private SaleOrder saleOrder;

    private Item item;
    private Integer quantity;
    private Double amount;



    public OrderItem() {
    }

    public OrderItem(Item item, Integer quantity, Double amount) {
        //this.saleOrder = saleOrder;
        this.item = item;
        this.quantity = quantity;
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

//    public SaleOrder getSaleOrder() {
//        return saleOrder;
//    }
//
//    public void setSaleOrder(SaleOrder saleOrder) {
//        this.saleOrder = saleOrder;
//    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
