package main;

import controller.FileInputController;
import exception.ValidationException;
import model.Direction;
import model.Instruction;
import model.Plateau;
import model.Rover;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestFileInputMain {
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

            assertEquals(new Point(1,3), rover.getPosition());
            assertEquals(Direction.N, rover.getDirection());
            assertEquals(new Point(5,1), rover2.getPosition());
            assertEquals(Direction.E, rover2.getDirection());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNormalFile3Rovers() {
        try {
            FileInputController controller = new FileInputController("testfile/input-normal-3rovers.txt");
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

            Rover rover3 = new Rover(
                    instructions.get(2).getPositionX(), instructions.get(2).getPositionY(),
                    instructions.get(2).getDirection(), plateau
            );
            rover3.setMovement(instructions.get(2).getMovement());
            rover3.go();

            assertEquals(new Point(1,3), rover.getPosition());
            assertEquals(Direction.N, rover.getDirection());
            assertEquals(new Point(5,1), rover2.getPosition());
            assertEquals(Direction.E, rover2.getDirection());
            assertEquals(new Point(1,0), rover3.getPosition());
            assertEquals(Direction.S, rover3.getDirection());
        }catch (ValidationException ignored){

        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/fileinput-main-invalid-case.csv", numLinesToSkip = 1)
    void testInvalidCaseThrowException(
            String input, String expectedMessage) {
        Exception exception = assertThrows(ValidationException.class, () -> {
            FileInputController controller = new FileInputController(input);
            Plateau plateau = new Plateau(controller.getPlateauWidth(), controller.getPlateauHeight());
            List<Instruction> instructions = controller.getInstructions();

            Rover rover = new Rover(
                    instructions.get(0).getPositionX(), instructions.get(0).getPositionY(),
                    instructions.get(0).getDirection(), plateau
            );
            rover.setMovement(instructions.get(0).getMovement());
            rover.go();
        });

        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void testRoversCollision() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            FileInputController controller = new FileInputController("testfile/input-rovers-collision.txt");
            Plateau plateau = new Plateau(controller.getPlateauWidth(), controller.getPlateauHeight());
            List<Instruction> instructions = controller.getInstructions();

            Rover rover = new Rover(
                    instructions.get(0).getPositionX(), instructions.get(0).getPositionY(),
                    instructions.get(0).getDirection(), plateau
            );
            rover.setMovement(instructions.get(0).getMovement());
            rover.go();
            plateau.addRovers(rover);

            Rover rover2 = new Rover(
                    instructions.get(1).getPositionX(), instructions.get(1).getPositionY(),
                    instructions.get(1).getDirection(), plateau
            );
            rover2.setMovement(instructions.get(1).getMovement());
            rover2.go();

            assertEquals(new Point(1,3), rover.getPosition());
            assertEquals(Direction.N, rover.getDirection());
            assertEquals(new Point(1,2), rover2.getPosition());
            assertEquals(Direction.N, rover2.getDirection());
        });

        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,"Watch out! You hit obstacle.");
    }
}
