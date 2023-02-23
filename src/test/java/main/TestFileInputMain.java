package main;

import controller.FileInputController;
import exception.ValidationException;
import model.Direction;
import model.Rover;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import util.RandomLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestFileInputMain {
    @Mock
    RandomLocation random;

    @BeforeEach
    void init() {
        random = Mockito.mock(RandomLocation.class);

        List<Point> points = new ArrayList<>();
        points.add(new Point(1,1));
        points.add(new Point(2,2));
        lenient().when(random.generateLocationAvoidConflict(2)).thenReturn(points);

        points.add(new Point(2,1));
        points.add(new Point(3,2));
        points.add(new Point(4,3));
        lenient().when(random.generateLocationAvoidConflict(3)).thenReturn(points);
    }

    @Test
    void testNormalFile1Rover() {
        try {
            FileInputController controller = new FileInputController("testfile/input-normal-1rovers.txt");
            Game game = new Game(controller);
            game.start();

            assertEquals(new Dimension(5,5), game.getPlateau().getSize());
            assertEquals(new Point(1,3), game.getRovers().get(0).getPosition());
            assertEquals(Direction.N, game.getRovers().get(0).getDirection());
            assertEquals(0, ((Rover)game.getRovers().get(0)).getBasket().size());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNormalFile2Rovers() {
        try {
            FileInputController controller = new FileInputController("testfile/input-normal-2rovers.txt");
            Game game = new Game(controller);
            game.start();

            assertEquals(new Point(1,3), game.getRovers().get(0).getPosition());
            assertEquals(Direction.N, game.getRovers().get(0).getDirection());
            assertEquals(new Point(5,1), game.getRovers().get(1).getPosition());
            assertEquals(Direction.E, game.getRovers().get(1).getDirection());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNormalFile3Rovers() {
        try {
            FileInputController controller = new FileInputController("testfile/input-normal-3rovers.txt");
            Game game = new Game(controller);
            game.start();

            assertEquals(new Point(1,3), game.getRovers().get(0).getPosition());
            assertEquals(Direction.N, game.getRovers().get(0).getDirection());
            assertEquals(new Point(5,1), game.getRovers().get(1).getPosition());
            assertEquals(Direction.E, game.getRovers().get(1).getDirection());
            assertEquals(new Point(1,0), game.getRovers().get(2).getPosition());
            assertEquals(Direction.S, game.getRovers().get(2).getDirection());
        }catch (ValidationException ignored){

        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/fileinput-main-invalid-case.csv", numLinesToSkip = 1)
    void testInvalidCaseThrowException(
            String input, String expectedMessage) {
        Exception exception = assertThrows(ValidationException.class, () -> {
            FileInputController controller = new FileInputController(input);
            Game game = new Game(controller);
            game.start();
        });

        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void testRoversCollision() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            FileInputController controller = new FileInputController("testfile/input-rovers-collision.txt");
            Game game = new Game(controller);
            game.start();

            assertEquals(new Point(1,3), game.getRovers().get(0).getPosition());
            assertEquals(Direction.N, game.getRovers().get(0).getDirection());
            assertEquals(new Point(1,2), game.getRovers().get(1).getPosition());
            assertEquals(Direction.N, game.getRovers().get(1).getDirection());
        });

        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,"Watch out! You hit obstacle.");
    }
}
