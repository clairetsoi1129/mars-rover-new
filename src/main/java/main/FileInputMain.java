package main;

import controller.FileInputController;
import exception.ValidationException;
import model.*;
import util.RandomLocation;
import util.RandomLocationImpl;

import java.text.MessageFormat;
import java.util.List;

public class FileInputMain {
    public static void main(String[] args){
        try {
            FileInputController controller = new FileInputController("testfile/input-normal-3rovers.txt");
            Plateau plateau = new Plateau(controller.getPlateauWidth(), controller.getPlateauHeight());
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
                        instruction.getDirection(), plateau
                        );
                System.out.println(MessageFormat.format("Init position: {0},{1} and {2}.",
                        rover.getPosition(), rover.getDirection(), instruction.getMovement()));
                rover.setMovement(instruction.getMovement());
                rover.go();
                System.out.println(MessageFormat.format("Final position: {0},{1} and got {2} samples.",
                        rover.getPosition(), rover.getDirection(), rover.getBasket().size()));
            }

        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }

    }
}
