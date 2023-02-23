package controller;

import model.Instruction;

import java.util.List;

public interface InputController {
    void parseSize();
    int getPlateauWidth();
    int getPlateauHeight();
    List<Instruction> getInstructions();
    
}
