package model;

import exception.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import java.awt.*;
import java.util.Set;

public abstract class MovingEntity {
    protected final String ERR_OUT_OF_BOUND_POSX = "Invalid position X. It is out of Plateau size.";
    protected final String ERR_OUT_OF_BOUND_POSY = "Invalid position Y. It is out of Plateau size.";
    @Min(value = 0, message = "Invalid position X. It is out of Plateau size.")
    protected int posX;
    @Min(value = 0, message = "Invalid position Y. It is out of Plateau size.")
    protected int posY;
    protected Point position;
    protected Direction direction;

    protected Scene plateau;
    protected String movement;
    protected String stepsTaken = "";

    public MovingEntity(int posX, int posY, Direction direction, Scene plateau) throws ValidationException {
        this.posX = posX;
        this.posY = posY;
        this.plateau = plateau;
        validate();
        position = new Point(this.posX, this.posY);
        this.direction = direction;
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
        Set<ConstraintViolation<MovingEntity>> violations = validator.validate(this);
        for (ConstraintViolation<MovingEntity> violation : violations) {
            throw new ValidationException(violation.getMessage());
        }

        if (plateau.getSize().width < posX){
            throw new ValidationException(ERR_OUT_OF_BOUND_POSX);
        }
        if (plateau.getSize().height < posY){
            throw new ValidationException(ERR_OUT_OF_BOUND_POSY);
        }
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }

    public abstract void go() throws ValidationException;

    public void rollback(){
        stepsTaken=stepsTaken.substring(0, stepsTaken.length()-1)+"[M]";
        switch (direction) {
            case N -> position.translate(0, -1);
            case E -> position.translate(-1, 0);
            case S -> position.translate(0, 1);
            case W -> position.translate(1, 0);
            default -> {
            } //ignore
        }
    }

    public String getStepsTaken() {
        return stepsTaken;
    }
}
