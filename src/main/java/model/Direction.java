package model;

import java.util.Arrays;

public enum Direction {
    N,E,S,W;

    public static boolean contains(String testedValue){
        return Arrays.stream(values())
                .map(Enum::name)
                .anyMatch(code -> code.equalsIgnoreCase(testedValue));
    }

    private static final Direction[] directions = values();

    public Direction right() {
        int ordinal = this.ordinal();
        return directions[(ordinal + 1) % directions.length];
    }

    public Direction left() {
        int ordinal = this.ordinal();
        if (ordinal == 0) {
            ordinal = 4;
        }
        return directions[(ordinal - 1) % directions.length];
    }
}
