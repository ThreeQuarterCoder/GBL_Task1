import chain.*;
import model.Cuisine;
import model.Restaurant;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantRecommender {

    public MultiHandler buildOrderListsHandler(Cuisine primaryCuisine, List<Cuisine> secondaryCuisines, Integer primaryCost, List<Integer> secondaryCost) {
        MultiHandler rootHandler = new MultiHandler();
        FeaturedSortingHandler featuredSortingHandler = new FeaturedSortingHandler();
        CuisineSortingHandler primaryCuisineSortingHandler = new CuisineSortingHandler();
        primaryCuisineSortingHandler.addCuisine(primaryCuisine);
        CuisineSortingHandler secondaryCuisineSortingHandler = new CuisineSortingHandler();
        for(Cuisine cuisine: secondaryCuisines) {
            secondaryCuisineSortingHandler.addCuisine(cuisine);
        }
        CostBracketSortingHandler primaryCostBracketSortingHandler = new CostBracketSortingHandler();
        primaryCostBracketSortingHandler.addCostBracket(primaryCost);
        CostBracketSortingHandler secondaryCostBracketSortingHandler = new CostBracketSortingHandler();
        for(Integer cost: secondaryCost){
            secondaryCostBracketSortingHandler.addCostBracket(cost);
        }

        RatingSortingHandler ratingSortingHandlerGT4 = new RatingSortingHandler();
        ratingSortingHandlerGT4.setTargetRating(4.0f);
        ratingSortingHandlerGT4.setGreaterThan(true);

        RatingSortingHandler ratingSortingHandlerGT45 = new RatingSortingHandler();
        ratingSortingHandlerGT45.setTargetRating(4.5f);
        ratingSortingHandlerGT45.setGreaterThan(true);

        RatingSortingHandler ratingSortingHandlerLT4 = new RatingSortingHandler();
        ratingSortingHandlerGT4.setTargetRating(4.0f);
        ratingSortingHandlerGT4.setGreaterThan(false);

        RatingSortingHandler ratingSortingHandlerLT45 = new RatingSortingHandler();
        ratingSortingHandlerGT45.setTargetRating(4.5f);
        ratingSortingHandlerGT45.setGreaterThan(false);

        MultiHandler primaryCuisinePrimaryCostHandler = new MultiHandler();
        primaryCuisinePrimaryCostHandler.addHandler(primaryCuisineSortingHandler);
        primaryCuisinePrimaryCostHandler.addHandler(primaryCostBracketSortingHandler);

        MultiHandler primaryCuisineSecondaryCostHandler = new MultiHandler();
        primaryCuisineSecondaryCostHandler.addHandler(primaryCuisineSortingHandler);
        primaryCuisineSecondaryCostHandler.addHandler(secondaryCostBracketSortingHandler);

        MultiHandler secondaryCuisinePrimaryCostHandler = new MultiHandler();
        secondaryCuisinePrimaryCostHandler.addHandler(secondaryCuisineSortingHandler);
        secondaryCuisinePrimaryCostHandler.addHandler(primaryCostBracketSortingHandler);

        MultiHandler order1 = new MultiHandler();
        order1.addHandler(featuredSortingHandler);
        order1.addHandler(primaryCuisineSortingHandler);
        order1.addHandler(primaryCuisineSecondaryCostHandler);
        order1.addHandler(secondaryCuisinePrimaryCostHandler);

        rootHandler.addHandler(order1);

        MultiHandler order2 = new MultiHandler();
        order2.addHandler(primaryCuisineSortingHandler);
        order2.addHandler(ratingSortingHandlerGT4);

        rootHandler.addHandler(order2);

        MultiHandler order3 = new MultiHandler();
        order3.addHandler(primaryCuisineSecondaryCostHandler);
        order3.addHandler(ratingSortingHandlerGT45);

        rootHandler.addHandler(order3);

        MultiHandler order4 = new MultiHandler();
        order4.addHandler(secondaryCuisinePrimaryCostHandler);
        order4.addHandler(ratingSortingHandlerGT45);

        rootHandler.addHandler(order4);

        NewlyAddedSortingHandler order5 = new NewlyAddedSortingHandler();
        order5.setLimit(4);

        rootHandler.addHandler(order5);

        MultiHandler order6 = new MultiHandler();
        order6.addHandler(primaryCuisineSortingHandler);
        order6.addHandler(ratingSortingHandlerLT4);

        rootHandler.addHandler(order6);

        MultiHandler order7 = new MultiHandler();
        order7.addHandler(primaryCuisineSecondaryCostHandler);
        order7.addHandler(ratingSortingHandlerLT45);

        rootHandler.addHandler(order7);

        MultiHandler order8 = new MultiHandler();
        order8.addHandler(secondaryCuisinePrimaryCostHandler);
        order8.addHandler(ratingSortingHandlerGT45);

        rootHandler.addHandler(order8);

        return rootHandler;
    }
    public List<String> getRestaurantRecommendations(User user, List<Restaurant> availableRestaurants) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for(Restaurant r: availableRestaurants){
            restaurants.add(r);
        }

        Cuisine primaryCuisine = user.getPrimaryCuisine();
        List<Cuisine> secondaryCuisines = user.getSecondaryCuisines();

        Integer primaryCost = user.getPrimaryCostBracket();
        List<Integer> secondaryCost = user.getSecondaryCostBrackets();

        MultiHandler handler = buildOrderListsHandler(primaryCuisine, secondaryCuisines, primaryCost, secondaryCost);
        handler.handle(restaurants, 0, restaurants.size() - 1);

        List<String> result = restaurants.stream()
                .map(Restaurant::getRestaurantId)
                .collect(Collectors.toList());

        return result;
    }
}
