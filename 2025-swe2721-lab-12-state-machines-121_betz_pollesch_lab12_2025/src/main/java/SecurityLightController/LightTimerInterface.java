package SecurityLightController;

/**
 * This interface defines that which is used for a light timer.
 * 
 * @author schilling
 * 
 */
public interface LightTimerInterface {

	/**
	 * This method will start a periodic timer.  The timer will cause the given expiration event to fire every time the timer reaches the delay time at which point it will reset and begin timing again.
	 * 
	 * @param delay
	 *            This is the delay, in ms, for the given timer.
	 */
	public void startPeriodicTimer(int delay);

	/**
	 * This method will start a one shot timer.  The timer will cause the given expiration event to fire after delay ms.  It will then stop running.
	 * @param delay This is the delay in ms.
	 */
	public void startOneShotTimer(int delay);

	/**
	 * This method will reset the timer, causing it to time from the starting delay value again but not firing the evenbt.
	 */
	public void resetTimer();

	/**
	 * This method will stop the given timen, killing off the running thread if applicable.
	 */
	public void stopTimer();

	/**
	 * This method will set the event that is fired upon timer expiration.
	 * @param eventToFire This is the event to fire off when the timer expires.
	 */
	public void setExpirationEvent(LightControllerCommandInterface.CommandActionEnum eventToFire);
}
