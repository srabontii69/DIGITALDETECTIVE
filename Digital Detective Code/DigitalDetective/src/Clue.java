import java.awt.Rectangle;

public class Clue {

    String name;

    int x;
    int y;

    int width = 35;
    int height = 35;

    int points;

    boolean found;

    public Clue(String name, int x, int y, int points) {

        this.name = name;
        this.x = x;
        this.y = y;
        this.points = points;

        found = false;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void collect() {
        found = true;
    }
}