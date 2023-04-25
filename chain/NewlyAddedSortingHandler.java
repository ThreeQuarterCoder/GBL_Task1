package chain;

import model.Restaurant;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NewlyAddedSortingHandler implements SortingHandler {
    private SortingHandler nextHandler;

    private Integer limit;

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public int handle(List<Restaurant> restaurants, int start, int end) {
        if((end - start + 1) < 0) {
            return start;
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -48);
        List<Restaurant> newlyAddedRestaurants = restaurants.stream()
                .skip(start)
                .limit(end - start + 1)
                .filter(r -> r.getOnboardedTime().after(cal.getTime()))
                .sorted((r1, r2) -> Float.compare(r1.getRating(), r2.getRating()))
                .limit(this.limit)
                .collect(Collectors.toList());

        List<Restaurant> otherRestaurants = restaurants.stream()
                .skip(start)
                .limit(end - start + 1)
                .filter(r -> !newlyAddedRestaurants.contains(r))
                .collect(Collectors.toList());

        int splitIndex = newlyAddedRestaurants.size() + start;
        restaurants.clear();
        restaurants.addAll(newlyAddedRestaurants);
        restaurants.addAll(otherRestaurants);

        return splitIndex;
    }
    public void setNextHandler(SortingHandler nextHandler) {this.nextHandler = nextHandler;}
}
