package model;

import exception.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import java.awt.*;
import java.util.Set;

public class Rover {
    private final String ERR_INVALID_DIRECTION = "Invalid direction. Please use N,E,S,W.";
    @Min(value = 0, message = "Position X must be positive or 0")
    private int posX;
    @Min(value = 0, message = "Position Y must be positive or 0")
    private int posY;
    private Point position;

    private String dirStr;
    private Direction direction;

    public Rover(int posX, int posY, String dirStr) {
        this.posX = posX;
        this.posY = posY;
        this.dirStr = dirStr;
        position = new Point(this.posX, this.posY);
        direction = Direction.valueOf(this.dirStr);
    }

    public Point getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }
}
