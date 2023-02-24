package main;

import controller.InputController;
import exception.ValidationException;
import model.*;
import util.RandomLocation;
import util.RandomLocationImpl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<MovingEntity> rovers;
    private Plateau plateau;

    public Game(InputController controller) throws ValidationException {
        init(controller, null);
    }

    public Game(InputController controller, RandomLocation random) throws ValidationException {
        init(controller, random);
    }

    public void init(InputController controller, RandomLocation random) throws ValidationException{
        rovers = new ArrayList<>();

        plateau = new Plateau(controller.getPlateauWidth(), controller.getPlateauHeight());
        if (random == null)
            random = new RandomLocationImpl(plateau.getSize());
        plateau.generateSample(random);
        plateau.generateObstacle(random);

        List<Sample> samples = plateau.getSamples();
        for (Sample sample: samples) {
            System.out.println("sample:"+sample.getLocation());
        }
        List<Obstacle> obstacles = plateau.getObstacles();
        for (Obstacle obstacle: obstacles) {
            System.out.println("obstacle:"+obstacle.getLocation());
        }

        List<Instruction> instructions = controller.getInstructions();
        for (Instruction instruction: instructions) {
            Rover rover = new Rover(
                    instruction.getPositionX(), instruction.getPositionY(),
                    instruction.getDirection(), plateau
            );
            rover.setMovement(instruction.getMovement());
            System.out.println(MessageFormat.format("Initial position: {0},{1}",
                    rover.getPosition(), rover.getDirection()));
            rovers.add(rover);
            plateau.addRovers(rover);
        }
    }

    public void start() throws ValidationException {
        if (!isGameEnd()) {
            for (MovingEntity rover : rovers) {
                rover.go();
                System.out.println(MessageFormat.format("Final position: {0},{1}",
                        rover.getPosition(), rover.getDirection()));
            }
        }
    }

    public List<MovingEntity> getRovers() {
        return rovers;
    }

    public Scene getPlateau() {
        return plateau;
    }

    public boolean isGameEnd(){
        return plateau.getSamples().size() == ((Rover)rovers.get(0)).getBasket().size();
    }
}
