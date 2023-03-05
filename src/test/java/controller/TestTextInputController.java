package controller;

import exception.ValidationException;
import model.Direction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestTextInputController {

    @Test
    void testNormalTextInput1Rover()  throws ValidationException{
        String userInput = String.format("5 5%s1 2 N%sLMLMLMLMM%sN%s",
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);

        TextInputController controller = new TextInputController();

        assertNotNull(controller);
        assertEquals(5, controller.getPlateauWidth());
        assertEquals(5, controller.getPlateauHeight());
        assertEquals(1, controller.getInstructions().get(0).getPositionX());
        assertEquals(2, controller.getInstructions().get(0).getPositionY());
        assertEquals(Direction.N, controller.getInstructions().get(0).getDirection());
        assertEquals("LMLMLMLMM", controller.getInstructions().get(0).getMovement());

        System.setIn(System.in);
    }

    @Test
    void testNormalTextInput2Rover()  throws ValidationException{
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

        System.setIn(System.in);
    }

    @Test
    void testNormalTextInput3Rover()  throws ValidationException{
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

        System.setIn(System.in);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/textinput-invalid-case.csv", numLinesToSkip = 1)
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
            assertNotNull(controller);

            System.setIn(System.in);
        });

        String actualMessage = exception.getMessage();
        assertEquals(actualMessage,expectedMessage);
    }
}
