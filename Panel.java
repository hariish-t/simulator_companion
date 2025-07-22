import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Panel extends JPanel implements ActionListener {

    private BufferedImage spriteSheet;
    private BufferedImage[] frames;
    private int frameIndex = 0;
    private Timer timer;
    private int floatOffset = 0;
    private boolean floatingUp = true;

    public Panel() {
        setPreferredSize(new Dimension(500, 300));
        setBackground(Color.BLACK);
        loadSpriteSheet();
        timer = new Timer(150, this); // ~6 FPS
        timer.start();
    }

    private void loadSpriteSheet() {
        try {
            spriteSheet = ImageIO.read(new File("gimg.png"));
            frames = new BufferedImage[10]; // 10 frames
            for (int i = 0; i < 10; i++) {
                frames[i] = spriteSheet.getSubimage(i * 64, 0, 64, 64);
            }
        } catch (IOException e) {
            System.err.println("Error loading sprite sheet: " + e.getMessage());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAnimatedCompanion((Graphics2D) g);
    }

    private void drawAnimatedCompanion(Graphics2D g2d) {
        // Floating animation
        if (floatingUp) {
            floatOffset--;
            if (floatOffset < -5) floatingUp = false;
        } else {
            floatOffset++;
            if (floatOffset > 5) floatingUp = true;
        }

       int scale = 3;
int spriteWidth = frames[frameIndex].getWidth() * scale;
int spriteHeight = frames[frameIndex].getHeight() * scale;

int x = (getWidth() - spriteWidth) / 2;
int y = (getHeight() - spriteHeight) / 2 + floatOffset;

g2d.drawImage(frames[frameIndex], x, y, spriteWidth, spriteHeight, null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Animate sprite
        frameIndex = (frameIndex + 1) % frames.length;
        repaint();
    }
}
