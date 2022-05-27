package sample.model;

import java.util.concurrent.atomic.AtomicLong;

public class BaseOrderItem {

    private static final AtomicLong count = new AtomicLong(0);

    private Long orderItemId;

    public BaseOrderItem(){
        this.orderItemId = count.incrementAndGet();
    }

    public Long getOrderItemId() {
        return orderItemId;
    }


}
