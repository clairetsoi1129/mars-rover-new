package view;

import model.Obstacle;
import model.Plateau;
import model.Sample;
import model.Scene;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlateauView {
    private Scene plateau;
    private GraphicView gui;

    private List<ObstacleView> obstacleUIs;
    private List<SampleView> sampleUIs;

    public PlateauView(Scene plateau, GraphicView gui) {
        this.plateau = plateau;
        this.gui = gui;
        initUI();
        initObstacleUI();
        initSampleUI();
    }

    private void initUI(){
        JPanel panel = gui.getBgPanel()[1];
        for (int row = 0; row < plateau.getSize().getWidth(); row++) {
            for (int col = 0; col < plateau.getSize().getHeight(); col++) {
                JLabel gridLabel = new JLabel();
                Point pos = gui.getGridPos(row, col);
                gridLabel.setBounds(pos.x, pos.y, 50, 50);
                gridLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                panel.add(gridLabel);
            }
        }
    }

    private void initObstacleUI(){
        obstacleUIs = new ArrayList<>();
        List<Obstacle> obstacles = ((Plateau)plateau).getObstacles();
        for (Obstacle obstacle : obstacles) {
            obstacleUIs.add(new ObstacleView(obstacle, gui));
        }
    }

    private void initSampleUI(){
        sampleUIs = new ArrayList<>();
        List<Sample> samples = ((Plateau)plateau).getSamples();
        for (Sample sample : samples) {
            sampleUIs.add(new SampleView(sample, gui));
        }
    }


    public void updateSampleUI(){
        List<Sample> samples = ((Plateau)plateau).getSamples();
        for (int i=0; i<samples.size(); i++) {
            if (samples.get(i).isCollected()) {
                sampleUIs.get(i).remove();
            }
        }
    }
}
