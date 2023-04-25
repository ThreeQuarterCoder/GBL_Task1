package model;

import model.CostTracking;
import model.Cuisine;
import model.CuisineTracking;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    private CuisineTracking[] cuisines;
    private CostTracking[] costBrackets;

    public User(CuisineTracking[] cuisines, CostTracking[] costBrackets) {
        this.cuisines = cuisines;
        this.costBrackets = costBrackets;
    }

    public CuisineTracking[] getCuisines() {
        return cuisines;
    }

    public CostTracking[] getCostBrackets() {
        return costBrackets;
    }

    public Cuisine getPrimaryCuisine() {
        return Arrays.stream(cuisines).max(Comparator.comparing(CuisineTracking::getNoOfOrders)).orElse(null).getType();
    }

    public List<Cuisine> getSecondaryCuisines() {
        Cuisine primaryCuisine = getPrimaryCuisine();
        return Arrays.stream(cuisines)
                .filter(obj -> obj.getType() != primaryCuisine)
                .sorted(Comparator.comparing(CuisineTracking::getNoOfOrders).reversed())
                .limit(2)
                .map(obj -> obj.getType())
                .collect(Collectors.toList());
    }

    public Integer getPrimaryCostBracket() {
        return Arrays.stream(costBrackets).max(Comparator.comparing(CostTracking::getNoOfOrders)).orElse(null).getType();
    }

    public List<Integer> getSecondaryCostBrackets() {
        Integer primaryCostBracket = getPrimaryCostBracket();
        return Arrays.stream(costBrackets)
                .filter(obj -> obj.getType()!=primaryCostBracket)
                .sorted(Comparator.comparing(CostTracking::getNoOfOrders).reversed())
                .limit(2)
                .map(obj -> obj.getType())
                .collect(Collectors.toList());
    }
}
