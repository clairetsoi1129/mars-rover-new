import exception.ValidationException;
import model.Direction;
import model.Plateau;
import model.Rover;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestRover {
    @Test
    void testNormalInitPosDirection() {
        try {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(2, 3, "N", plateau);
            assertEquals(new Point(2,3), rover.getPosition());
            assertEquals(Direction.N, rover.getDirection());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNegativeInitPositionX() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(-2, 3, "N", plateau);
        });

        String expectedMessage = "Position X must be positive or 0";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void testNegativeInitPositionY() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(2, -3, "N", plateau);
        });

        String expectedMessage = "Position Y must be positive or 0";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void testInitPositionXOutOfBound() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(6, 1, "N", plateau);
        });

        String expectedMessage = "Invalid initial X. It is out of Plateau size.";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void testInitPositionYOutOfBound() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(1, 6, "N", plateau);
        });

        String expectedMessage = "Invalid initial Y. It is out of Plateau size.";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void testInvalidDirection() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(1, 5, "R", plateau);
        });

        String expectedMessage = "Invalid direction. Please use N,E,S,W.";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }
}
