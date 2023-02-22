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
public class TestObstacle {
    @Mock
    RandomLocation random;

    @BeforeEach
    void init() {
        random = Mockito.mock(RandomLocation.class);

        List<Point> points = new ArrayList<>();
        points.add(new Point(2,1));
        points.add(new Point(3,2));
        points.add(new Point(4,3));

        lenient().when(random.generateLocationAvoidConflict(3)).thenReturn(points);
    }
    @Test
    void testNormalObstacle() {
        try {
            Plateau plateau = new Plateau(5, 5);
            plateau.generateSample(random);
            plateau.generateObstacle(random);
            Point obstacleLoc = plateau.getObstacles().get(0).getLocation();
            Point point = new Point(2,1);
            assertTrue(plateau.hasObstacle(point));
            assertEquals(point, obstacleLoc);
            obstacleLoc = plateau.getObstacles().get(1).getLocation();
            point = new Point(3,2);
            assertTrue(plateau.hasObstacle(point));
            assertEquals(point, obstacleLoc);
        }catch (ValidationException ignored){

        }
    }

}
