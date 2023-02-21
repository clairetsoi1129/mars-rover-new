import exception.ValidationException;
import model.Plateau;
import model.Rover;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRover {
    @Test
    void testNormalInitPosDirection() {
        try {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(2, 3, "N");
            assertEquals(new Point(2,3), rover.getPosition());
        }catch (ValidationException ignored){

        }
    }
}
