package SecurityLightController;

/**
 * This interface defines an interface which a class must implement if it
 * desires to observe the lamp state.
 * 
 * @author schilling
 * 
 */
public interface LightControllerStateMachineObserverInterface {
	/**
	 * This enumeration will define the behavior of the overarching state machine.
	 */
	public enum LightState {INVALID,
		LAMP_OFF_DAYLIGHT, LAMP_ON_FULL_BRIGHTNESS, LAMP_OFF_NIGHTIME, LAMP_ON_NIGHTIME_BRIGHTNESS, MOTION_DETECTED, INTRUSION_DETECTED
	};

	/**
	 * This enumeration will define the behavior of the Intrusion Detected Substate machine.
	 */
	public enum LightStateIntrusionDetectedStates {
		LAMP_ON, LAMP_OFF
	};
	/**
	 * This method will update the state of the light, passing in one of the the
	 * parameters representing the current state.
	 * 
	 * @param newState
	 *            The new state, one of LAMP_OFF_DAYLIGHT ,
	 *            LAMP_ON_FULL_BRIGHTNESS, LAMP_OFF_NIGHTIME,
	 *            LAMP_ON_NIGHTIME_BRIGHTNESS, MOTION_DETECTED,
	 *            INTRUSION_DETECTED = 32;
	 */
	public void updateLightState(LightState newState);

}
