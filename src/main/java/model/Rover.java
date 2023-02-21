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
    private final String ERR_OUT_OF_BOUND_POSX = "Invalid initial X. It is out of Plateau size.";
    private final String ERR_OUT_OF_BOUND_POSY = "Invalid initial Y. It is out of Plateau size.";
    @Min(value = 0, message = "Position X must be positive or 0")
    private int posX;
    @Min(value = 0, message = "Position Y must be positive or 0")
    private int posY;
    private Point position;

    private String dirStr;
    private Direction direction;

    private Plateau plateau;

    private String instruction;

    public Rover(int posX, int posY, String dirStr, Plateau plateau) throws ValidationException {
        this.posX = posX;
        this.posY = posY;
        this.dirStr = dirStr;
        this.plateau = plateau;
        validate();
        position = new Point(this.posX, this.posY);
        direction = Direction.valueOf(this.dirStr);
    }

    public Point getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void validate() throws ValidationException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Rover>> violations = validator.validate(this);
        for (ConstraintViolation<Rover> violation : violations) {
            throw new ValidationException(violation.getMessage());
        }

        if (!Direction.contains(dirStr)){
            throw new ValidationException(ERR_INVALID_DIRECTION);
        }

        if (plateau.getSize().width < posX){
            throw new ValidationException(ERR_OUT_OF_BOUND_POSX);
        }
        if (plateau.getSize().height < posY){
            throw new ValidationException(ERR_OUT_OF_BOUND_POSY);
        }
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public void go() {
        for (int i=0; i<instruction.length(); i++){
            if (instruction.charAt(i) == 'M'){
                switch (direction) {
                    case N -> position.translate(0, 1);
                    case E -> position.translate(1, 0);
                    case S -> position.translate(0, -1);
                    case W -> position.translate(-1, 0);
                    default -> {
                    } //ignore
                }
            }
        }
    }
}
