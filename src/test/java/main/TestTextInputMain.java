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
    void testNormalText1Rover() {
        String userInput = String.format("5 5%s1 2 N%sLMLMLMLMM%sN%s",
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);

        TextInputController controller = null;
        Game game = null;

        try {
            controller = new TextInputController();
            game = new Game(controller, random);
            game.start();
        }catch (ValidationException e){
            e.printStackTrace();
        }
        assertNotNull(controller);

        assertEquals(new Dimension(5,5), game.getPlateau().getSize());
        assertEquals(new Point(1,3), game.getRovers().get(0).getPosition());
        assertEquals(Direction.N, game.getRovers().get(0).getDirection());
        assertEquals(1, ((Rover)game.getRovers().get(0)).getBasket().size());

        System.setIn(System.in);
    }

    @Test
    void testNormalText2Rovers() {
        String userInput = String.format("5 5%s1 2 N%sLMLMLMLMM%sY%s3 3 E%sLMMRMMRMRRM%sN%s",
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);

        TextInputController controller;
        Game game = null;

        try {
            controller = new TextInputController();
            game = new Game(controller, random);
            game.start();
        }catch (ValidationException e){
            e.printStackTrace();
        }
        assertEquals(new Point(1,3), game.getRovers().get(0).getPosition());
        assertEquals(Direction.N, game.getRovers().get(0).getDirection());
        assertEquals(new Point(5,5), game.getRovers().get(1).getPosition());
        assertEquals(Direction.N, game.getRovers().get(1).getDirection());

        System.setIn(System.in);
    }

    @Test
    void testNormalText3Rovers() {
        String userInput = String.format("5 5%s1 2 N%sLMLMLMLMM%sY%s3 3 E%sLMMRMMRMRRM%sY%s0 0 E%sM%sN%s",
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

        TextInputController controller;
        Game game = null;

        try {
            controller = new TextInputController();
            game = new Game(controller, random);
            game.start();
        }catch (ValidationException e){
            e.printStackTrace();
        }
        assertEquals(new Point(1,3), game.getRovers().get(0).getPosition());
        assertEquals(Direction.N, game.getRovers().get(0).getDirection());
        assertEquals(new Point(5,5), game.getRovers().get(1).getPosition());
        assertEquals(Direction.N, game.getRovers().get(1).getDirection());
        assertEquals(new Point(1,0), game.getRovers().get(2).getPosition());
        assertEquals(Direction.E, game.getRovers().get(2).getDirection());

        System.setIn(System.in);
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
