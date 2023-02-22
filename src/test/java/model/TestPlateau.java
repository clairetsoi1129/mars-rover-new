package model;

import exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

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

    @ParameterizedTest
    @CsvFileSource(resources = "/plateau-invalid-case.csv", numLinesToSkip = 1)
    void testInvalidCaseThrowException(
            int inputWidth, int inputHeight, String expectedMessage) {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Plateau plateau = new Plateau(inputWidth, inputHeight);
        });

        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
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
}
