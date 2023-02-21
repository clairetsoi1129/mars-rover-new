package model;

import exception.ValidationException;

import java.util.regex.Pattern;

public class Instruction {
    private final String ERR_INIT_ROW = "Invalid initial position or direction. " +
            "Please input positive integer for x and y coordinates of Rover. " +
            "And input N/E/S/W for direction.";
    private final String ERR_MOVE_ROW = "Invalid movement. Please input L/R/M in one row.";
    private int positionX;
    private int positionY;
    private Direction direction;
    private String movement;

    private String initRow;

    public Instruction(String initRow, String moveRow) throws ValidationException{
        this.initRow = initRow;
        this.movement = moveRow;
        validate();
        parseInitRow();

    }

    public void parseInitRow(){
        String[] initRows = initRow.split(" ");
        positionX = Integer.parseInt(initRows[0]);
        positionY = Integer.parseInt(initRows[1]);
        direction = Direction.valueOf(initRows[2]);
    }

    public void validate() throws ValidationException {
        String initRowRegex = "^[0-9]+\s[0-9]+\s[NESWnesw]$";
        boolean initRowMatch = Pattern.compile(initRowRegex).matcher(initRow).matches();
        String moveRowRegex = "^[MLRmlr]+$";
        boolean moveRowMatch = Pattern.compile(moveRowRegex).matcher(movement).matches();

        if (!initRowMatch)
            throw new ValidationException(ERR_INIT_ROW);
        if (!moveRowMatch)
            throw new ValidationException(ERR_MOVE_ROW);
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getMovement() {
        return movement;
    }
}
