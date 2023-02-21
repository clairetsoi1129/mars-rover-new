package main;

import controller.FileInputController;
import exception.ValidationException;
import model.Direction;
import model.Instruction;
import model.Plateau;
import model.Rover;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFileInputMarsRover {
    @Test
    void testNormalFile1Rover() {
        try {
            FileInputController controller = new FileInputController("testfile/input-normal-1rovers.txt");
            Plateau plateau = new Plateau(controller.getPlateauWidth(), controller.getPlateauHeight());
            List<Instruction> instructions = controller.getInstructions();

            Rover rover = new Rover(
                    instructions.get(0).getPositionX(), instructions.get(0).getPositionY(),
                    instructions.get(0).getDirection(), plateau
            );
            rover.setMovement(instructions.get(0).getMovement());
            rover.go();

            assertEquals(new Dimension(5,5), plateau.getSize());
            assertEquals(new Point(1,3), rover.getPosition());
            assertEquals(Direction.N, rover.getDirection());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNormalFile2Rovers() {
        try {
            FileInputController controller = new FileInputController("testfile/input-normal-2rovers.txt");
            Plateau plateau = new Plateau(controller.getPlateauWidth(), controller.getPlateauHeight());
            List<Instruction> instructions = controller.getInstructions();

            Rover rover = new Rover(
                    instructions.get(0).getPositionX(), instructions.get(0).getPositionY(),
                    instructions.get(0).getDirection(), plateau
            );
            rover.setMovement(instructions.get(0).getMovement());
            rover.go();

            Rover rover2 = new Rover(
                    instructions.get(1).getPositionX(), instructions.get(1).getPositionY(),
                    instructions.get(1).getDirection(), plateau
            );
            rover2.setMovement(instructions.get(1).getMovement());
            rover2.go();

            assertEquals(new Point(5,1), rover2.getPosition());
            assertEquals(Direction.E, rover2.getDirection());
        }catch (ValidationException ignored){

        }
    }
}
