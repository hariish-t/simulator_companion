import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {

    private Emotion currentEmotion = Emotion.HAPPY;
    private final Timer timer;
    private long lastUpdate = System.currentTimeMillis();
    private final Random rand = new Random();
    private int floatOffset = 0;
    private boolean floatingUp = true;

    public Panel() {
        setPreferredSize(new Dimension(500, 300));
        setBackground(Color.BLACK);
        timer = new Timer(100, this); // refresh every 100ms
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGhost((Graphics2D) g);
    }

    private void drawGhost(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Floating effect
        if (floatingUp) {
            floatOffset--;
            if (floatOffset < -5) floatingUp = false;
        } else {
            floatOffset++;
            if (floatOffset > 5) floatingUp = true;
        }

        int ghostX = getWidth() / 2 - 60;
        int ghostY = getHeight() / 2 - 80 + floatOffset;

        // Draw ghost body
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(ghostX, ghostY, 120, 140, 60, 60);

        // Draw ghost face (eyes + mouth)
        g2d.setColor(Color.BLACK);

        // Eyes
        g2d.fillOval(ghostX + currentEmotion.eyeX1, ghostY + 40, 10, 10);
        g2d.fillOval(ghostX + currentEmotion.eyeX2, ghostY + 40, 10, 10);

        // Mouth
        if (currentEmotion.mouthType.equals("smile")) {
            g2d.drawArc(ghostX + 40, ghostY + 70, 40, 20, 0, -180);
        } else if (currentEmotion.mouthType.equals("flat")) {
            g2d.drawLine(ghostX + 40, ghostY + 80, ghostX + 80, ghostY + 80);
        } else if (currentEmotion.mouthType.equals("open")) {
            g2d.fillOval(ghostX + 55, ghostY + 75, 10, 10);
        }

        // Message
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 18));
        g2d.drawString(currentEmotion.message, 20, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // update mood every 5–10 sec
        if (System.currentTimeMillis() - lastUpdate > 5000 + rand.nextInt(5000)) {
            currentEmotion = Emotion.random();
            lastUpdate = System.currentTimeMillis();
        }
        repaint();
    }
}
