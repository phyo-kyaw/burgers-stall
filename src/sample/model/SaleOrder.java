package sample.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class SaleOrder extends BaseSaleOrder {

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime localDateTime;
    @JsonIgnoreProperties("saleOrder")
    private List<OrderItem> orderItems;

    public SaleOrder() {
    }

    public SaleOrder(LocalDateTime localDateTime, List<OrderItem> orderItems) {
        super();
        this.localDateTime = localDateTime;
        this.orderItems = orderItems;
    }

    public SaleOrder(LocalDateTime localDateTime) {
        super();
        this.localDateTime = localDateTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}
