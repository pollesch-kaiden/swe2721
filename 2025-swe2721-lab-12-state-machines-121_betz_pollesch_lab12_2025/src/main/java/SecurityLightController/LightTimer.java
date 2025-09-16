package SecurityLightController;

import SecurityLightController.LightControllerCommandInterface.CommandActionEnum;

/**
 * This class implements a light timer. It is used to control the timeouts
 * associated with a given light.
 *
 * @author schilling
 */
public class LightTimer implements LightTimerInterface {
    /**
     * This variable holds the delay in milliseconds until the timer expires.
     */
    private int myDelay;
    private int remainingTime;

    /**
     * This variable holds the callback which is invoked when the timer expires.
     */
    private final LightControllerCommandInterface callback;

    /**
     * This is the event that is to be fired when the timer expires.
     */
    private CommandActionEnum eventToFire;

    /**
     * This will indicate whether the timer is periodic (true) or a one shot timer.
     * If periodic, it will keep timing and keep cycling when the time expires.  If a one shot timer,
     * it will fire once and then stop.
     */
    private boolean periodic;

    /**
     * This variable holds the thread which is to tun for this timer.
     */
    private Thread t;

    private boolean keepThreadRunning;

    private final int SPEEDUP = 6;

    /**
     * @param callback This is the callback for when the timer expires.
     */
    public LightTimer(LightControllerCommandInterface callback) {
        super();
        this.callback = callback;
        t = null;
    }

     @Override
    public void startPeriodicTimer(int delay) {
         synchronized(this) {
             this.myDelay = delay;
             this.remainingTime = delay;
         }

        if ((t != null) && (t.isAlive())) {
            // DO nothing, as the thread is already running.
        } else {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized(this) {
                        keepThreadRunning = true;
                        periodic = true;
                    }
                    while (keepThreadRunning && periodic) {
                        synchronized(this) {
                            remainingTime = myDelay;
                        }
                        while (remainingTime > 0 && keepThreadRunning) {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                // If we are interrupted, it will not harm us
                                // other than the light will blink faster. So
                                // simply catch the error and ignore its
                                // presence.
                            }
                            synchronized(this) {
                                remainingTime -= 200*SPEEDUP;
                            }
                        }
                        if (keepThreadRunning) {
                            // Indicate that the timer has expired.
                            callback.signalAction(eventToFire);
                        }
                    }
                }
            });
            t.start();
        }
    }

    @Override
    public void startOneShotTimer(int delay) {
        synchronized(this) {
            this.myDelay = delay;
            this.remainingTime = delay;
        }

        if ((t != null) && (t.isAlive())) {
            // DO nothing, as the thread is already running.
        } else {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized(this) {
                        periodic = false;
                        keepThreadRunning = true;
                        remainingTime = myDelay;
                    }
                    while (remainingTime > 0 && keepThreadRunning) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            // If we are interrupted, it will not harm us
                            // other than the light will blink faster. So
                            // simply catch the error and ignore its
                            // presence.
                        }
                        synchronized(this) {
                            remainingTime -= 200*SPEEDUP;
                        }
                    }
                    if (keepThreadRunning) {
                        // Indicate that the timer has expired.
                        callback.signalAction(eventToFire);
                    }
                    synchronized(this) {
                        keepThreadRunning = false;
                    }
                }
            });
            t.start();
        }


    }

    @Override
    public void resetTimer() {
        synchronized(this) {
            this.remainingTime = this.myDelay;
        }
    }

    @Override
    public void stopTimer() {
        synchronized(this) {
            keepThreadRunning=false;
        }
    }

    @Override
    public void setExpirationEvent(CommandActionEnum eventToFire) {
        this.eventToFire = eventToFire;
    }
}
