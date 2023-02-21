package model;

import exception.ValidationException;
import model.Direction;
import model.Plateau;
import model.Rover;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestRover {
    @Test
    void testNormalInitPosDirection() {
        try {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(2, 3, "N", plateau);
            assertEquals(new Point(2,3), rover.getPosition());
            assertEquals(Direction.N, rover.getDirection());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNegativeInitPositionX() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(-2, 3, "N", plateau);
        });

        String expectedMessage = "Position X must be positive or 0";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void testNegativeInitPositionY() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(2, -3, "N", plateau);
        });

        String expectedMessage = "Position Y must be positive or 0";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void testInitPositionXOutOfBound() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(6, 1, "N", plateau);
        });

        String expectedMessage = "Invalid initial X. It is out of Plateau size.";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void testInitPositionYOutOfBound() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(1, 6, "N", plateau);
        });

        String expectedMessage = "Invalid initial Y. It is out of Plateau size.";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void testInvalidDirection() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(1, 5, "R", plateau);
        });

        String expectedMessage = "Invalid direction. Please use N,E,S,W.";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    void testNormalRoverMoveNorthOneStep() {
        try {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(1, 1, "N", plateau);
            rover.setInstruction("M");
            rover.go();
            assertEquals(new Point(1,2),rover.getPosition());
            assertEquals(Direction.N,rover.getDirection());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNormalRoverMoveSouthOneStep() {
        try {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(1, 1, "S", plateau);
            rover.setInstruction("M");
            rover.go();
            assertEquals(new Point(1,0),rover.getPosition());
            assertEquals(Direction.S,rover.getDirection());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNormalRoverMoveEastOneStep() {
        try {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(1, 1, "E", plateau);
            rover.setInstruction("M");
            rover.go();
            assertEquals(new Point(2,1),rover.getPosition());
            assertEquals(Direction.E,rover.getDirection());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNormalRoverMoveWestOneStep() {
        try {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(1, 1, "W", plateau);
            rover.setInstruction("M");
            rover.go();
            assertEquals(new Point(0,1),rover.getPosition());
            assertEquals(Direction.W,rover.getDirection());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNormalRoverFaceNorthTurnLeft() {
        try {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(1, 1, "N", plateau);
            rover.setInstruction("L");
            rover.go();
            assertEquals(new Point(1,1),rover.getPosition());
            assertEquals(Direction.W,rover.getDirection());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNormalRoverFaceSouthTurnLeft() {
        try {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(1, 1, "S", plateau);
            rover.setInstruction("L");
            rover.go();
            assertEquals(new Point(1,1),rover.getPosition());
            assertEquals(Direction.E,rover.getDirection());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNormalRoverFaceEastTurnLeft() {
        try {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(1, 1, "E", plateau);
            rover.setInstruction("L");
            rover.go();
            assertEquals(new Point(1,1),rover.getPosition());
            assertEquals(Direction.N,rover.getDirection());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNormalRoverFaceWestTurnLeft() {
        try {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(1, 1, "W", plateau);
            rover.setInstruction("L");
            rover.go();
            assertEquals(new Point(1,1),rover.getPosition());
            assertEquals(Direction.S,rover.getDirection());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNormalRoverFaceNorthTurnRight() {
        try {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(1, 1, "N", plateau);
            rover.setInstruction("R");
            rover.go();
            assertEquals(new Point(1,1),rover.getPosition());
            assertEquals(Direction.E,rover.getDirection());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNormalRoverFaceSouthTurnRight() {
        try {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(1, 1, "S", plateau);
            rover.setInstruction("R");
            rover.go();
            assertEquals(new Point(1,1),rover.getPosition());
            assertEquals(Direction.W,rover.getDirection());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNormalRoverFaceEastTurnRight() {
        try {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(1, 1, "E", plateau);
            rover.setInstruction("R");
            rover.go();
            assertEquals(new Point(1,1),rover.getPosition());
            assertEquals(Direction.S,rover.getDirection());
        }catch (ValidationException ignored){

        }
    }

    @Test
    void testNormalRoverFaceWestTurnRight() {
        try {
            Plateau plateau = new Plateau(5, 5);
            Rover rover = new Rover(1, 1, "W", plateau);
            rover.setInstruction("R");
            rover.go();
            assertEquals(new Point(1,1),rover.getPosition());
            assertEquals(Direction.N,rover.getDirection());
        }catch (ValidationException ignored){

        }
    }
}
