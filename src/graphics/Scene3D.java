package graphics;

import javax.swing.JPanel;

import java.awt.Graphics;

import common.datastructures.concrete.*;
import common.datastructures.interfaces.*;
import math.linalg.lin3d.*;

public class Scene3D extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final double[] viewToLoc = new double[] {0,0,0};
	
	private static int FPS = 60;
	private double[] cameraLoc;
	private Vector3D screenb1, screenb2; 
	
	private Plane3D viewPlane;
	private double screenWidth, screenHeight; 
	private int FOV;
	

	private IList<Polygon3D> polys = new DoubleLinkedList<>();
	
	  
	public void paintComponent(Graphics g) {
		// TODO: Draw everything here. 
	}
	
	public Scene3D() {
		
	}
	
	
	/**
	 * Take the point which is a projection onto the plane and gets the 
	 * coordinate in terms of the plane. 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static double[] getDrawableCoordinate(double x, double y, double z) {
		return null;
	}
	
	
	/**
	 * Generates the cubes in the scene.
	 */
	public void generateCubes(int size) {
		
	}
	
	public Vector3D getRotatation() {
		return null;
		
	}
	
}
