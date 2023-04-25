package model;

public class CuisineTracking {
    private Cuisine type;
    private int noOfOrders;

    public CuisineTracking(Cuisine type, int noOfOrders) {
        this.type = type;
        this.noOfOrders = noOfOrders;
    }

    public Cuisine getType() {
        return type;
    }

    public int getNoOfOrders() {
        return noOfOrders;
    }
}
