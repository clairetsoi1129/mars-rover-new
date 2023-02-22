package model;

import java.awt.*;

public class Sample {
    private Point location;
    private boolean isCollected;

    public Sample(Point location) {
        this.location = location;
        this.isCollected = false;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }
}
