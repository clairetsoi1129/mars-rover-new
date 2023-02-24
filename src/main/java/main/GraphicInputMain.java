package main;

import controller.GraphicInputController;
import view.GraphicView;


public class GraphicInputMain {
    public static void main(String[] args) {
        GraphicView gui = new GraphicView();
        GraphicInputController controller = new GraphicInputController(gui);
    }
}
