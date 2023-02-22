package model;

import exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestRover {
    @Test
    void testNormalInitPosDirection() {
        try {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(2, 3, Direction.N, plateau);
            assertEquals(new Point(2,3), rover.getPosition());
            assertEquals(Direction.N, rover.getDirection());
        }catch (ValidationException ignored){

        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/rover-invalid-case.csv", numLinesToSkip = 1)
    void testInvalidCaseThrowException(
            int inputX, int inputY, Direction inputDir, String expectedMessage) {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(inputX, inputY, inputDir, plateau);
        });

        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/rover-valid-case.csv", numLinesToSkip = 1)
    void testNormalRoverMove(
            int inputX, int inputY, Direction inputDir, String movement,
            int expectedX, int expectedY, Direction expectedDir) {
        try {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(inputX, inputY, inputDir, plateau);
            rover.setMovement(movement);
            rover.go();
            assertEquals(new Point(expectedX,expectedY),rover.getPosition());
            assertEquals(expectedDir,rover.getDirection());
        }catch (ValidationException ignored){

        }
    }
}
