package view;

import model.Obstacle;

import javax.swing.*;
import java.awt.*;

public class ObstacleView {
    private Obstacle obstacle;
    private final String OBSTACLE_IMG = "img/stone50x50.png";

    private JLabel obstacleLabel;
    private GraphicView gui;

    public ObstacleView(Obstacle obstacle, GraphicView gui) {
        this.obstacle = obstacle;
        this.gui = gui;
        initUI();
    }

    private void initUI(){
        JPanel panel = gui.getBgPanel()[1];
        Point obstaclePos = gui.translateObjectPos(obstacle.getLocation());
        obstacleLabel = gui.createObject(1, obstaclePos.x, obstaclePos.y, OBSTACLE_IMG);
        panel.add(obstacleLabel);
    }
}
