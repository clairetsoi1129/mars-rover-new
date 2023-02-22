package util;

import java.awt.*;

public interface RandomLocation {
    Point getGeneratedLocation(int i);

    void generateLocationAvoidConflict(int times);
}
