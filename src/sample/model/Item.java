package sample.model;


public class Item extends BaseItem {

    private String name;
    private Double price;

    public Item() {
    }

    public Item(String name, Double price) {
        super();
        this.name = name;
        this.price = price;
    }

    public Long getItemId() {
        return super.getItemId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
