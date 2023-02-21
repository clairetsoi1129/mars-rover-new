package model;

import java.util.Arrays;

public enum Direction {
    N,E,S,W;

    public static boolean contains(String testedValue){
        return Arrays.stream(values())
                .map(Enum::name)
                .anyMatch(code -> code.equals(testedValue));
    }
}
