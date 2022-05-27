package sample.model;

public class CheeseAddOn extends ItemDecorator {

    public CheeseAddOn(Item item) {
        this.item = item;
    }

    public String getName(){
        return item.getName() + " + Cheese";
    }

    public Double getPrice() {
        return  item.getPrice() + 1.00;
    }

}
