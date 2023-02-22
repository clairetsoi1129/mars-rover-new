package util;

import java.awt.*;
import java.util.List;

public interface RandomLocation {
    List<Point> generateLocationAvoidConflict(int times);
}
