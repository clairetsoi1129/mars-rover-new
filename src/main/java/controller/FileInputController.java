package controller;

import exception.ValidationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FileInputController {
    private final String ERR_SIZE_ROW = "Plateau size is invalid. Please input 2 integers and separated by space.";
    private String sizeRow;
    private int plateauWidth;
    private int plateauHeight;
    public FileInputController(String filename) throws ValidationException {
        try {
            File inputFile = new File(filename);
            Scanner scanner = new Scanner(inputFile);
            sizeRow = scanner.nextLine();
            validate();
            parseSize();
            scanner.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void parseSize(){
        String[] size = sizeRow.split(" ");
        plateauWidth = Integer.parseInt(size[0]);
        plateauHeight = Integer.parseInt(size[1]);
    }

    public int getPlateauWidth() {
        return plateauWidth;
    }

    public int getPlateauHeight() {
        return plateauHeight;
    }

    public void validate() throws ValidationException {
        String regex = "^[0-9]+\s[0-9]+$";
        boolean match = Pattern.compile(regex).matcher(sizeRow).matches();

        if (!match)
            throw new ValidationException(ERR_SIZE_ROW);
    }
}
