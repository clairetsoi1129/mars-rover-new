package controller;

import exception.ValidationException;
import model.Instruction;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TextInputController {
    private final String MSG_WELCOME = "Welcome to Melody Mars Mission, you can have a wonderful experience " +
            "to move rovers around the surface of Mars!";
    private final String MSG_STEP_SIZE = "Please enter plateau size in integer (width height). eg. 5 6";
    private final String MSG_STEP_INIT = "Please enter rover initial position and direction, in format (x y direction). x and y must be integer and direction is N/E/S/W. eg. 1 2 N";

    private final String MSG_STEP_MOVE = "Please enter the instruction (LRM) to Rover in one row, eg. LRRMRRMM.";
    private final String MSG_STEP_CONT = "Do you want to create another rover? (y/n)";


    private final String ERR_SIZE_ROW = "Plateau size is invalid. Please input 2 integers and separated by space.";
    private String sizeRow;
    private List<Instruction> instructions;
    private int plateauWidth;
    private int plateauHeight;

    public TextInputController() throws ValidationException {
        instructions = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        System.out.println(MSG_WELCOME);
        System.out.println(MSG_STEP_SIZE);
        sizeRow = scanner.nextLine();
        validate();
        parseSize();

        do {
            System.out.println(MSG_STEP_INIT);
            Instruction instruction = new Instruction(scanner.nextLine());
            System.out.println(MSG_STEP_MOVE);
            instruction.setMovement(scanner.nextLine());
            instructions.add(instruction);
            System.out.println(MSG_STEP_CONT);
        } while (parseContinue(scanner.nextLine()));

        scanner.close();
    }

    public void parseSize() {
        String[] size = sizeRow.split(" ");
        plateauWidth = Integer.parseInt(size[0]);
        plateauHeight = Integer.parseInt(size[1]);
    }

    public boolean parseContinue(String continueStr) {
        return continueStr.equalsIgnoreCase("Y");
    }

    public int getPlateauWidth() {
        return plateauWidth;
    }

    public int getPlateauHeight() {
        return plateauHeight;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    private void validate() throws ValidationException {
        String regex = "^[0-9]+\s[0-9]+$";
        boolean match = Pattern.compile(regex).matcher(sizeRow).matches();

        if (!match)
            throw new ValidationException(ERR_SIZE_ROW);
    }
}
