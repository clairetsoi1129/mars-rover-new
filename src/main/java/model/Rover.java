package model;

import exception.ValidationException;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class Rover extends MovingEntity{
    private final String ERR_HIT_OBSTACLE = "Watch out! You hit obstacle.";
    private List<Sample> basket;

    public Rover(int posX, int posY, Direction direction, Plateau plateau) throws ValidationException {
        super(posX, posY, direction, plateau);

        basket = new ArrayList<>();
    }


    public void go() throws ValidationException{
        for (int i = 0; i< movement.length(); i++){
            if (movement.charAt(i) == 'M'){
                stepsTaken+=movement.charAt(i);
                switch (direction) {
                    case N -> position.translate(0, 1);
                    case E -> position.translate(1, 0);
                    case S -> position.translate(0, -1);
                    case W -> position.translate(-1, 0);
                    default -> {
                    } //ignore
                }
                posX = position.x;
                posY = position.y;
                try {
                    validate();
                }catch (ValidationException e){
                    rollback();
                    throw e;
                }
                if (checkObstacle()) {
                    rollback();
                    System.out.println(MessageFormat.format("Final position: {0},{1},Step taken:{2}",
                            this.getPosition(), this.getDirection(), this.getStepsTaken()));
                    throw new ValidationException(ERR_HIT_OBSTACLE);
                }
                else {
                    collectSample();
                }
            }else if (movement.charAt(i) == 'L'){
                stepsTaken+=movement.charAt(i);
                direction = direction.left();
            }else if (movement.charAt(i) == 'R'){
                stepsTaken+=movement.charAt(i);
                direction = direction.right();
            } // ignore

        }
    }

    private void collectSample(){
        if (((Plateau)plateau).hasSample(position)){
            basket.add(((Plateau)plateau).collectSample(position));
        }
    }

    private boolean checkObstacle(){
        return ((Plateau)plateau).hasObstacle(position) ||
                ((Plateau)plateau).hasOtherRover(position, this);
    }

    public List<Sample> getBasket() {
        return basket;
    }
}
