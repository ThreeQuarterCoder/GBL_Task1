package chain;

import model.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CostBracketSortingHandler implements SortingHandler {
    private SortingHandler nextHandler;

    private List<Integer> targetCostBrackets;

    public CostBracketSortingHandler() {
        this.targetCostBrackets = new ArrayList<>();
    }

    public void addCostBracket(Integer costBracket) {
        this.targetCostBrackets.add(costBracket);
    }

    @Override
    public int handle(List<Restaurant> restaurants, int start, int end) {
        if((end - start + 1) < 0) {
            return start;
        }
        List<Restaurant> costBracketRestaurants = restaurants.stream()
                .skip(start)
                .limit(end - start + 1)
                .filter(r -> this.targetCostBrackets.contains(r.getCostBracket()))
                .collect(Collectors.toList());

        List<Restaurant> otherRestaurants = restaurants.stream()
                .skip(start)
                .limit(end - start + 1)
                .filter(r -> !this.targetCostBrackets.contains(r.getCostBracket()))
                .collect(Collectors.toList());

        int splitIndex = costBracketRestaurants.size() + start;
        restaurants.clear();
        restaurants.addAll(costBracketRestaurants);
        restaurants.addAll(otherRestaurants);

        return splitIndex;
    }

    public void setNextHandler(SortingHandler nextHandler) {this.nextHandler = nextHandler;}
}
