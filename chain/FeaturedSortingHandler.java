package chain;

import model.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

public class FeaturedSortingHandler implements SortingHandler {
    private SortingHandler nextHandler;
    @Override
    public int handle(List<Restaurant> restaurants, int start, int end) {
        if((end - start + 1) < 0) {
            return start;
        }
        List<Restaurant> featuredRestaurants = restaurants.stream()
                .skip(start)
                .limit(end - start + 1)
                .filter(r -> r.isRecommended())
                .collect(Collectors.toList());
        List<Restaurant> otherRestaurants = restaurants.stream()
                .skip(start)
                .limit(end - start + 1)
                .filter(r -> !r.isRecommended())
                .collect(Collectors.toList());

        int splitIndex = featuredRestaurants.size() + start;
        restaurants.clear();
        restaurants.addAll(featuredRestaurants);
        restaurants.addAll(otherRestaurants);

        return splitIndex;
    }
    public void setNextHandler(SortingHandler nextHandler) {this.nextHandler = nextHandler;}
}
