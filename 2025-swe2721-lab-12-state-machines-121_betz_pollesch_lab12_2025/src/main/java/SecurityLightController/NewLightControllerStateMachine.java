package SecurityLightController;

import java.util.ArrayList;
import java.util.List;

import SecurityLightController.LightControllerStateMachineObserverInterface.LightState;

import static SecurityLightController.LightControllerCommandInterface.CommandActionEnum.INIT;
import static SecurityLightController.LightControllerCommandInterface.CommandActionEnum.LIGHT_SENSOR_DARKENED;

/**
 * This class implements a state machine that can be used to control a security
 * light.
 *
 * @author schilling
 */
public class NewLightControllerStateMachine implements LightControllerCommandInterface {
    /**
     * This variable holds a list of observers. Whenever a state changes, the
     * observers are updated with the state change.
     */
    private final List<LightControllerStateMachineObserverInterface> observers = new ArrayList<>();

    /**
     * This variable holds the current state for the state machine.  Note the scope of the variable has been set to allow easier access to this variable for testing purposes without needing to use reflection.
     */
    private LightState currentState;

    /**
     * This variable holds the substate for the intrusion detected state.
     * It will have a value of 1 if the lamp is on and 0 if it is off.
     * Note that this is also available for access without the usage of reflection.
     */
    private LightControllerStateMachineObserverInterface.LightStateIntrusionDetectedStates intrusionDetectionSubStateVariable;

    /**
     * This variable holds a reference to the light which is to be controlled by this state
     * machine.
     */
    private LightDeviceInterface light;

    /**
     * This variable is a reference to the timer that is to be used to control the blinking of lights.  This timer is periodic in nature and will continue to fire every x ms until shutdown.for timed
     * activities.
     */
    private LightTimerInterface blinkTimer;

    /**
     * This variable is a reference to the timer that is to be used for one shot activities.  This timer will fire once after x ms have gone by.
     */
    private LightTimerInterface motionDetectedTimer;

    /**
     * This is the default constructor, which will instantiate a new instance of
     * this class.
     */
    public NewLightControllerStateMachine() {
        currentState = LightState.LAMP_OFF_DAYLIGHT;
        intrusionDetectionSubStateVariable = LightControllerStateMachineObserverInterface.LightStateIntrusionDetectedStates.LAMP_OFF;
    }

    /**
     * This method will set the given light that is controlled by this state machine.
     *
     * @param light This is the instance of the light that is to be directly
     *              controlled.
     */
    public void setLight(LightDeviceInterface light) {

    }

    /**
     * This method will set an instance of the timer that is to be used with
     * this class.
     *
     * @param blinkTimer This is the timer instance.
     */
    public void setBlinkTimer(LightTimerInterface blinkTimer) {

    }

    /**
     * This method will set the instance of the timer that is to be used for motion detection.
     *
     * @param motionDetectedTimer This si the timer instance.
     */
    public void setMotionDetectedTimer(LightTimerInterface motionDetectedTimer) {

    }


 

    /*
     * (non-Javadoc)
     *
     * @seeSecurityLightController.LightControllerCommandInterface#subscribe(
     * SecurityLightController.LightControllerStateMachineObserverInterface)
     */
    public void subscribe(LightControllerStateMachineObserverInterface obs) {

    }

    /*
     * (non-Javadoc)
     *
     * @seeSecurityLightController.LightControllerCommandInterface#unsubscribe(
     * SecurityLightController.LightControllerStateMachineObserverInterface)
     */
    public void unsubscribe(LightControllerStateMachineObserverInterface obs) {
	}

   @Override
    /*
     * (non-Javadoc)
     *
     * @see
     * SecurityLightController.LightControllerCommandInterface#signalAction(int)
     */
    public void signalAction(CommandActionEnum signal) {

    }
}
