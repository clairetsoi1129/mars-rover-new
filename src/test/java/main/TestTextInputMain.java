package main;

import controller.TextInputController;
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
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestTextInputMain {
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
    void testNormalText1Rover() {
        String userInput = String.format("5 5%s1 2 N%sLMLMLMLMM%sN%s",
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);

        try {
            TextInputController controller = new TextInputController();
            Game game = new Game(controller, random);
            game.start();

            assertNotNull(controller);

            assertEquals(new Dimension(5,5), game.getPlateau().getSize());
            assertEquals(new Point(1,3), game.getRovers().get(0).getPosition());
            assertEquals(Direction.N, game.getRovers().get(0).getDirection());
            assertEquals(0, ((Rover)game.getRovers().get(0)).getBasket().size());
        }catch (ValidationException ignored){

        }
        System.setIn(System.in);
    }

    @Test
    void testNormalText2Rovers() {
        String userInput = String.format("5 5%s1 2 N%sLMLMLMLMM%sY%s3 3 E%sMMRMMRMRRM%sN%s",
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);
        try {
            TextInputController controller = new TextInputController();
            Game game = new Game(controller, random);
            game.start();

            assertEquals(new Point(1,3), game.getRovers().get(0).getPosition());
            assertEquals(Direction.N, game.getRovers().get(0).getDirection());
            assertEquals(new Point(5,1), game.getRovers().get(1).getPosition());
            assertEquals(Direction.E, game.getRovers().get(1).getDirection());
        }catch (ValidationException ignored){

        }
        System.setIn(System.in);
    }

    @Test
    void testNormalText3Rovers() {
        String userInput = String.format("5 5%s1 2 N%sLMLMLMLMM%sY%s3 3 E%sMMRMMRMRRM%sY%s0 0 S%sM%sN%s",
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);

        try {
            TextInputController controller = new TextInputController();
            Game game = new Game(controller, random);
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
    @CsvFileSource(resources = "/textinput-main-invalid-case.csv", numLinesToSkip = 1)
    void testInvalidCaseThrowException(
            String input, String expectedMessage) {
        Exception exception = assertThrows(ValidationException.class, () -> {
            String userInput = String.format(input,
                    System.lineSeparator(),
                    System.lineSeparator(),
                    System.lineSeparator(),
                    System.lineSeparator());
            ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
            System.setIn(bais);

            TextInputController controller = new TextInputController();
            Game game = new Game(controller, random);
            game.start();
        });

        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void testRoversCollision() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            String userInput = String.format("5 5%s1 2 N%sLMLMLMLMM%sY%s1 2 N%sLMLMLMLMMM%sN%s",
                    System.lineSeparator(),
                    System.lineSeparator(),
                    System.lineSeparator(),
                    System.lineSeparator(),
                    System.lineSeparator(),
                    System.lineSeparator(),
                    System.lineSeparator());
            ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
            System.setIn(bais);

            TextInputController controller = new TextInputController();
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
