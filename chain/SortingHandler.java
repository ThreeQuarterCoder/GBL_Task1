package chain;

import model.Restaurant;

import java.util.List;

public interface SortingHandler {
    public int handle(List<Restaurant> restaurants, int start, int end);
    public void setNextHandler(SortingHandler nextHandler);
}
