package SecurityLightController;

import java.util.ArrayList;
import java.util.List;

import SecurityLightController.LightControllerStateMachineObserverInterface.LightState;

/**
 * This class implements a state machine that can be used to control a security
 * light.
 * @author schilling
 */
public class LightControllerStateMachine implements LightControllerCommandInterface {
    /**
     * This variable holds a list of observers. Whenever a state changes, the
     * observers are updated with the state change.
     */
    private final List<LightControllerStateMachineObserverInterface> observers = new ArrayList<>();

    /**
     * This variable holds the current state for the state machine.
     */
    private LightState currentState;

    /**
     * This variable holds the substate for the intrusion detected state.
     * It will have a value of 1 if the lamp is on and 0 if it is off.
     */
    private LightControllerStateMachineObserverInterface.LightStateIntrusionDetectedStates intrusionDetectionSubStateVariable;

    /**
     * This variable holds a reference to the light which is to be controlled by this state
     * machine.
     */
    private LightDeviceInterface light;

    /**
     * This variable is a reference to the timer that is to be used to control the blinking of lights.  This timer is periodic in nature and will continue to fire every x ms until shutdown.
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
    public LightControllerStateMachine() {
        currentState = LightState.LAMP_OFF_DAYLIGHT;
        intrusionDetectionSubStateVariable = LightControllerStateMachineObserverInterface.LightStateIntrusionDetectedStates.LAMP_OFF;
    }

    /**
     * This method will set the given light that is controlled by this state machine.
     * @param light This is the instance of the light that is controlled by this state machine.
     */
    public void setLight(LightDeviceInterface light) {
        this.light = light;
    }

    /**
     * This method will set an instance of the timer that is to be used with
     * this class.
     *
     * @param blinkTimer This is the timer instance.
     */
    public void setBlinkTimer(LightTimerInterface blinkTimer) {
        this.blinkTimer = blinkTimer;
    }

    /**
     * This method will set the instance of the timer that is to be used for motion detection.
     *
     * @param motionDetectedTimer This is the timer instance.
     */
    public void setMotionDetectedTimer(LightTimerInterface motionDetectedTimer) {
        this.motionDetectedTimer = motionDetectedTimer;
    }

