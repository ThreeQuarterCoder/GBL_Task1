package chain;

import model.Cuisine;
import model.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CuisineSortingHandler implements SortingHandler{
    private SortingHandler nextHandler;

    private List<Cuisine> targetCuisines;

    public CuisineSortingHandler() {
        this.targetCuisines = new ArrayList<Cuisine>();
    }

    public void addCuisine(Cuisine cuisine) {
        targetCuisines.add(cuisine);
    }

    @Override
    public int handle(List<Restaurant> restaurants, int start, int end) {
        if((end - start + 1) < 0) {
            return start;
        }
        List<Restaurant> cuisineRestaurants = restaurants.stream()
                .skip(start)
                .limit(end - start + 1)
                .filter(r -> targetCuisines.contains(r.getCuisine()))
                .collect(Collectors.toList());
        List<Restaurant> otherRestaurants = restaurants.stream()
                .skip(start)
                .limit(end - start + 1)
                .filter(r -> !targetCuisines.contains(r.getCuisine()))
                .collect(Collectors.toList());

        int splitIndex = cuisineRestaurants.size() + start;
        restaurants.clear();
        restaurants.addAll(cuisineRestaurants);
        restaurants.addAll(otherRestaurants);
        return splitIndex;
    }
    public void setNextHandler(SortingHandler nextHandler) {this.nextHandler = nextHandler;}
}
