package view;

import controller.GraphicInputController;
import exception.ValidationException;
import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class GraphicView {
    public static final String TITLE = "Welcome to Melody Mars Rover!!";
    public static final String WELCOME_MSG = "Welcome to Melody Mars Mission, you can have a wonderful experience " +
            "to move rovers around the surface of Mars!";
    public static final String MSG_SETUP_DONE = "Setup complete. You can control the rover now.";

    private JFrame window;
    private JTextArea messageText;

    private JPanel[] bgPanel = new JPanel[2];
    private JLabel[] bgLabel = new JLabel[2];

    private final int WIN_WIDTH = 800;
    private final int WIN_HEIGHT = 700;
    private final int TEXT_AREA_WIDTH = WIN_WIDTH;
    private final int TEXT_AREA_HEIGHT = 150;
    private final int TEXT_AREA_X = 0;
    private final int TEXT_AREA_Y = WIN_HEIGHT - TEXT_AREA_HEIGHT;

    private final int BG_PANEL_WIDTH = WIN_WIDTH;
    private final int BG_PANEL_HEIGHT = WIN_HEIGHT - TEXT_AREA_HEIGHT;
    private final int BG_PANEL_X = 0;
    private final int BG_PANEL_Y = 0;
    private final int CELL_WIDTH = 50;
    private final int CELL_HEIGHT = 50;

    private final String BG_IMG = "img/plateau800x600.png";

    private GraphicInputController controller;

    private RoverView roverUI;
    private PlateauView plateauUI;

    private Game game;

    public GraphicView() {
        initMainWindow();
        initMainBoard();

        window.setName("Game");
        window.setVisible(true);
    }

    private void initMainWindow() {
        window = new JFrame(TITLE);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(WIN_WIDTH, WIN_HEIGHT);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(null);

        messageText = new JTextArea(WELCOME_MSG);
        messageText.setName("messageText");
        messageText.setBounds(TEXT_AREA_X, TEXT_AREA_Y, TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
        messageText.setBackground(Color.BLACK);
        messageText.setForeground(Color.WHITE);
        messageText.setEditable(false);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setFont(new Font("Book Antiqua", Font.PLAIN, 18));
        window.add(messageText);
    }

    private void createBackground(int bgNum, String bgImg) {
        bgPanel[bgNum] = new JPanel();
        bgPanel[bgNum].setBounds(BG_PANEL_X, BG_PANEL_Y, BG_PANEL_WIDTH, BG_PANEL_HEIGHT);
        bgPanel[bgNum].setBackground(Color.BLUE);
        bgPanel[bgNum].setLayout(null);
        window.add(bgPanel[bgNum]);

        bgLabel[bgNum] = new JLabel();
        bgLabel[bgNum].setBounds(BG_PANEL_X, BG_PANEL_Y, BG_PANEL_WIDTH, BG_PANEL_HEIGHT);

        ImageIcon bgIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(bgImg)));
        bgLabel[bgNum].setIcon(bgIcon);
    }

    public JLabel createObject(int bgNum, int objX, int objY, String objImg) {
        JLabel objectLabel = new JLabel();
        objectLabel.setBounds(objX, objY, CELL_WIDTH, CELL_HEIGHT);
        ImageIcon objectIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(objImg)));
        objectLabel.setIcon(objectIcon);

        bgPanel[bgNum].add(objectLabel);
        return objectLabel;
    }

    private void initMainBoard() {
        createBackground(0, BG_IMG);
        controller = new GraphicInputController(this);
        bgPanel[0].add(bgLabel[0]);
    }

    public void initGameBoardComponents() {
        createBackground(1, BG_IMG);
        plateauUI = new PlateauView(game.getPlateau(), this);
        roverUI = new RoverView(game.getRovers().get(0), this);
        bgPanel[1].add(bgLabel[1]);

        bgPanel[1].addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                updateRover(KeyEvent.getKeyText(e.getKeyCode()).toCharArray());
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        bgPanel[1].setFocusable(true);
        getMessageText().setText(MSG_SETUP_DONE);
    }

    public Point getGridPos(int x, int y) {
        Dimension dim = new Dimension(controller.getPlateauWidth(),controller.getPlateauHeight());
        return new Point((WIN_WIDTH / 2 - (dim.width) * CELL_WIDTH / 2 + x * CELL_WIDTH),
                (WIN_HEIGHT - TEXT_AREA_HEIGHT) / 2 + (dim.height) * CELL_HEIGHT / 2 - (dim.height - y) * CELL_HEIGHT);
    }

    private void updateRover(char[] command) {
        if (command.length == 0)
            return;

        game.getRovers().get(0).setMovement(new String(command));

        try {
            game.start();
            roverUI.updateUI(game.isGameEnd());
            plateauUI.updateSampleUI();

        } catch (ValidationException e) {
            getMessageText().setText(e.getMessage());
        }
    }

    public Point translateObjectPos(int x, int y) {
        Point pos = getGridPos(x, controller.getPlateauHeight() - y - 1);
        return new Point(pos.x - CELL_WIDTH / 2, pos.y + CELL_HEIGHT / 2);
    }

    public Point translateObjectPos(Point point) {
        return translateObjectPos(point.x, point.y);
    }


    public void showPlayScreen(){
        if (this.getBgPanel()[1] != null) {
            this.getBgPanel()[1].setVisible(true);
        }
        if (this.getBgPanel()[0] != null) {
            this.getBgPanel()[0].setVisible(false);
        }
    }

    public void showInputScreen(){
        if (this.getBgPanel()[0] != null) {
            this.getBgPanel()[0].setVisible(true);
        }
        if (this.getBgPanel()[1] != null) {
            this.getBgPanel()[1].setVisible(false);
        }
    }

    public JTextArea getMessageText() {
        return messageText;
    }


    public JPanel[] getBgPanel() {
        return bgPanel;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
