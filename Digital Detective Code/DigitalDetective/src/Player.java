import java.awt.Rectangle;

public class Player {

    int x;
    int y;

    int width = 40;
    int height = 50;

    int speed = 5;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int dx, int dy) {

        x += dx * speed;
        y += dy * speed;

        if (x < 40) {
            x = 40;
        }

        if (y < 100) {
            y = 100;
        }

        if (x > 920) {
            x = 920;
        }

        if (y > 580) {
            y = 580;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void reset() {
        x = 100;
        y = 300;
    }
}