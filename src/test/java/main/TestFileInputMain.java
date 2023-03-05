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

        List<Point> samples = new ArrayList<>();
        samples.add(new Point(1,1));
        samples.add(new Point(2,2));
        lenient().when(random.generateLocationAvoidConflict(2)).thenReturn(samples);

        List<Point> obstacles = new ArrayList<>();
        obstacles.add(new Point(2,1));
        obstacles.add(new Point(3,2));
        obstacles.add(new Point(4,3));
        lenient().when(random.generateLocationAvoidConflict(3)).thenReturn(obstacles);
    }

    @Test
    void testNormalFile1Rover()  throws ValidationException{
        FileInputController controller = new FileInputController("testfile/input-normal-1rovers.txt");
        Game game = new Game(controller, random);
        game.start();

        assertEquals(new Dimension(5,5), game.getPlateau().getSize());
        assertEquals(new Point(1,3), game.getRovers().get(0).getPosition());
        assertEquals(Direction.N, game.getRovers().get(0).getDirection());
        assertEquals(1, ((Rover)game.getRovers().get(0)).getBasket().size());
    }

    @Test
    void testNormalFile2RoverCollectSample() throws ValidationException{
        FileInputController controller = new FileInputController("testfile/input-sample.txt");
        Game game = new Game(controller, random);
        game.start();

        assertEquals(new Dimension(5,5), game.getPlateau().getSize());
        assertEquals(new Point(1,3), game.getRovers().get(0).getPosition());
        assertEquals(Direction.N, game.getRovers().get(0).getDirection());
        assertEquals(1, ((Rover)game.getRovers().get(0)).getBasket().size());

        assertEquals(new Point(5,5), game.getRovers().get(1).getPosition());
        assertEquals(Direction.N, game.getRovers().get(1).getDirection());
        assertEquals(0, ((Rover)game.getRovers().get(1)).getBasket().size());
    }

    @Test
    void testNormalFile2Rovers() throws ValidationException{
        FileInputController controller = new FileInputController("testfile/input-normal-2rovers.txt");
        Game game = new Game(controller, random);
        game.start();

        assertEquals(new Point(1,3), game.getRovers().get(0).getPosition());
        assertEquals(Direction.N, game.getRovers().get(0).getDirection());
        assertEquals(new Point(5,5), game.getRovers().get(1).getPosition());
        assertEquals(Direction.N, game.getRovers().get(1).getDirection());
    }

    @Test
    void testNormalFile3Rovers() throws ValidationException{
        FileInputController controller = new FileInputController("testfile/input-normal-3rovers.txt");
        Game game = new Game(controller, random);
        game.start();

        assertEquals(new Point(1,3), game.getRovers().get(0).getPosition());
        assertEquals(Direction.N, game.getRovers().get(0).getDirection());
        assertEquals(new Point(5,5), game.getRovers().get(1).getPosition());
        assertEquals(Direction.N, game.getRovers().get(1).getDirection());
        assertEquals(new Point(1,0), game.getRovers().get(2).getPosition());
        assertEquals(Direction.E, game.getRovers().get(2).getDirection());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/fileinput-main-invalid-case.csv", numLinesToSkip = 1)
    void testInvalidCaseThrowException(
            String input, String expectedMessage) {
        Exception exception = assertThrows(ValidationException.class, () -> {
            FileInputController controller = new FileInputController(input);
            Game game = new Game(controller, random);
            game.start();
        });

        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void testRoversCollision() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            FileInputController controller = new FileInputController("testfile/input-rovers-collision.txt");
            Game game = new Game(controller, random);
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
