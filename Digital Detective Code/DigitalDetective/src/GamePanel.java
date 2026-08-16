import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    GameState gameState = GameState.MENU;

    Player player;
    Case currentCase;

    int score = 0;
    ArrayList<String> evidence = new ArrayList<>();

    boolean up;
    boolean down;
    boolean left;
    boolean right;

    boolean interactionPressed = false;

    Timer timer;

    public GamePanel() {

        setPreferredSize(new Dimension(1000, 700));
        setFocusable(true);

        player = new Player(100, 300);

        currentCase = new Case("The Missing Diamond");

        setupKeys();

        timer = new Timer(16, e -> {
            updateGame();
            repaint();
        });

        timer.start();

        requestFocusInWindow();
    }

    private void setupKeys() {

        InputMap inputMap =
                getInputMap(WHEN_IN_FOCUSED_WINDOW);

        ActionMap actionMap =
                getActionMap();

        // Movement
        inputMap.put(
                KeyStroke.getKeyStroke("W"),
                "upPressed"
        );

        inputMap.put(
                KeyStroke.getKeyStroke("released W"),
                "upReleased"
        );

        inputMap.put(
                KeyStroke.getKeyStroke("S"),
                "downPressed"
        );

        inputMap.put(
                KeyStroke.getKeyStroke("released S"),
                "downReleased"
        );

        inputMap.put(
                KeyStroke.getKeyStroke("A"),
                "leftPressed"
        );

        inputMap.put(
                KeyStroke.getKeyStroke("released A"),
                "leftReleased"
        );

        inputMap.put(
                KeyStroke.getKeyStroke("D"),
                "rightPressed"
        );

        inputMap.put(
                KeyStroke.getKeyStroke("released D"),
                "rightReleased"
        );

        // Investigate
        inputMap.put(
                KeyStroke.getKeyStroke("E"),
                "interact"
        );

        // ENTER = Start / Restart
        inputMap.put(
                KeyStroke.getKeyStroke("ENTER"),
                "startGame"
        );

        // W
        actionMap.put(
                "upPressed",
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        up = true;
                    }
                }
        );

        actionMap.put(
                "upReleased",
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        up = false;
                    }
                }
        );

        // S
        actionMap.put(
                "downPressed",
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        down = true;
                    }
                }
        );

        actionMap.put(
                "downReleased",
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        down = false;
                    }
                }
        );

        // A
        actionMap.put(
                "leftPressed",
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        left = true;
                    }
                }
        );

        actionMap.put(
                "leftReleased",
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        left = false;
                    }
                }
        );

        // D
        actionMap.put(
                "rightPressed",
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        right = true;
                    }
                }
        );

        actionMap.put(
                "rightReleased",
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        right = false;
                    }
                }
        );

        // E = investigate
        actionMap.put(
                "interact",
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        interactionPressed = true;
                    }
                }
        );

        // ENTER = start/restart
        actionMap.put(
                "startGame",
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {

                        if (gameState == GameState.MENU) {

                            gameState = GameState.PLAYING;

                            player.reset();

                            score = 0;

                            currentCase.reset();
                        }

                        else if (gameState == GameState.CASE_COMPLETE) {

                            gameState = GameState.PLAYING;

                            player.reset();

                            score = 0;

                            currentCase.reset();
                        }
                    }
                }
        );
    }

    private void updateGame() {

        if (gameState != GameState.PLAYING) {
            return;
        }

        int dx = 0;
        int dy = 0;

        if (up) {
            dy--;
        }

        if (down) {
            dy++;
        }

        if (left) {
            dx--;
        }

        if (right) {
            dx++;
        }

        player.move(dx, dy);

        if (interactionPressed) {

            checkClueInteraction();

            interactionPressed = false;
        }

        if (currentCase.isComplete()) {

            gameState = GameState.CASE_COMPLETE;
        }
    }

    private void checkClueInteraction() {

        Rectangle playerArea =
                player.getBounds();

        for (Clue clue : currentCase.clues) {

            if (!clue.found &&
                    playerArea.intersects(clue.getBounds())) {

                clue.collect();
                evidence.add("Clue");

                score += clue.points;

                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (gameState == GameState.MENU) {

            drawMenu(g);

        } else if (gameState == GameState.PLAYING) {

            drawGame(g);

        } else if (gameState == GameState.CASE_COMPLETE) {

            drawCaseComplete(g);
        }
    }

    private void drawMenu(Graphics g) {

        g.setColor(new Color(25, 25, 35));

        g.fillRect(
                0,
                0,
                getWidth(),
                getHeight()
        );

        g.setColor(Color.WHITE);

        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        50
                )
        );

        g.drawString(
                "DIGITAL DETECTIVE",
                250,
                180
        );

        g.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        25
                )
        );

        g.drawString(
                "CASE #01: THE MISSING DIAMOND",
                300,
                250
        );

        g.drawString(
                "Press ENTER to Start",
                360,
                350
        );

        g.drawString(
                "W A S D = Move",
                390,
                410
        );

        g.drawString(
                "E = Investigate",
                390,
                450
        );
    }

    private void drawGame(Graphics g) {

        // Room background
        g.setColor(
                new Color(220, 210, 190)
        );

        g.fillRect(
                0,
                0,
                getWidth(),
                getHeight()
        );

        // Top bar
        g.setColor(
                new Color(40, 40, 45)
        );

        g.fillRect(
                0,
                0,
                1000,
                80
        );

        g.setColor(Color.WHITE);

        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        g.drawString(
                "CASE #01: THE MISSING DIAMOND",
                30,
                35
        );

        g.drawString(
                "SCORE: " + score,
                780,
                35
        );

        g.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        18
                )
        );
        

        g.drawString(
                "Clues: "
                        + currentCase.getFoundClues()
                        + "/"
                        + currentCase.getTotalClues(),
                780,
                62
        );
        g.drawString(
        "Evidence: " + evidence.size(),
        780,
        80
);

        // Investigation room
        g.setColor(
                new Color(245, 240, 225)
        );

        g.fillRect(
                40,
                100,
                900,
                500
        );

        g.setColor(Color.DARK_GRAY);

        g.drawRect(
                40,
                100,
                900,
                500
        );

        // Furniture
        drawFurniture(g);

        // Clues
        for (Clue clue : currentCase.clues) {

            if (!clue.found) {

                g.setColor(Color.YELLOW);

                g.fillRect(
                        clue.x,
                        clue.y,
                        clue.width,
                        clue.height
                );

                g.setColor(Color.BLACK);

                g.drawRect(
                        clue.x,
                        clue.y,
                        clue.width,
                        clue.height
                );

                g.setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                16
                        )
                );

                g.drawString(
                        "?",
                        clue.x + 12,
                        clue.y + 24
                );
            }
        }

        // Player
        g.setColor(
                new Color(60, 100, 200)
        );

        g.fillRect(
                player.x,
                player.y,
                player.width,
                player.height
        );

        g.setColor(Color.WHITE);

        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        g.drawString(
                "D",
                player.x + 13,
                player.y + 30
        );

        // Instructions
        g.setColor(Color.BLACK);

        g.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        18
                )
        );

        g.drawString(
                "WASD = Move     E = Investigate clues",
                300,
                650
        );
    }

    private void drawFurniture(Graphics g) {

        // Desk
        g.setColor(
                new Color(120, 80, 50)
        );

        g.fillRect(
                100,
                150,
                120,
                60
        );

        g.setColor(Color.BLACK);

        g.drawString(
                "DESK",
                135,
                185
        );

        // Cabinet
        g.setColor(
                new Color(100, 70, 50)
        );

        g.fillRect(
                650,
                350,
                100,
                100
        );

        g.setColor(Color.WHITE);

        g.drawString(
                "CABINET",
                665,
                405
        );

        // Painting
        g.setColor(Color.BLACK);

        g.drawRect(
                400,
                130,
                120,
                100
        );

        g.drawString(
                "PAINTING",
                425,
                185
        );

        // Exit
        g.setColor(
                new Color(100, 60, 40)
        );

        g.fillRect(
                850,
                500,
                60,
                100
        );

        g.setColor(Color.WHITE);

        g.drawString(
                "EXIT",
                865,
                550
        );
    }

    private void drawCaseComplete(Graphics g) {

        g.setColor(
                new Color(20, 25, 30)
        );

        g.fillRect(
                0,
                0,
                getWidth(),
                getHeight()
        );

        g.setColor(Color.WHITE);

        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        50
                )
        );

        g.drawString(
                "CASE SOLVED!",
                330,
                200
        );

        g.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        30
                )
        );

        g.drawString(
                "Final Score: " + score,
                360,
                280
        );

        g.drawString(
                "Rank: " + getRank(),
                400,
                330
        );

        g.drawString(
                "Press ENTER to play again",
                330,
                430
        );
    }

    private String getRank() {

        if (score >= 125) {
            return "MASTER DETECTIVE";
        }

        if (score >= 100) {
            return "DETECTIVE";
        }

        if (score >= 50) {
            return "INVESTIGATOR";
        }

        return "BEGINNER";
    }
}
