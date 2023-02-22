package main;

import controller.FileInputController;
import controller.TextInputController;
import exception.ValidationException;
import model.Direction;
import model.Instruction;
import model.Plateau;
import model.Rover;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTextInputMain {
    @Test
    void testNormalText1Rover() {
        String userInput = String.format("5 5%s1 2 N%sLMLMLMLMM%sN%s",
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);

        try {
            TextInputController controller = new TextInputController();
            Plateau plateau = new Plateau(controller.getPlateauWidth(), controller.getPlateauHeight());
            List<Instruction> instructions = controller.getInstructions();

            Rover rover = new Rover(
                    instructions.get(0).getPositionX(), instructions.get(0).getPositionY(),
                    instructions.get(0).getDirection(), plateau
            );
            rover.setMovement(instructions.get(0).getMovement());
            rover.go();
            assertNotNull(controller);
            assertEquals(new Dimension(5,5), plateau.getSize());
            assertEquals(new Point(1,3), rover.getPosition());
            assertEquals(Direction.N, rover.getDirection());
        }catch (ValidationException ignored){

        }
        System.setIn(System.in);
    }

    @Test
    void testNormalText2Rovers() {
        String userInput = String.format("5 5%s1 2 N%sLMLMLMLMM%sY%s3 3 E%sMMRMMRMRRM%sN%s",
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);
        try {
            TextInputController controller = new TextInputController();
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
        System.setIn(System.in);
    }

    @Test
    void testNormalText3Rovers() {
        String userInput = String.format("5 5%s1 2 N%sLMLMLMLMM%sY%s3 3 E%sMMRMMRMRRM%sY%s0 0 S%sM%sN%s",
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);

        try {
            TextInputController controller = new TextInputController();
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
    @CsvFileSource(resources = "/textinput-main-invalid-case.csv", numLinesToSkip = 1)
    void testInvalidCaseThrowException(
            String input, String expectedMessage) {
        Exception exception = assertThrows(ValidationException.class, () -> {
            String userInput = String.format(input,
                    System.lineSeparator(),
                    System.lineSeparator(),
                    System.lineSeparator(),
                    System.lineSeparator());
            ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
            System.setIn(bais);

            TextInputController controller = new TextInputController();
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
