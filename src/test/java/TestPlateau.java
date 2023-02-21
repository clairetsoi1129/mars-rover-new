import model.Plateau;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPlateau {
    @Test
    void testNormalSquarePlateauSize() {
        Dimension dim = new Dimension(5,5);
        Plateau plateau = new Plateau(dim);
        assertEquals(dim, plateau.getSize());
    }

    @Test
    void testNormalRectanglePlateauSize() {
        Dimension dim = new Dimension(8,5);
        Plateau plateau = new Plateau(dim);
        assertEquals(dim, plateau.getSize());
    }

}
