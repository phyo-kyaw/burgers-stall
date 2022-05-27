package sample;

import sample.model.Item;
import sample.model.OrderItem;
import sample.model.SaleOrder;

import java.util.ArrayList;
import java.util.List;

public class OpService {

    List<Item> itemList = new ArrayList<Item>();
    List<SaleOrder> saleOrderList = new ArrayList<SaleOrder>();
    List<OrderItem> orderItemList = new ArrayList<OrderItem>();

    public OpService() {
        itemList.add(new Item("Hamburger", 10.50));
        itemList.add(new Item("Chips", 2.50));
        itemList.add(new Item("Coke", 2.00));
        itemList.add(new Item("Total", null));
        itemList.add(new Item("Changes", null));
        itemList.add(new Item("Paid", null));
    }

    private static OpService INSTANCE = new OpService();
    public static OpService getInstance() { return INSTANCE; }

    public List<Item> getItemList() {
        return itemList;
    }

    public List<SaleOrder> getSaleOrderList() {
        return saleOrderList;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

}
