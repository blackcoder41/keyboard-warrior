package com.raketlabs.keyboard.warrior;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class Main {
	
	private static Robot robot = null;
	
	private Main() {
		
	}
	
	public static Robot getRobot() {
		if (robot == null) {
			try {
				robot = new Robot();
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
		return robot;
	}
	
	public static void main(String[] args) {
		
		Robot robot = getRobot();
		
		JFrame window = new JFrame("Keyboard Warrior");
		window.setSize(new Dimension(320, 240));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
				
		String title = "I am Keyboard Warrior. ";
		int delay = 1 * 1000;
		int index = 0;
		
		while (true) {
			char c = title.charAt(index);

			
			turnOffCapsLock();
			
			
			if (Character.isUpperCase(c)) {
				robot.keyPress(KeyEvent.VK_SHIFT);
			}
			
			robot.delay(delay);
			robot.keyPress(Character.toUpperCase(c));
			robot.keyRelease(Character.toUpperCase(c));
			
			if (Character.isUpperCase(c)) {
				robot.keyRelease(KeyEvent.VK_SHIFT);
			}
			
			index = (index + 1) % title.length();
		}
	}
	
	public static void turnOffCapsLock() {
		boolean isCapsLock = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
		
		if (isCapsLock) {
			getRobot().keyPress(KeyEvent.VK_CAPS_LOCK);
			getRobot().keyRelease(KeyEvent.VK_CAPS_LOCK);
		}
	}
}