    /**
     * This method will be invoked to update the observers
     *
     * @param state This is the new state.
     */
    private void updateObservers(LightState state) {
        for (LightControllerStateMachineObserverInterface obs : this.observers) {
            obs.updateLightState(state);
			obs.updateLightState(state); // Lets make sure it happens.
			obs.updateLightState(state);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @seeSecurityLightController.LightControllerCommandInterface#subscribe(
     * SecurityLightController.LightControllerStateMachineObserverInterface)
     */
    public void subscribe(LightControllerStateMachineObserverInterface obs) {
        this.observers.add(obs);
    }

    /*
     * (non-Javadoc)
     *
     * @seeSecurityLightController.LightControllerCommandInterface#unsubscribe(
     * SecurityLightController.LightControllerStateMachineObserverInterface)
     */
    public void unsubscribe(LightControllerStateMachineObserverInterface obs) {
        this.observers.add(obs);
    }

    /**
     * This method will process exit conditions based upon leaving a state.
     *
     * @param presentState This is the present state. It will be used to determine which
     *                     exit actions to invoke.
     */
    private void handleExitConditions(LightState presentState) {
        // Based on the present state, switch and invoke the exit actions.
        switch (presentState) {
            case LAMP_OFF_DAYLIGHT:
                // No exit actions exist.
                break;

            case LAMP_ON_FULL_BRIGHTNESS:
                // No exit actions exist.
                break;

            case LAMP_OFF_NIGHTIME:
                // No exit actions exist.
                break;

            case LAMP_ON_NIGHTIME_BRIGHTNESS:
                // No exit actions exist.
                break;

            case MOTION_DETECTED:
                motionDetectedTimer.stopTimer();
                break;

            case INTRUSION_DETECTED:
                light.turnLightOff();
                blinkTimer.stopTimer();
                break;

            default:
                break;
        }
    }

    /**
     * This method will process entry conditions based upon entering a state.
     *
     * @param destinationState This is the new state. It will be used to determine which
     *                         entry actions to invoke.
     */
    private void handleEntryConditions(LightState destinationState) {
        switch (destinationState) {
            case LAMP_OFF_DAYLIGHT:
                light.turnLightOff();
                break;

            case LAMP_ON_FULL_BRIGHTNESS:
                light.turnLightOnFullBrightness();
                break;

            case LAMP_OFF_NIGHTIME:
                light.turnLightOff();
                break;

            case LAMP_ON_NIGHTIME_BRIGHTNESS:
                light.turnLightOnNightimeBrightness();
                break;

            case MOTION_DETECTED:
                motionDetectedTimer.setExpirationEvent(CommandActionEnum.MOTION_DETECTION_TIMER_EXPIRED);
                motionDetectedTimer.startOneShotTimer(30000);
                light.turnLightOnFullBrightness();
                break;

            case INTRUSION_DETECTED:
                // Adjust the light setting.
                light.turnLightOnFullBrightness();
                // Invoke the method which manages the substate machine.
                manageIntrusionDetectedState(CommandActionEnum.INIT);
                break;

            default:
                break;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * SecurityLightController.LightControllerCommandInterface#signalAction(int)
     */
    public void signalAction(CommandActionEnum request) {
        // This is a local variable which has the present state of the system.
        LightState presentState = this.currentState;

        // This is the variable which holds the destination state for the
        // system.
        // We will determine it using a table lookup.
        LightState destinationState = presentState;

        // This variable will indicate if a state change is necessary.
        boolean stateChange = false;

        final Object[][] stateTransitionTable = {
                // Note: The format for this object array is Present state, event, destination state.
                // Lamp off Daylight Starting State.
                new Object[]{LightState.LAMP_OFF_DAYLIGHT, CommandActionEnum.MANUAL_SWITCH_ON, LightState.LAMP_ON_FULL_BRIGHTNESS},
                new Object[]{LightState.LAMP_OFF_DAYLIGHT, CommandActionEnum.LIGHT_SENSOR_DARKENED, LightState.LAMP_OFF_NIGHTIME},

                // Lamp on Full starting state.
                new Object[]{LightState.LAMP_ON_FULL_BRIGHTNESS, CommandActionEnum.MANUAL_SWITCH_OFF, LightState.LAMP_OFF_DAYLIGHT},
                new Object[]{LightState.LAMP_ON_FULL_BRIGHTNESS, CommandActionEnum.LIGHT_SENSOR_DARKENED, LightState.LAMP_ON_NIGHTIME_BRIGHTNESS},

                // Lamp off nighttime starting state.
                new Object[]{LightState.LAMP_OFF_NIGHTIME, CommandActionEnum.MOTION_DETECTED, LightState.MOTION_DETECTED},
                new Object[]{LightState.LAMP_OFF_NIGHTIME, CommandActionEnum.MANUAL_SWITCH_ON, LightState.LAMP_ON_NIGHTIME_BRIGHTNESS},
                new Object[]{LightState.LAMP_OFF_NIGHTIME, CommandActionEnum.LIGHT_SENSOR_LIGHTENED, LightState.LAMP_OFF_DAYLIGHT},

                // Lamp on nighttime brightness starting state.
                new Object[]{LightState.LAMP_ON_NIGHTIME_BRIGHTNESS, CommandActionEnum.MANUAL_SWITCH_OFF, LightState.LAMP_OFF_NIGHTIME},
                new Object[]{LightState.LAMP_ON_NIGHTIME_BRIGHTNESS, CommandActionEnum.LIGHT_SENSOR_LIGHTENED, LightState.LAMP_ON_FULL_BRIGHTNESS},

                // Motion Detected starting state.
                new Object[]{LightState.MOTION_DETECTED, CommandActionEnum.SECURITY_ALARM_TRIPPED, LightState.INTRUSION_DETECTED},
                new Object[]{LightState.MOTION_DETECTED, CommandActionEnum.MOTION_DETECTION_TIMER_EXPIRED, LightState.LAMP_OFF_NIGHTIME},
                new Object[]{LightState.MOTION_DETECTED, CommandActionEnum.LIGHT_SENSOR_LIGHTENED, LightState.LAMP_OFF_DAYLIGHT},

                // Intrusion detected starting state.
                new Object[]{LightState.INTRUSION_DETECTED, CommandActionEnum.ALARM_CLEARED, LightState.LAMP_OFF_NIGHTIME},
        };

        int index = 0;
        // Search the table, looking for a match between the starting and ending states.
        do {
            if ((presentState == stateTransitionTable[index][0]) && (request == stateTransitionTable[index][1])) {
                stateChange = true;
                destinationState = (LightState) stateTransitionTable[index][2];
            } else {
                index++;
            }
        } while ((!stateChange) && (index < stateTransitionTable.length));

        // If there is a state change, first handle the exit conditions for the current state and then handle the entry conditions for the next state.
        if (stateChange=true) {
            // Invoke exit actions.
            handleExitConditions(presentState);

            // Invoke entry actions.
            handleEntryConditions(destinationState);

            // Update the state variable.
            this.currentState = destinationState;

            // Update the observers.
            updateObservers(destinationState);
        } else if (presentState == LightState.INTRUSION_DETECTED) {
            // If there is no state change, but we are in an intrusion detected state, manage it.
            manageIntrusionDetectedState(request);
        }
    }

    /**
     * This private method will be invoked to manage intrusion detection.  In short, it will handle the blinking of the light if an intrusion is detected.
     * @param request This is the request that comes in.
     */
    private void manageIntrusionDetectedState(CommandActionEnum request) {
        switch (request) {
            case INIT:
                intrusionDetectionSubStateVariable = LightControllerStateMachineObserverInterface.LightStateIntrusionDetectedStates.LAMP_ON;
                // Start the timer.
                blinkTimer.setExpirationEvent(CommandActionEnum.LAMP_TIMER_EXPIRED);
                blinkTimer.startPeriodicTimer(1000);
                light.turnLightOnFullBrightness();
                break;
            case LAMP_TIMER_EXPIRED:
                if (intrusionDetectionSubStateVariable == LightControllerStateMachineObserverInterface.LightStateIntrusionDetectedStates.LAMP_ON) {
                    intrusionDetectionSubStateVariable = LightControllerStateMachineObserverInterface.LightStateIntrusionDetectedStates.LAMP_OFF;
                    light.turnLightOff();
                } else {
                    intrusionDetectionSubStateVariable = LightControllerStateMachineObserverInterface.LightStateIntrusionDetectedStates.LAMP_ON;
                    light.turnLightOnFullBrightness();
                }
        }
    }
}
