package graphics;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GraphicsMain {	
	public static void main(String[] args) {
		JFrame mainDisplay = new JFrame();
		mainDisplay.setSize(1440, 720);
		int windowSize = 300;
		JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); 
		//container.setLayout(new BoxLayout(container, FlowLayout));
		for(int i = 1; i < 10; i++) {
			Scene3D s = new Scene3D(i, true, windowSize);
			s.setPreferredSize(new Dimension(windowSize, windowSize));
			container.add(s);
		}
		mainDisplay.add(container);
		mainDisplay.setTitle("Project BigCube");
		mainDisplay.setVisible(true);
		mainDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
