package model;

import exception.ValidationException;
import util.RandomLocation;

import javax.validation.*;
import javax.validation.constraints.Min;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Plateau implements Scene{
    @Min(value = 1, message = "Width should be greater than 1")
    private int width;

    @Min(value = 1, message = "Height should be greater than 1")
    private int height;

    private Dimension size;

    private List<Sample> samples;

    private List<Rover> rovers;
    private List<Obstacle> obstacles;

    private int numOfSample;
    private int numOfObstacle;

    public Plateau (int width, int height) throws ValidationException {
        this.width = width;
        this.height = height;
        validate();
        this.size = new Dimension(width, height);
        samples = new ArrayList<>();
        numOfSample = width*height/10;
        numOfObstacle = width*height/10+1;
        rovers = new ArrayList<>();
        obstacles = new ArrayList<>();
    }
    public Dimension getSize() {
        return size;
    }

    public void validate() throws ValidationException{
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Plateau>> violations = validator.validate(this);
        for (ConstraintViolation<Plateau> violation : violations) {
            throw new ValidationException(violation.getMessage());
        }
    }
    
    public void generateSample(RandomLocation random) {
        List<Point> locations = random.generateLocationAvoidConflict(numOfSample);
        for (Point loc: locations) {
            samples.add(new Sample(loc));
        }
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public boolean hasSample(Point location){
        boolean hasSample = false;
        for (Sample sample: samples){
            if (location.equals(sample.getLocation()) && !sample.isCollected()){
                hasSample = true;
                break;
            }
        }
        return hasSample;
    }

    public Sample collectSample(Point location) {
        Sample result = null;
        for (Sample sample: samples){
            if (location.equals(sample.getLocation())){
                sample.setCollected(true);
                result = sample;
                break;
            }
        }
        return result;
    }

    public void addRovers(Rover rover) {
        this.rovers.add(rover);
    }

    public boolean hasOtherRover(Point location, Rover currRover){
        boolean hasObstacle = false;
        for (Rover rover: rovers){
            if (rover != currRover) {
                if (location.equals(rover.getPosition())) {
                    hasObstacle = true;
                    break;
                }
            }
        }
        if (hasObstacle)
            System.out.println("Rover at "+location);
        return hasObstacle;
    }

    public boolean hasObstacle(Point location){
        boolean hasObstacle = false;
        for (Obstacle obstacle: obstacles){
            if (location.equals(obstacle.getLocation())){
                hasObstacle = true;
                break;
            }
        }
        if (hasObstacle)
            System.out.println("Obstacle at "+location);
        return hasObstacle;

    }

    public void generateObstacle(RandomLocation random) {
        List<Point> locations = random.generateLocationAvoidConflict(numOfObstacle);
        for (Point loc: locations) {
            obstacles.add(new Obstacle(loc));
        }
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }
}