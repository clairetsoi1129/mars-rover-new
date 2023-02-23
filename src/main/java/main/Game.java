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

    private boolean isGameEnd;

    public Game(InputController controller) throws ValidationException {
        isGameEnd = false;
        rovers = new ArrayList<>();

        plateau = new Plateau(controller.getPlateauWidth(), controller.getPlateauHeight());
        RandomLocation random = new RandomLocationImpl(plateau.getSize());
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
                    instruction.getDirection(), (Plateau)plateau
            );
            rover.setMovement(instruction.getMovement());
            rovers.add(rover);
        }
    }

    public void start() throws ValidationException {
        for (MovingEntity rover: rovers) {
            rover.go();
            System.out.println(MessageFormat.format("Final position: {0},{1}",
                    rover.getPosition(), rover.getDirection()));
        }

    }

    public List<MovingEntity> getRovers() {
        return rovers;
    }

    public Scene getPlateau() {
        return plateau;
    }
}
