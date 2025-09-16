package UI;

import SecurityLightController.LightControllerCommandInterface;
import SecurityLightController.LightControllerCommandInterface.CommandActionEnum;
import SecurityLightController.LightControllerStateMachineObserverInterface;
import SecurityLightController.LightDeviceInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class implements a basic UI for usage with the light controller. It
 * simulates that which would exist in a real world light timer implementation.
 *
 * @author schilling
 */
public class SecurityLampSimulatedUI extends JPanel implements
        LightDeviceInterface, LightControllerStateMachineObserverInterface {

    private static final long serialVersionUID = 1L;
    private JLabel lamp;
    private LightControllerCommandInterface lcsm;
    private JLabel state;

    /**
     * This constructor will instantiate a new GUI instance.
     *
     * @param lcsm This is the instance of the light controller state machine
     *             that is to be used.
     */
    public SecurityLampSimulatedUI(LightControllerCommandInterface lcsm) {
        super();
        this.lcsm = lcsm;
        this.setLayout(new GridBagLayout());
        this.addContents();
    }

    /**
     * This method will add the contents to the given panel.
     */
    private void addContents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.ipadx = 5;
        gbc.ipady = 5;

        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.0;
        this.add(new JLabel("Manual Lamp Override"), gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;

        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.0;

        JButton onSwitch = new JButton("ON");
        JButton offSwitch = new JButton("OFF");
        this.add(onSwitch, gbc);
        gbc.gridx = 3;
        this.add(offSwitch, gbc);

        offSwitch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                lcsm.signalAction(CommandActionEnum.MANUAL_SWITCH_OFF);
            }
        });

        onSwitch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                lcsm.signalAction(CommandActionEnum.MANUAL_SWITCH_ON);
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;

        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.0;
        this.add(new JLabel("Light Sensor"), gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;

        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        gbc.insets = new Insets(10, 0, 0, 0);

        JButton darkButton = new JButton("DARK");
        JButton lightButton = new JButton("LIGHT");
        this.add(darkButton, gbc);


        gbc.gridx = 3;
        gbc.insets = new Insets(10, 10, 0, 0);
        this.add(lightButton, gbc);

        darkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                lcsm.signalAction(CommandActionEnum.LIGHT_SENSOR_DARKENED);
            }
        });


        lightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                lcsm.signalAction(CommandActionEnum.LIGHT_SENSOR_LIGHTENED);
            }
        });

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        gbc.gridheight = 1;
        gbc.weightx = 0.25;
        gbc.weighty = 0.25;
        JButton motionDetectedButton = new JButton("Motion Detected");
        motionDetectedButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                lcsm
                        .signalAction(CommandActionEnum.MOTION_DETECTED);
            }
        });
        this.add(motionDetectedButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        JButton alarmTripped = new JButton("Alarm Tripped");
        alarmTripped.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                lcsm
                        .signalAction(CommandActionEnum.SECURITY_ALARM_TRIPPED);
            }
        });
        this.add(alarmTripped, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        JButton cancelButton = new JButton("Cancel Alarm");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                lcsm
                        .signalAction(CommandActionEnum.ALARM_CLEARED);
            }
        });
        this.add(cancelButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        JLabel stateLabel = new JLabel("State");
        this.add(stateLabel, gbc);

        gbc.gridx = 2;
        this.state = new JLabel("Lamp Off Daylight");
        this.add(state, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;

        gbc.gridheight = 3;
        gbc.weightx = 0.5;
        gbc.weighty = 0.0;
        this.lamp = new JLabel(this.createImageIcon("dark.gif",
                "A darkened lamp"));
        this.add(this.lamp, gbc);


    }

    /*
     * (non-Javadoc)
     *
     * @see SecurityLightController.LightDeviceInterface#turnLightOff()
     */
    public void turnLightOff() {
        this.lamp.setIcon(this.createImageIcon("dark.gif", "A darkened lamp"));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * SecurityLightController.LightDeviceInterface#turnLightOnFullBrightness()
     */
    public void turnLightOnFullBrightness() {
        this.lamp.setIcon(this.createImageIcon("bright.gif", "A bright lamp"));

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * SecurityLightController.LightDeviceInterface#turnLightOnNightimeBrightness
     * ()
     */
    public void turnLightOnNightimeBrightness() {
        this.lamp.setIcon(this.createImageIcon("dim.gif", "A dim lamp"));
    }

    /**
     * This method will create an image icon.
     *
     * @param path        This is the path to the icon.
     * @param description This is a description of the icon.
     * @return Returns an ImageIcon, or null if the path was invalid.
     */
    private ImageIcon createImageIcon(String path, String description) {
        ClassLoader cldr = this.getClass().getClassLoader();
        java.net.URL imageURL = cldr.getResource(path);

        if (imageURL != null) {
            return new ImageIcon(imageURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    @Override
    public void updateLightState(LightState newState) {
        switch (newState) {
            case LAMP_OFF_DAYLIGHT:
                state.setText("Lamp Off Daylight");
                break;
            case LAMP_ON_FULL_BRIGHTNESS:
                state.setText("Lamp On Full Brightness");
                break;
            case LAMP_OFF_NIGHTIME:
                state.setText("Lamp Off Night Time");
                break;
            case LAMP_ON_NIGHTIME_BRIGHTNESS:
                state.setText("Lamp On Nightime Brightness");
                break;
            case MOTION_DETECTED:
                state.setText("Motion Detected");
                break;
            case INTRUSION_DETECTED:
                state.setText("Intrusion Detected");
                break;
        }


    }
}
