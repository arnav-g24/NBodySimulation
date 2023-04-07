import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NBodySimulation {
	private Body[] bodies; // stores all the bodies in the simulation
	private int numBodies; // number of bodies in this simulation
	private double uRadius; // radius of the universe
	private String fileName; // file providing the input

	public NBodySimulation(String nameOfFile) {
		Scanner scan = null;
		fileName = nameOfFile;
		try {
			scan = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("file not found!");
			e.printStackTrace();
		}

		numBodies = scan.nextInt();
		uRadius = scan.nextDouble();
		bodies = new Body[numBodies];

		for (int i = 0; i < numBodies; i++) {
			bodies[i] = new Body(scan.nextDouble(), scan.nextDouble(), scan.nextDouble(), scan.nextDouble(),
					scan.nextDouble(), scan.next());
		}

		initCanvas(); // don't move, should be the last line of the constructor
	}

	/** initialize the drawing canvas */
	private void initCanvas() {
		StdDraw.setScale(-uRadius, uRadius); // set canvas size / scale
		StdDraw.picture(0, 0, "starfield.jpg"); // paint background

		// below is a for-each loop, which we will cover in the next lab
		// more info is available in the powerpoints, for the curious
		for (Body body : bodies)
			body.draw();
	}

	/**
	 * run the n-bodies simulation
	 * 
	 * @param T  total time to run the simulation, in seconds
	 * @param dt time step, in seconds
	 */
	public void run(double T, double dt) {

		for (double i = 0; i < T; i = i + dt) {
			for (int k = 0; k < numBodies; k++) {
				bodies[k].setNetForce(bodies);
				bodies[k].update(dt);
			}
			StdDraw.picture(0, 0, "starfield.jpg");

			for (int j = 0; j < numBodies; j++) {
				StdDraw.picture(bodies[j].getX(), bodies[j].getY(), bodies[j].image);
			}
			StdDraw.show(10);
		}

	}
}
