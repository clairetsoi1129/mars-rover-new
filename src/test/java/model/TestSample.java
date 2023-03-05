package model;

import exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import util.RandomLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestSample {
    @Mock
    RandomLocation random;

    @BeforeEach
    void init() {
        random = Mockito.mock(RandomLocation.class);

        List<Point> points = new ArrayList<>();
        points.add(new Point(1,1));
        points.add(new Point(2,2));

        lenient().when(random.generateLocationAvoidConflict(2)).thenReturn(points);
    }
    @Test
    void testNormalSample() throws ValidationException{
        Plateau plateau = new Plateau(5, 5);
        plateau.generateSample(random);
        Point sampleLoc = plateau.getSamples().get(0).getLocation();
        Point point = new Point(1,1);
        assertTrue(plateau.hasSample(point));
        assertEquals(point, sampleLoc);
        sampleLoc = plateau.getSamples().get(1).getLocation();
        point = new Point(2,2);
        assertTrue(plateau.hasSample(point));
        assertEquals(point, sampleLoc);
    }

    @Test
    void testNormalRoverCollectSample() throws ValidationException{
        Plateau plateau = new Plateau(5, 5);
        plateau.generateSample(random);
        Rover rover = new Rover(0,0, Direction.N, plateau);
        rover.setMovement("MRM");
        rover.go();
        assertEquals(1, rover.getBasket().size());
        assertTrue(rover.getBasket().get(0).isCollected());
    }
}
