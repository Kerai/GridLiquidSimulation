package tests;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class Input {
	
	static boolean[] pressed = new boolean[10];
	static boolean[] justPressed = new boolean[10];
	
	
	public static void update() {
		Arrays.fill(justPressed, false);
	}
	
	public static MouseAdapter listener = new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			pressed[e.getButton()] = true;
			justPressed[e.getButton()] = true;
		}
		
		public void mouseReleased(MouseEvent e) {
			pressed[e.getButton()] = false;
		};
		
		public void mouseMoved(MouseEvent e) {
			mousePosition.setLocation(e.getX(), e.getY());
		};
		
		public void mouseDragged(MouseEvent e) {
			mousePosition.setLocation(e.getX(), e.getY());
		};
		
	};
	
	
	public static final Point2D mousePosition = new Point2D.Double();

	public static boolean isMousePressed(int button) {
		return pressed[button];
	}
	public static boolean isMouseJustPressed(int button) {
		return justPressed[button];
	}

}
