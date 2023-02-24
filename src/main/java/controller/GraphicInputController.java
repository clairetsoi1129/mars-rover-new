package controller;

import exception.ValidationException;
import main.Game;
import model.Instruction;
import view.GraphicView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GraphicInputController implements InputController{
    public static final String ERR_MSG_INVALID_SIZE_GUI = "Input is not a valid size. Please input integer which is greater than or equals to 1 only. Max allowable size is 15 x 9.";
    public static final String MSG_GET_PLATEAU_SIZE_GUI = "Please enter plateau size (width x height).";

    public static final String MSG_INIT_POS_X_Y_GUI = "Rover initial position in integer, (x,y):";
    public static final String MSG_INIT_DIR = "Rover initial facing direction (N,E,S,W): ";

    private JTextField widthText;
    private JTextField heightText;
    private JTextField roverXText;
    private JTextField roverYText;
    private JTextField roverDirText;

    private final int BTN_CONT_WIDTH = 50;
    private final int BTN_CONT_HEIGHT = 50;

    private final String ARROW_IMG = "img/arrow50x50.png";

    private final String CMD_GOTO_PLAY = "gotoPlay";
    private final String CMD_GOTO_INPUT = "gotoInput";

    private GraphicView gui;
    private int plateauWidth;
    private int plateauHeight;

    private List<Instruction> instructions;

    private String roverInitX;
    private String roverInitY;
    private String roverInitDir;

    private Game game;

    public GraphicInputController(GraphicView gv){
        this.gui = gv;
        initUI();
    }

    private void initUI(){
        initPlateauSizeUI();
        initRoverPosUI();
        initContinueButton();
    }

    private void initPlateauSizeUI(){
        int labelWidth = 300;
        int labelHeight = 50;
        int labelX = 100;
        int labelY = 100;
        int textFieldWidth = 50;
        int textFieldX = labelX + labelWidth;
        int textFieldY = labelY;
        JPanel panel = gui.getBgPanel()[0];

        JLabel plateauSizeLabel = new JLabel();
        plateauSizeLabel.setName("plateauSizeLabel");
        plateauSizeLabel.setText(MSG_GET_PLATEAU_SIZE_GUI);
        plateauSizeLabel.setForeground(Color.WHITE);
        plateauSizeLabel.setBounds(labelX, labelY, labelWidth, labelHeight);

        widthText = new JTextField();
        widthText.setName("widthText");
        heightText = new JTextField();
        heightText.setName("heightText");
        widthText.setBounds(textFieldX, textFieldY, textFieldWidth, labelHeight);
        heightText.setBounds(textFieldX + 50, textFieldY, textFieldWidth, labelHeight);

        panel.add(plateauSizeLabel);
        panel.add(widthText);
        panel.add(heightText);
    }

    private void initRoverPosUI(){
        int labelWidth = 300;
        int labelHeight = 50;
        int labelX = 100;
        int labelY = 200;
        int textFieldWidth = 50;
        int textFieldX = 400;
        int textFieldY = labelY;
        JPanel panel = gui.getBgPanel()[0];

        JLabel roverPosLabel = new JLabel(MSG_INIT_POS_X_Y_GUI);
        roverPosLabel.setName("roverPosLabel");
        roverPosLabel.setBounds(labelX, labelY, labelWidth, labelHeight);
        roverPosLabel.setForeground(Color.WHITE);

        JLabel roverDirLabel = new JLabel(MSG_INIT_DIR);
        roverDirLabel.setName("roverDirLabel");
        roverDirLabel.setBounds(labelX, labelY + 50, labelWidth, labelHeight);
        roverDirLabel.setForeground(Color.WHITE);

        roverXText = new JTextField();
        roverXText.setName("roverXText");
        roverYText = new JTextField();
        roverYText.setName("roverYText");
        roverDirText = new JTextField();
        roverDirText.setName("roverDirText");
        roverXText.setBounds(textFieldX, textFieldY, textFieldWidth, labelHeight);
        roverYText.setBounds(textFieldX + 50, textFieldY, textFieldWidth, labelHeight);
        roverDirText.setBounds(textFieldX, textFieldY+50, textFieldWidth, labelHeight);

        panel.add(roverPosLabel);
        panel.add(roverDirLabel);
        panel.add(roverXText);
        panel.add(roverYText);
        panel.add(roverDirText);
    }

    private void initContinueButton(){
        initButton(CMD_GOTO_PLAY);
    }

    private void initButton(String cmd){
        JPanel panel = gui.getBgPanel()[0];

        ImageIcon arrowIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(ARROW_IMG)));
        JButton arrowButton = new JButton();
        arrowButton.setName("arrowButton");
        arrowButton.setBounds(400, 400, BTN_CONT_WIDTH, BTN_CONT_HEIGHT);
        arrowButton.setBackground(null);
        arrowButton.setContentAreaFilled(false);
        arrowButton.setFocusPainted(false);
        arrowButton.setIcon(arrowIcon);
        arrowButton.addActionListener(ev -> {
            if (CMD_GOTO_PLAY.equals(cmd)) {
                System.out.println(CMD_GOTO_PLAY);
                try {
//                    arrowButton.setBorderPainted(true);
                    parseSize();
                    parseInitPosDir();
                    game = new Game(this);
                    gui.setGame(game);
                    gui.initGameBoardComponents();
                    gui.showPlayScreen();
                } catch (ValidationException e) {
                    e.printStackTrace();
                    gui.getMessageText().setText(e.getMessage());
                }
            } else if (CMD_GOTO_INPUT.equals(cmd)) {
                gui.showInputScreen();
            }
        });
        arrowButton.setActionCommand(CMD_GOTO_PLAY);
        arrowButton.setBorderPainted(false);
        panel.add(arrowButton);
    }

    @Override
    public void parseSize() {
        try{
            try {
                plateauWidth = Integer.parseInt(widthText.getText());
                plateauHeight = Integer.parseInt(heightText.getText());
            }catch (NumberFormatException e){
                throw new ValidationException(ERR_MSG_INVALID_SIZE_GUI);
            }
            if (plateauWidth > 15 || plateauHeight > 9 || plateauWidth < 1 || plateauHeight < 1)
                throw new ValidationException(ERR_MSG_INVALID_SIZE_GUI);
        }catch (ValidationException e){
            gui.getMessageText().setText(e.getMessage());
        }
    }

    @Override
    public int getPlateauWidth() {
        return plateauWidth;
    }

    @Override
    public int getPlateauHeight() {
        return plateauHeight;
    }

    private void parseInitPosDir(){
        instructions = new ArrayList<>();
        String initRow = roverXText.getText()+" "+roverYText.getText()+" "+roverDirText.getText();
        try {
            instructions.add(new Instruction(initRow));
        }catch(ValidationException e){
            gui.getMessageText().setText(e.getMessage());
        }
    }

    @Override
    public List<Instruction> getInstructions() {
        return instructions;
    }
}
