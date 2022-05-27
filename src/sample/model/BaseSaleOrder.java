package sample.model;

import java.util.concurrent.atomic.AtomicLong;

public class BaseSaleOrder {

    private static final AtomicLong count = new AtomicLong(0);

    private Long saleOrderId;

    public BaseSaleOrder(){
        this.saleOrderId = count.incrementAndGet();
    }

    public Long getSaleOrderId() {
        return saleOrderId;
    }


}
