
package org.usfirst.frc.team4787.robot;


import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
    RobotDrive myRobot;
    Joystick stick;
	 Joystick mechstick; JoystickButton trigger;
	    final int  flywheel1_PWM = 0, flywheel2_PWM = 1,
	    		angler1_PWM = 2, angler2_PWM = 3,
	    		ballPusher_PWM = 9,
	    		MECHSTICK_NUM = 0,
	    		UP_LIMIT = 0, DN_LIMIT = 1; // PWM, Stick, and Limiter ports
	    //name PWMs ^^
	    final double TEST_TIMEOUT = .5;
	    final int TRIGGER_BTN = 1;
	    int motorSwitch = 0; // Current motor for testing
	    final double DEADZONEX = 0.08, DEADZONEY = 0.08, DEADZONEMECH = 0.06; //Deadzones
	    double lastTime = 0, expX = 0, expY = 0, x = 0, y = 0, z = 0, mechX = 0, mechY = 0, mechZ = 0; // Motor powers
	    
	    Jaguar fly1 = new Jaguar(flywheel1_PWM);
	    Jaguar fly2 = new Jaguar(flywheel2_PWM);
	    Jaguar angler1 = new Jaguar(angler1_PWM);
	    Jaguar angler2 = new Jaguar(angler2_PWM);
	    Servo ballPusher1 = new Servo(ballPusher_PWM);
	    
	    double pusherPos=1, pusherMin=0, pusherMax=1, pusherStep = .007;
	    double intakePower = .25;
    public Robot() {
        mechstick = new Joystick(MECHSTICK_NUM);
    	trigger = new JoystickButton(mechstick, 1);
    }
    
    public void robotInit() {
    }

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the if-else structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomous() {
    	
    
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
        while (isOperatorControl() && isEnabled()) {
        	//when stick is moved out of deadzones, provide power to motor to move up/down
        	
        	double mechx = mechstick.getX();
        	double mechy = mechstick.getY();
        	System.out.println(mechy + "");
        	if(Math.abs(mechy) > DEADZONEY) {
        		angler1.set(mechy);
        		angler2.set(-mechy);
        	}
        	else {
        		angler1.set(0);
        		angler2.set(0);
        	}
        	
        	if(mechstick.getRawButton(2)) { // intake
        		fly1.set(intakePower);
        		fly2.set(-intakePower);
        	} else if (mechstick.getRawButton(3)) { // spinup
        		fly1.set(-1);
        		fly2.set(1);
            	if(mechstick.getRawButton(1)) { // trigger fires the ball.
            		pusherPos = pusherPos - pusherStep < pusherMin ? pusherMin : pusherPos - pusherStep;
            	}
        	} else {
        		fly1.set(0);
        		fly2.set(0);
        		pusherPos = pusherPos+pusherStep > pusherMax ? pusherMax : pusherPos+ pusherStep;
        	}
        	

        	ballPusher1.set(pusherPos);
        	
        	Timer.delay(0.005);
        }
    }

    /**
     * Runs during test mode
     */
    public void test() {
    }
}
