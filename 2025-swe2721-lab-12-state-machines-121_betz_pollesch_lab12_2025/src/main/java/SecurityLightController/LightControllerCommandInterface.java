package SecurityLightController;

/**
 * This interface defines the light controller interface. Included are the
 * signals that can be sent into the controller (based on the detection of
 * changes in the system) as well as the methods which can be invoked.
 * 
 * @author schilling
 * 
 */
public interface LightControllerCommandInterface {
	/**
	 * This enumeration defines the events that the state machine is to respond to.
	 */
	public enum CommandActionEnum {

		/**
		 * This event is used to signal that initialization is occurring.  It's not an event that is normally used during operation.
		 */
		INIT,

		/**
		 * This signal indicates that the light sensor has been darkened,
		 * indicating diminished ambient light and the approach of nightfall.
		 */
		LIGHT_SENSOR_DARKENED,
		/**
		 * This signal indicates that the light sensor has been lit up,
		 * indicating the sun is up and daylight is here.
		 */
		LIGHT_SENSOR_LIGHTENED,

		/**
		 * This signal indicates that a manual override switch has been placed
		 * into the on position.
		 */
		MANUAL_SWITCH_ON,

		/**
		 * This signal indicates that a manual override switch has been placed
		 * into the off position.
		 */
		MANUAL_SWITCH_OFF,

		/**
		 * This signal indicates that motion has been detected in the area.
		 * Typically, this indicates that a motion sensor has gone off.
		 */
		MOTION_DETECTED,

		/**
		 * This signal indicates that an intrusion has been detected. Perhaps a
		 * window has been opened or some other detection of intrusion has
		 * occurred.
		 */
		SECURITY_ALARM_TRIPPED,

		/**
		 * This signal indicates that someone has cleared the active alarm. This
		 * is typically done by resetting the alarm.
		 */
		ALARM_CLEARED,

		/**
		 * This signal indicates that a timer has expired.
		 */
		LAMP_TIMER_EXPIRED,

		/**
		 * This signal indicated that the Motion Detection One Shot Timer has expired.
		 */
		MOTION_DETECTION_TIMER_EXPIRED
	};

	/**
	 * This method provides a mechanism for a signal to be received by the light
	 * controller. The signal will be one of the defined values provided
	 * previously.
	 * 
	 * @param signal
	 *            This is the signal that is being received. It can be one of
	 *            LIGHT_SENSOR_DARKENED, LIGHT_SENSOR_LIGHTENED,
	 *            MANUAL_SWITCH_ON, MANUAL_SWITCH_OFF, MOTION_DETECTED,
	 *            SECURITY_ALARM_TRIPPED, ALARM_CLEARED, or LAMP_TIMER_EXPIRED.
	 *            \
	 **/
	public void signalAction(CommandActionEnum signal);

	/**
	 * This method will allow an external observer to subscribe to state
	 * machine, receiving updates when states change.
	 * 
	 * @param obs
	 *            This is the observer interface that is to be subscribed.
	 */
	public void subscribe(LightControllerStateMachineObserverInterface obs);

	/**
	 * This method will allow an external observer to unsubscribe to state
	 * machine, stopping the reception of updates when states change.
	 * 
	 * @param obs
	 *            This is the observer interface that is to be unsubscribed.
	 */
	public void unsubscribe(LightControllerStateMachineObserverInterface obs);

}
