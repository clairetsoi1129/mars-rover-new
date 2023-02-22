package model;

import exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSample {
    @Test
    void testNormalSample() {
        try {
            Plateau plateau = new Plateau(5, 5);
            plateau.generateSample();
            Point sampleLoc = plateau.getSamples().get(0).getLocation();
            Point point = new Point(1,1);
            assertTrue(plateau.hasSample(point));
            assertEquals(point, sampleLoc);
            sampleLoc = plateau.getSamples().get(1).getLocation();
            point = new Point(2,2);
            assertTrue(plateau.hasSample(point));
            assertEquals(point, sampleLoc);
        }catch (ValidationException ignored){

        }
    }
}
