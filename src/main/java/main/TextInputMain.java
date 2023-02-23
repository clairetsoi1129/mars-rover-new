package main;

import controller.TextInputController;
import exception.ValidationException;

public class TextInputMain {
    public static void main(String[] args){
        try {
            TextInputController controller = new TextInputController();
            Game game = new Game(controller);
            game.start();
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
