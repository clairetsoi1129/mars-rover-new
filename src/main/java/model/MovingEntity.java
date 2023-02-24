package model;

import exception.ValidationException;

import java.awt.*;

public interface MovingEntity {
    void validate() throws ValidationException;
    void setMovement(String movement);
    void go() throws ValidationException;
    void rollback();
    Point getPosition();
    Direction getDirection();
    String getStepsTaken();
}
