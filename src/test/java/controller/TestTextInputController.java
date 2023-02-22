package controller;

import exception.ValidationException;
import model.Direction;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestTextInputController {

    @Test
    void testNormalTextInput1Rover() {
        String userInput = String.format("5 5%s1 2 N%sLMLMLMLMM%sN%s",
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);

        try {
            TextInputController controller = new TextInputController();
            assertNotNull(controller);
            assertEquals(5, controller.getPlateauWidth());
            assertEquals(5, controller.getPlateauHeight());
            assertEquals(1, controller.getInstructions().get(0).getPositionX());
            assertEquals(2, controller.getInstructions().get(0).getPositionY());
            assertEquals(Direction.N, controller.getInstructions().get(0).getDirection());
            assertEquals("LMLMLMLMM", controller.getInstructions().get(0).getMovement());
        }catch (ValidationException ignored){

        }
        System.setIn(System.in);
    }

    @Test
    void testNormalTextInput2Rover() {
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
            assertNotNull(controller);
            assertEquals(5, controller.getPlateauWidth());
            assertEquals(5, controller.getPlateauHeight());
            assertEquals(1, controller.getInstructions().get(0).getPositionX());
            assertEquals(2, controller.getInstructions().get(0).getPositionY());
            assertEquals(Direction.N, controller.getInstructions().get(0).getDirection());
            assertEquals("LMLMLMLMM", controller.getInstructions().get(0).getMovement());
            assertEquals(3, controller.getInstructions().get(1).getPositionX());
            assertEquals(3, controller.getInstructions().get(1).getPositionY());
            assertEquals(Direction.E, controller.getInstructions().get(1).getDirection());
            assertEquals("MMRMMRMRRM", controller.getInstructions().get(1).getMovement());
        }catch (ValidationException ignored){

        }
        System.setIn(System.in);
    }

    @Test
    void testNormalTextInput3Rover() {
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
            assertNotNull(controller);
            assertEquals(5, controller.getPlateauWidth());
            assertEquals(5, controller.getPlateauHeight());
            assertEquals(1, controller.getInstructions().get(0).getPositionX());
            assertEquals(2, controller.getInstructions().get(0).getPositionY());
            assertEquals(Direction.N, controller.getInstructions().get(0).getDirection());
            assertEquals("LMLMLMLMM", controller.getInstructions().get(0).getMovement());
            assertEquals(3, controller.getInstructions().get(1).getPositionX());
            assertEquals(3, controller.getInstructions().get(1).getPositionY());
            assertEquals(Direction.E, controller.getInstructions().get(1).getDirection());
            assertEquals("MMRMMRMRRM", controller.getInstructions().get(1).getMovement());
            assertEquals(0, controller.getInstructions().get(2).getPositionX());
            assertEquals(0, controller.getInstructions().get(2).getPositionY());
            assertEquals(Direction.S, controller.getInstructions().get(2).getDirection());
            assertEquals("M", controller.getInstructions().get(2).getMovement());
        }catch (ValidationException ignored){

        }
        System.setIn(System.in);
    }
}
