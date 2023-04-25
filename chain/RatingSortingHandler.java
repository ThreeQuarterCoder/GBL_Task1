package chain;

import model.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

public class RatingSortingHandler implements SortingHandler {
    private SortingHandler nextHandler;

    private Float targetRating;
    private Boolean greaterThan;

    public RatingSortingHandler() {
        this.greaterThan = true;
        this.targetRating = 0.0f;
    }

    public void setTargetRating(Float targetRating) {
        this.targetRating = targetRating;
    }

    public void setGreaterThan(Boolean greaterThan) {
        this.greaterThan = greaterThan;
    }

    @Override
    public int handle(List<Restaurant> restaurants, int start, int end) {
        if((end - start + 1) < 0) {
            return start;
        }
        List<Restaurant> ratingGTEQRestaurants = restaurants.stream()
                .skip(start)
                .limit(end - start + 1)
                .filter(r -> r.getRating() >= targetRating)
                .collect(Collectors.toList());

        List<Restaurant> ratingLTRestaurants = restaurants.stream()
                .filter(r -> r.getRating() < targetRating)
                .collect(Collectors.toList());
        restaurants.clear();

        int splitIndex=  0;

        if(greaterThan) {
            splitIndex = ratingGTEQRestaurants.size() + start;
            restaurants.addAll(ratingGTEQRestaurants);
            restaurants.addAll(ratingLTRestaurants);
        } else {
            splitIndex = ratingLTRestaurants.size() + start;
            restaurants.addAll(ratingLTRestaurants);
            restaurants.addAll(ratingGTEQRestaurants);
        }
        return splitIndex;
    }
    public void setNextHandler(SortingHandler nextHandler) {this.nextHandler = nextHandler;}
}
