package main;

import controller.FileInputController;
import exception.ValidationException;
import model.Instruction;
import model.Plateau;
import model.Rover;

import java.text.MessageFormat;
import java.util.List;

public class FileInputMain {
    public static void main(String[] args){
        try {
            FileInputController controller = new FileInputController("testfile/input-normal-1rovers.txt");
            Plateau plateau = new Plateau(controller.getPlateauWidth(), controller.getPlateauHeight());
            List<Instruction> instructions = controller.getInstructions();
            for (Instruction instruction: instructions) {
                Rover rover = new Rover(
                        instruction.getPositionX(), instruction.getPositionY(),
                        instruction.getDirection(), plateau
                        );
                rover.setMovement(instruction.getMovement());
                rover.go();
                System.out.println(MessageFormat.format("Final position: {0},{1}",
                        rover.getPosition(), rover.getDirection()));
            }

        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }

    }
}
