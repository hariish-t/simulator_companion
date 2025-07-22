import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Panel extends JPanel implements ActionListener {
    private final int WIDTH = 512;   // Simulates OLED 128x64 scaling
    private final int HEIGHT = 256;
    private final Timer timer;
    private State ghostState;

    public Panel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        ghostState = new State();
        timer = new Timer(1000, this); // update every second
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGhost((Graphics2D) g);
    }

    private void drawGhost(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 20));
        g2d.drawString(ghostState.message, 20, 30);

        int cx = WIDTH / 2 - 40;
        int cy = 100;

        g2d.drawOval(cx, cy, 80, 80); // head
        switch (ghostState.mood) {
            case "neutral":
                g2d.fillOval(cx + 20, cy + 25, 10, 10);
                g2d.fillOval(cx + 50, cy + 25, 10, 10);
                break;
            case "blink":
                g2d.drawLine(cx + 20, cy + 30, cx + 30, cy + 30);
                g2d.drawLine(cx + 50, cy + 30, cx + 60, cy + 30);
                break;
            case "goofy":
                g2d.fillOval(cx + 20, cy + 25, 10, 10);
                g2d.drawOval(cx + 50, cy + 25, 10, 10);
                break;
            case "lonely":
                g2d.fillOval(cx + 25, cy + 28, 5, 5);
                g2d.fillOval(cx + 55, cy + 28, 5, 5);
                break;
        }
        g2d.drawArc(cx, cy + 40, 80, 40, 0, -180); // mouth
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ghostState.shouldUpdate()) {
            ghostState.updateState();
            repaint();
        }
    }
}
