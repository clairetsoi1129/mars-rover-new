package controller;

import exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.junit.jupiter.api.Assertions.*;

public class TestFileInputController {
    @Test
    void testNormalFile() {
        try {
            FileInputController controller = new FileInputController("testfile/input-normal.txt");
            assertNotNull(controller);
            assertEquals(5, controller.getPlateauWidth());
            assertEquals(5, controller.getPlateauHeight());
        }catch (ValidationException ignored){

        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/fileinput-invalid-case.csv", numLinesToSkip = 1)
    void testInvalidSizeThrowException(
            String input) {
        Exception exception = assertThrows(ValidationException.class, () -> {
            FileInputController controller = new FileInputController(input);
            assertNotNull(controller);
        });

        String expectedMessage = "Plateau size is invalid. Please input 2 integers and separated by space.";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }
}
