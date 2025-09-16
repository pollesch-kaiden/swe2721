import javax.swing.JFrame;
import SecurityLightController.LightControllerStateMachine;
import SecurityLightController.LightTimer;
import SecurityLightController.NewLightControllerStateMachine;
import UI.SecurityLampSimulatedUI;

import javax.swing.*;

/**
 * This is the main class. It instantiates instances of the GUI and ties it
 * together with the state machine.
 * 
 * @author schilling
 * 
 */
public class NewMain {
	public static void main(String[] args) {

		NewLightControllerStateMachine lcsm = new NewLightControllerStateMachine();

		JFrame frame = new JFrame("New Security Light Controller GUI");
		SecurityLampSimulatedUI cwrb1 = new SecurityLampSimulatedUI(lcsm);
		frame.setContentPane(cwrb1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		lcsm.subscribe(cwrb1);

		frame.setBounds(300, 0, 20, 10);
		frame.pack();
		frame.setVisible(true);

		lcsm.setLight(cwrb1);
		lcsm.setBlinkTimer(new LightTimer(lcsm));
		lcsm.setMotionDetectedTimer(new LightTimer(lcsm));
	}

}
