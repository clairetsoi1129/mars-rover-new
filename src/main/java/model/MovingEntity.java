package model;

import exception.ValidationException;

public interface MovingEntity {
    void validate() throws ValidationException;
    void setMovement(String movement);
    void go() throws ValidationException;
    void rollback();
}
