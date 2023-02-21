package model;

import exception.ValidationException;
import model.Plateau;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlateau {
    @Test
    void testNormalSquarePlateauSize() {
        try {
            Plateau plateau = new Plateau(5, 5);
            assertEquals(new Dimension(5, 5), plateau.getSize());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNormalRectanglePlateauSize() {
        try {
            Plateau plateau = new Plateau(8,5);
            assertEquals(new Dimension(8,5), plateau.getSize());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNegativePlateauWidth() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Plateau plateau = new Plateau(-1,1);
        });

        String expectedMessage = "Width should be greater than 1";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void testNegativePlateauHeight() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Plateau plateau = new Plateau(1,-1);
        });

        String expectedMessage = "Height should be greater than 1";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void testZeroPlateauHeight() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Plateau plateau = new Plateau(0,1);
        });

        String expectedMessage = "Width should be greater than 1";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void testZeroPlateauWidth() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Plateau plateau = new Plateau(1,0);
        });

        String expectedMessage = "Height should be greater than 1";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }
}
