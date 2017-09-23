package rezblot.hourglass;

/**
 * Created by rezblot on 9/3/17.
 */

//https://stackoverflow.com/questions/8255738/is-there-a-stopwatch-in-java
// (Apparently not)

public class Stopwatch {

    private long startTime = 0;
    private long stopTime = 0;
    private boolean running = false;


    public boolean isRunning() {
        return this.running;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }


    public void stop() {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }

    public long getSeconds() {
        return getMilliseconds() / 1000;
    }

    public long getMilliseconds() {
        long milliseconds;

        long endTime = running
                ? System.currentTimeMillis()
                : stopTime;

        milliseconds = (endTime - startTime);

        return milliseconds;
    }
}
