package model;

import exception.ValidationException;

import java.awt.*;

public interface Scene {
    Dimension getSize();
    void validate() throws ValidationException;
}
