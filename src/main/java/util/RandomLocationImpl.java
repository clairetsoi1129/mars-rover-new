package util;

import java.awt.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class RandomLocationImpl implements RandomLocation{
    private List<Point> generatedLocs;
    private List<Point> occupiedLocs;

    private Dimension dimension;

    public RandomLocationImpl(Dimension plateauSize) {
        this.dimension = plateauSize;
        this.occupiedLocs = new ArrayList<>();
    }

    private Point generateLocation(){
        return new Point(getRandomNumber(0, dimension.width), getRandomNumber(0, dimension.height));
    }

    private int getRandomNumber(int min, int max) {
        return new SecureRandom().nextInt(min, max);
    }

    public void generateLocationAvoidConflict(){
        Point location = generateLocation();
        while (hasConflict(location)){
            location = generateLocation();
        }
        generatedLocs.add(location);
        occupiedLocs.add(location);
    }

    public List<Point> generateLocationAvoidConflict(int times){
        generatedLocs = new ArrayList<>();
        for (int i=0; i<times; i++) {
            generateLocationAvoidConflict();
        }
        return generatedLocs;
    }

    private boolean hasConflict(Point location){
        boolean hasConflict = false;
        for (Point occupiedLoc : occupiedLocs) {
            if (location.getLocation().equals(occupiedLoc)) {
                hasConflict = true;
                break;
            }
        }
        return hasConflict;
    }
}
