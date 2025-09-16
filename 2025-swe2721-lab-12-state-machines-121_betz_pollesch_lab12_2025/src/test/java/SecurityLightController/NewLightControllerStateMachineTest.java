package SecurityLightController;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * This class will test the operation of the light controller state machine class.
 */
public class NewLightControllerStateMachineTest {
    private NewLightControllerStateMachine sm;

    @Mock
    private LightControllerStateMachineObserverInterface observer;
    @Mock
    private LightDeviceInterface device;
    @Mock
    private LightTimerInterface blinkTimer;
    @Mock
    private LightTimerInterface motionDetectedTimer;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        sm = new NewLightControllerStateMachine();
        observer = mock(LightControllerStateMachineObserverInterface.class);
        device = mock(LightDeviceInterface.class);
        blinkTimer = mock(LightTimerInterface.class);
        motionDetectedTimer = mock(LightTimerInterface.class);

        sm.setLight(device);
        sm.setBlinkTimer(blinkTimer);
        sm.setMotionDetectedTimer(motionDetectedTimer);
    }

    @Test(groups = {"all", "studentsmtests"})
    public void testConstructor() throws NoSuchFieldException, IllegalAccessException {
        Field initialStateField = NewLightControllerStateMachine.class.getDeclaredField("currentState");
        initialStateField.setAccessible(true);
        assertEquals(initialStateField.get(sm), LightControllerStateMachineObserverInterface.LightState.LAMP_OFF_DAYLIGHT);
    }

    @Test(groups = {"all", "studentsmtests"})
    public void testSubscribeAndUnsubscribe() throws NoSuchFieldException, IllegalAccessException {
        sm.subscribe(observer);
        Field observersField = NewLightControllerStateMachine.class.getDeclaredField("observers");
        observersField.setAccessible(true);
        List<?> observers = (List<?>) observersField.get(sm);
        assertEquals(observers.size(), 1);

        sm.unsubscribe(observer);
        observers = (List<?>) observersField.get(sm);
        assertEquals(observers.size(), 0);
    }

    @Test(groups = {"all", "studentsmtests"})
    public void testSignalActionTransitions() {
        sm.subscribe(observer);

        sm.signalAction(LightControllerCommandInterface.CommandActionEnum.LIGHT_SENSOR_DARKENED);
        verify(device).turnLightOff();

        sm.signalAction(LightControllerCommandInterface.CommandActionEnum.MANUAL_SWITCH_ON);
        verify(device).turnLightOnFullBrightness();

        sm.signalAction(LightControllerCommandInterface.CommandActionEnum.MOTION_DETECTED);
        verify(device).turnLightOnFullBrightness();
        verify(motionDetectedTimer).startOneShotTimer(30000);

        sm.signalAction(LightControllerCommandInterface.CommandActionEnum.SECURITY_ALARM_TRIPPED);
        verify(device, atLeastOnce()).turnLightOnFullBrightness();
        verify(blinkTimer).startPeriodicTimer(1000);
    }
}