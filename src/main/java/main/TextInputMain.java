package main;

import controller.TextInputController;
import exception.ValidationException;
import model.Instruction;
import model.Plateau;
import model.Rover;

import java.text.MessageFormat;
import java.util.List;

public class TextInputMain {
    public static void main(String[] args){
        try {
            TextInputController controller = new TextInputController();
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
