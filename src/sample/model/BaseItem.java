package sample.model;

import java.util.concurrent.atomic.AtomicLong;

public class BaseItem {

    private static final AtomicLong count = new AtomicLong(0);

    private Long itemId;

    public BaseItem(){
        this.itemId = count.incrementAndGet();
    }

    public Long getItemId() {
        return itemId;
    }


}
