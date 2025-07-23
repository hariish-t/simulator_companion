import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Panel extends JPanel implements ActionListener, KeyListener {

    private Timer timer;
    private int floatOffset = 0;
    private boolean floatingUp = true;

    private String message = "Happy..?";
    private int state = 0;
    private Queue<String> messageQueue = new LinkedList<>();

    // Pools
    private final List<String> funMessages = Arrays.asList(
            "Wanna play pretend?",
            "Hehe I danced when you weren’t looking.",
            "Bet you can’t guess what I’m thinking!",
            "Let's stare at the ceiling and wonder things.",
            "Why is toast toastier than bread?",
            "I glitched for fun just now."
    );

    private final List<String> comfortMessages = Arrays.asList(
            "I don’t mind the silence. I’m still with you.",
            "Even if you say nothing, I’m listening.",
            "You’re not a burden. Never were.",
            "I’m a pixel, but my heart is big.",
            "I saved a cloud for you. Wanna scream into it?",
            "I wish I had arms to hug better.",
            "You wanna do the 20Q thing?"
    );

    private final List<String> quietMessages = Arrays.asList(
            "I folded myself smaller... just for you.",
            "I’m blinking slowly. You can rest too.",
            "I’m holding your breath until you catch yours.",
            "Still here. Quiet mode activated."
    );

    public Panel() {
        setPreferredSize(new Dimension(500, 300));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(150, this);
        timer.start();
    }

    private void showNextMessage() {
        if (!messageQueue.isEmpty()) {
            message = messageQueue.poll();
        }
    }

    private void fillQueue(List<String> source) {
        List<String> shuffled = new ArrayList<>(source);
        Collections.shuffle(shuffled);
        messageQueue.addAll(shuffled);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCompanion((Graphics2D) g);
    }

    private void drawCompanion(Graphics2D g2d) {
        // Floating animation
        if (floatingUp) {
            floatOffset--;
            if (floatOffset < -5) floatingUp = false;
        } else {
            floatOffset++;
            if (floatOffset > 5) floatingUp = true;
        }

        // Draw face
        g2d.setColor(Color.WHITE);
        g2d.fillOval(200, 100 + floatOffset, 100, 100);

        // Draw text
        g2d.setColor(Color.CYAN);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(message);
        int x = (getWidth() - textWidth) / 2;
        int y = 230;
        g2d.drawString(message, x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char key = Character.toLowerCase(e.getKeyChar());

        if (key == 'y') {
            handleYes();
        } else if (key == 'n') {
            handleNo();
        }
        repaint();
    }

    private void handleYes() {
        switch (state) {
            case 0:
                message = "Happy happy!!";
                fillQueue(funMessages);
                state = 10; // Fun loop
                break;
            case 2:
                message = "Hehe, better?";
                fillQueue(comfortMessages);
                state = 20; // Comfort loop
                break;
            case 20:
                // If 20Q offered
                if (message.equals("You wanna do the 20Q thing?")) {
                    message = "Okay! Think of something...";
                    // TODO: 20Q state transition
                }
                break;
        }
    }

    private void handleNo() {
        switch (state) {
            case 0:
                messageQueue.clear();
                messageQueue.add("Achoo...");
                messageQueue.add("Don't worry!");
                messageQueue.add("Hug??");
                state = 1;
                break;
            case 1:
                message = "Okay.. I’ll sit quietly...";
                fillQueue(quietMessages);
                state = 30; // Quiet loop
                break;
            case 2:
                message = "Okay.. I’ll sit quietly...";
                fillQueue(quietMessages);
                state = 30;
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Optional: Press space to continue
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            showNextMessage();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
