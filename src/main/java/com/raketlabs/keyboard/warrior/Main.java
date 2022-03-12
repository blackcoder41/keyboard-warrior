package com.raketlabs.keyboard.warrior;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		
		Robot robot = getRobot();
		
		JFrame window = new JFrame("Keyboard Warrior");
		window.setSize(new Dimension(320, 240));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
				
		String title = "I am Keyboard Warrior. ";
		int delay = 3 * 60 * 1000;
		int index = 0;
		
		while (true) {
			try {
				char c = title.charAt(index);
				
				int remainingDelay = delay;
				
				while (remainingDelay > 0) {
					int d = Math.min(60000, delay);
					d = Math.min(remainingDelay, d);
					robot.delay(d);
					remainingDelay -= d;
				}

				turnOffCapsLock();

				if (Character.isUpperCase(c)) {
					robot.keyPress(KeyEvent.VK_SHIFT);
				}

				robot.keyPress(Character.toUpperCase(c));
				robot.keyRelease(Character.toUpperCase(c));

				if (Character.isUpperCase(c)) {
					robot.keyRelease(KeyEvent.VK_SHIFT);
				}

				index = (index + 1) % title.length();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(window, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
			}
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