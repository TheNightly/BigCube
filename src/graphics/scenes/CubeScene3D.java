package graphics.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import common.datastructures.concrete.*;
import common.datastructures.interfaces.*;
import cube.GameCube;
import graphics.Polygon3D;
import graphics.PolygonDistancePair;
import graphics.SceneCube;
import cube.FullStickerCube;
import math.linalg.lin3d.*;

public class CubeScene3D extends Scene3D implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	// Camera Properties
	private static final double CAMERA_ROTATION_INTERVAL = 0.001;
	private boolean[] keysHeld;
	private int zoom;

	private SceneCube[][][] magicCube; // Keeping a pointer to all the cube objects in the magic Cube. (0,0,0) is top
										// left, (n,n,n) is bottom right

	private GameCube gameCube; // Game object to keep.
	public static final double cubeSpacing = 0.1;

	// Animation Variables
	private boolean animationOn;
	private static final int ANIMATION_STEPS = 100;

	public CubeScene3D(int cubeSize, boolean animations, int screenWidth) {
		super(screenWidth, screenWidth, 60, true);
		zoom = (screenWidth * screenWidth) / (cubeSize * cubeSize * 500);
		keysHeld = new boolean[4];
		gameCube = new FullStickerCube(cubeSize);
		
		lastRefresh = System.currentTimeMillis();
		this.animationOn = animations;

		// Generating the initial viewPlane
		viewPlane = new Plane3d(Math.pow(cubeSize,  2), 0, 0, Lin3d.zBasis, Lin3d.yBasis);

		// Generating the Rubicks cube
		generateScene(cubeSize);
		keysHeld[0] = true;
		keysHeld[1] = true;
	}

	public CubeScene3D(int size) {
		this(size, true, 720);
	}

	protected void drawBackground(Graphics g) {
		g.setColor(new Color(140, 180, 180));
		g.fillRect(0, 0, screenWidth, screenWidth);
		g.setColor(Color.black);
	}
	
	protected void render(Graphics g) {
		for (Polygon3D p : polys) {
			p.drawPolygon(g);
		}
	}
	
	protected void displayDebug(Graphics g) {
		g.drawString("FPS: " + (int) drawFPS + " (Benchmark)", 40, 40);
		g.drawString("Current Camera Loc: (" + getCameraLoc().getX() + ", " + getCameraLoc().getY() + ", "
				+ getCameraLoc().getZ() + ")", 40, 60);
		//g.drawString("Camera Radius: " + LinAlg.norm(getCameraLoc(), 2), 40, 80);
		g.drawString("Zoom: " + zoom, 40, 80);
	}

	protected boolean updateScene() {
		// TODO Continue/Finish any animation that is happening.
		if (animationOn) {

		} else {

		}
		// throw new NotYetImplementedException();
		return false;
	}

	/**
	 * Updates the camera based on key presses.
	 * 
	 * @return
	 */
	protected boolean updateCamera() {
		boolean cameraMovement = false;
		for (int i = 0; i < keysHeld.length; i++) {
			if (keysHeld[i] && !keysHeld[(i + 2) % keysHeld.length]) {
				cameraMovement = true;
				int direction = ((i > 1) ? -1 : 1) * gameCube.getSize();
				if (i % 2 == 0) {
					viewPlane.applyTransform(Lin3d.getRotationAroundY(direction * CAMERA_ROTATION_INTERVAL));
				} else {
					viewPlane.applyTransform(Lin3d.getRotationAroundZ(direction * CAMERA_ROTATION_INTERVAL));
				}
			}
		}
		return cameraMovement;
	}

	/**
	 * Sorts polys in non-decreasing order of distance from camera, and updates each
	 * drawable.
	 */
	protected void updateDrawables() {
		IPriorityQueue<PolygonDistancePair> pq = new ArrayHeap<>();
		Vector3d cameraLoc = getCameraLoc();
		
		while (!polys.isEmpty()) {
			Polygon3D currentPoly = polys.remove();
			pq.insert(new PolygonDistancePair(currentPoly, currentPoly.getAverageDistance(cameraLoc)));
		}

		while (!pq.isEmpty()) {
			Polygon3D p = pq.removeMin().getPolygon();
			p.updateDrawable(viewPlane, zoom, screenWidth);
			p.calculateLighting(viewPlane);
			polys.add(p);
		}
		
		/*for (Polygon3D p : polys) {
			p.updateDrawable(viewPlane, zoom, screenWidth);
		}*/
		
	}

	/**
	 * Returns the camera location.
	 * 
	 * @return
	 */
	public Vector3d getCameraLoc() {
		return this.viewPlane.getPoint();
	}


	/**
	 * Generates the cubes in the scene.
	 */
	public void generateScene(int size) {
		double offSet = size % 2 == 1 ? -0.5: 0;
		int width = 1;
		int half = size / 2;
		// Draw the cubes.
		for (int x = - half; x < half + size % 2 ; x += width) {
			for (int y = - half; y < half + size % 2; y += width) {
				for (int z = - half; z < half + size % 2; z += width) {
					SceneCube c = new SceneCube(this, x + offSet, y + offSet, z + offSet, width);
				}
			}
		}
		updateDrawables();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W)
			keysHeld[0] = true;
		if (e.getKeyCode() == KeyEvent.VK_A)
			keysHeld[1] = true;
		if (e.getKeyCode() == KeyEvent.VK_S)
			keysHeld[2] = true;
		if (e.getKeyCode() == KeyEvent.VK_D)
			keysHeld[3] = true;
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W)
			keysHeld[0] = false;
		if (e.getKeyCode() == KeyEvent.VK_A)
			keysHeld[1] = false;
		if (e.getKeyCode() == KeyEvent.VK_S)
			keysHeld[2] = false;
		if (e.getKeyCode() == KeyEvent.VK_D)
			keysHeld[3] = false;

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
