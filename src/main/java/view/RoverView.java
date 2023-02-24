package view;

import model.MovingEntity;
import model.Rover;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
import java.util.Objects;

public class RoverView {
    private MovingEntity rover;
    private final String ROVER_IMG = "img/rover50x50-%s.png";
    private final int CELL_WIDTH = 50;
    private final int CELL_HEIGHT = 50;

    private JLabel roverLabel;
    private GraphicView gui;

    public RoverView(MovingEntity rover, GraphicView gui) {
        this.rover = rover;
        this.gui = gui;
        initUI();
    }

    private void initUI(){
        Point roverPos = gui.translateObjectPos(rover.getPosition());
        roverLabel = gui.createObject(1, roverPos.x, roverPos.y, String.format(ROVER_IMG, rover.getDirection()));
    }

    public void updateUI(boolean isGameEnd){
        Point positionOnBoard = gui.translateObjectPos(rover.getPosition());
        String img = String.format(ROVER_IMG, rover.getDirection());
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(img)));
        roverLabel.setIcon(icon);
        roverLabel.setBounds(positionOnBoard.x, positionOnBoard.y, CELL_WIDTH, CELL_HEIGHT);
        int basketSize = ((Rover)rover).getBasket().size();
        String steps = rover.getStepsTaken();
        String msg;

        if (isGameEnd)
            msg = MessageFormat.format("Congratulation! You have collected all the samples. Total samples collected is {0}. Your final position is ({1},{2}) and heading to {3}. Steps taken are {4}.",
                    basketSize, rover.getPosition().x, rover.getPosition().y, rover.getDirection(), steps);
        else
            msg = MessageFormat.format("Your new position is ({0},{1}) and heading to {2}. You got {3} sample(s) now. Steps: {4}.",
                    rover.getPosition().x, rover.getPosition().y, rover.getDirection(), basketSize, steps);

        gui.getMessageText().setText(msg);
    }
}
