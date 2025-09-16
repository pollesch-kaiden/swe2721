package SecurityLightController;

/**
 * This interface defines those things which must be present for a light. This
 * includes the methods invoked from the state machine to control the light.
 * 
 * @author schilling
 *
 */
public interface LightDeviceInterface {
	/**
	 * This method will turn the given light off.
	 */
	public void turnLightOff();

	/**
	 * This method will turn the light on to full brightness.
	 */
	public void turnLightOnFullBrightness();

	/**
	 * This method will turn the light on to full night time brightness.
	 */
	public void turnLightOnNightimeBrightness();
}
