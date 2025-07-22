import java.util.Random;

public class State {
    public String mood = "neutral";
    public String message = "Just floating~";

    private long lastUpdate = System.currentTimeMillis();
    private final Random rand = new Random();

    public boolean shouldUpdate() {
        return System.currentTimeMillis() - lastUpdate > 10000 + rand.nextInt(10000);
    }

    public void updateState() {
        int state = rand.nextInt(4);
        switch (state) {
            case 0:
                mood = "neutral";
                message = "Just floating~";
                break;
            case 1:
                mood = "blink";
                message = "Did you blink too?";
                break;
            case 2:
                mood = "goofy";
                message = "I'm dizzy!";
                break;
            case 3:
                mood = "lonely";
                message = "Where'd you go?";
                break;
        }
        lastUpdate = System.currentTimeMillis();
    }
}
