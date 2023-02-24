package main;

import controller.FileInputController;
import exception.ValidationException;

public class FileInputMain {
    public static void main(String[] args){
        try {
            FileInputController controller = new FileInputController("testfile/input-normal-3rovers.txt");
            Game game = new Game(controller);
            game.start();
        } catch (ValidationException e) {
            System.err.println(e.getMessage());
        }

    }
}
