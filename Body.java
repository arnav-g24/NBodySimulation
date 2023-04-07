import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Body {
	public static final double G = 6.67E-11; // Newtons' gravitational constant

	private double x ; // planet's x and y coordinate position
	private double y ;
	private double xVelocity, yVelocity;
	private double mass;
	private double xNetForce, yNetForce; // net forces action on this planet
	private double xAccel, yAccel;
	public String image; // image of this planet (for drawing)

	public static void main(String[] args) {
		NumberFormat numFormat = new DecimalFormat();

		new Body(0.5, 0.5, 0.0, 0.0, 0.0, "earth.gif").draw();
		
		Body body = new Body(0.5, 0.5, 0.0, 0.0, 0.0, " ");
		double ans = body.getDistance(new Body(1.0, 1.0, 0.0, 0.0, 0.0, " "));
		numFormat = new DecimalFormat("0.####");
		System.out.println(numFormat.format(ans));
		
		ans = 0.0;

		Body other = new Body(0.0, 0.0, 0.0, 0.0, 6E24, " ");
		ans = other.getPairwiseForce(new Body(1.0, 1.0, 0.0, 0.0, 7E24, " "));
		numFormat = new DecimalFormat("0.##E0");
		System.out.println(numFormat.format(ans));

		ans = 0.0;

		ans = other.getPairwiseX(new Body(1.0, 1.0, 0.0, 0.0, 7E24, " "));
		numFormat = new DecimalFormat("0.##E0");
		System.out.println(numFormat.format(ans));

		ans = 0.0;

		ans = other.getPairwiseY(new Body(1.0, 1.0, 0.0, 0.0, 7E24, " "));
		numFormat = new DecimalFormat("0.##E0");
		System.out.println(numFormat.format(ans));
		
		Body mercury = new Body(0,0,0,0, 3.2E23, " ");
		Body venus = new Body(0,0,0,0, 4.8E24, " ");
		Body mars = new Body(0,0,0,0, 6.4E23, " ");
		Body[] bodies = {mercury, venus, mars};
		
		Body current = new Body( 0, 0, 0, 0, 5.9E24," ");
		
		current.setNetForce(bodies);
		
	}

	public Body() {
		mass = 0;
		image = "";
		x = 0;
		y = 0;
		xVelocity = 0;
		yVelocity = 0;
	}

	public Body(double a, double b, double c, double d, double e, String f) {
		x = a;
		y = b;
		xVelocity = c;
		yVelocity = d;
		mass = e;
		image = f;
	}

	public void setNetForce(Body[] bodies) {
		xNetForce = 0;
		yNetForce = 0;
		
		for(Body body: bodies) {
			if(this == body) {
				continue;
			}
			xNetForce += getPairwiseX (body);
			yNetForce += getPairwiseY (body);
		}	
	}

	
	public void update(double dt) {
		xAccel = xNetForce/mass;
		yAccel = yNetForce/mass;
		xVelocity += xAccel * dt;
		yVelocity += yAccel * dt;
		x += xVelocity * dt;
		y += yVelocity * dt;
	}

	/** Draws the body using the StdDraw library file's drawing method */
	public void draw() {
		StdDraw.picture(x, y, image);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getMass() {
		return mass;
	}

	private double getDistance(Body other) {
		double dx = other.getX() - x;
		double dy = other.getY() - y;
		
		return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
	}

	private double getPairwiseForce(Body other) {
		// g=((G)(m1)(m2))/(r)^2

		double g = (G * mass * other.getMass()) / (Math.pow(getDistance(other), 2));

		return g;
	}

	private double getPairwiseX(Body other) {

		double dx = other.getX() - x;
		return getPairwiseForce(other) * (dx/getDistance(other));

	}

	private double getPairwiseY(Body other) {

		double dy = other.getY() - y;
		return getPairwiseForce(other) * (dy/getDistance(other));
	}

}
