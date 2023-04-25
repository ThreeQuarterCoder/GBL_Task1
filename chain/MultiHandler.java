package chain;

import model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class MultiHandler implements SortingHandler {
    private List<SortingHandler> handlers;
    private SortingHandler nextHandler;


    public MultiHandler() {
        this.handlers = new ArrayList<SortingHandler>();
    }

    public void addHandler(SortingHandler sortingHandler) {
        this.handlers.add(sortingHandler);
    }
    @Override
    public int handle(List<Restaurant> restaurants, int start, int end) {
        if((end - start + 1) < 0) {
            return start;
        }
        int sstart = start;
        int send = end;
        int splitIndex = start;
        for(SortingHandler sortingHandler: this.handlers) {
            splitIndex = sortingHandler.handle(restaurants, sstart, send);
            sstart = splitIndex;
        }
        return splitIndex;
    }

    @Override
    public void setNextHandler(SortingHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
