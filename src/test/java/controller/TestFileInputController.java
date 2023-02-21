package controller;

import exception.ValidationException;
import model.Direction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.junit.jupiter.api.Assertions.*;

public class TestFileInputController {
    @Test
    void testNormalFile1Rover() {
        try {
            FileInputController controller = new FileInputController("testfile/input-normal-1rovers.txt");
            assertNotNull(controller);
            assertEquals(5, controller.getPlateauWidth());
            assertEquals(5, controller.getPlateauHeight());
            assertEquals(1, controller.getInstructions().get(0).getPositionX());
            assertEquals(2, controller.getInstructions().get(0).getPositionY());
            assertEquals(Direction.N, controller.getInstructions().get(0).getDirection());
            assertEquals("LMLMLMLMM", controller.getInstructions().get(0).getMovement());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNormalFile1RoverSmallLetter() {
        try {
            FileInputController controller = new FileInputController("testfile/input-normal-1rovers-small-letter.txt");
            assertNotNull(controller);
            assertEquals(5, controller.getPlateauWidth());
            assertEquals(5, controller.getPlateauHeight());
            assertEquals(1, controller.getInstructions().get(0).getPositionX());
            assertEquals(2, controller.getInstructions().get(0).getPositionY());
            assertEquals(Direction.N, controller.getInstructions().get(0).getDirection());
            assertEquals("LMLMLMLMM", controller.getInstructions().get(0).getMovement());
        }catch (ValidationException ignored){

        }
    }
    @Test
    void testNormalFile2Rovers() {
        try {
            FileInputController controller = new FileInputController("testfile/input-normal-2rovers.txt");
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
    }

    @Test
    void testNormalFile3Rovers() {
        try {
            FileInputController controller = new FileInputController("testfile/input-normal-3rovers.txt");
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
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/fileinput-invalid-case.csv", numLinesToSkip = 1)
    void testInvalidCaseThrowException(
            String input, String expectedMessage) {
        Exception exception = assertThrows(ValidationException.class, () -> {
            FileInputController controller = new FileInputController(input);
            assertNotNull(controller);
        });

        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }
}
