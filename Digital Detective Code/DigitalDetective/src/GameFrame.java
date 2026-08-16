import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public GameFrame() {

        setTitle("Digital Detective");
        setSize(1000, 700);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        add(new GamePanel());

        setVisible(true);
    }
}