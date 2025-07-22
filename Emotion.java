import java.util.Random;

public class Emotion {
    public final String name;
    public final String message;
    public final String mouthType; // "smile", "flat", "open"
    public final int eyeX1;
    public final int eyeX2;

    public Emotion(String name, String message, String mouthType, int eyeX1, int eyeX2) {
        this.name = name;
        this.message = message;
        this.mouthType = mouthType;
        this.eyeX1 = eyeX1;
        this.eyeX2 = eyeX2;
    }

    private static final Emotion[] EMOTIONS = new Emotion[]{
        new Emotion("HAPPY", "I'm so happy to see you!", "smile", 35, 75),
        new Emotion("LONELY", "Where have you been?", "flat", 40, 70),
        new Emotion("GOOFY", "Heehee, I'm a silly ghost!", "open", 30, 80),
        new Emotion("SAD", "It's quiet in here...", "flat", 45, 65),
        new Emotion("CURIOUS", "What's that behind you?", "open", 35, 75),
        new Emotion("SLEEPY", "Zzz... still here tho", "flat", 38, 72),
        new Emotion("BLINK", "Just blinked.", "flat", 35, 75)
    };

    public static Emotion random() {
        Random r = new Random();
        return EMOTIONS[r.nextInt(EMOTIONS.length)];
    }
}
