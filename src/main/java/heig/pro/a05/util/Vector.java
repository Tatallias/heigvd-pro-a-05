package heig.pro.a05.util;

public class Vector {
	private double x, y;

	/**
	 * Constructor of a vector, taking a starting position and an ending position as parameter.
	 *
	 * @param x1 the x coordinate of the starting position
	 * @param x2 the x coordinate of the ending position
	 * @param y1 the y coordinate of the starting position
	 * @param y2 the y coordinate of the ending position
	 */
	public Vector(double x1, double x2, double y1, double y2) {
		this(x2 - x1, y2 - y1);
	}

	/**
	 * Constructor of a 2D vector, taking both coordinate as parameter.
	 *
	 * @param x the x coordinate of the vector
	 * @param y the y coordinate of the vector
	 */
	public Vector(double x, double y) {
		this.setX(x);
		this.setY(y);
	}

	public void normalize(){
		double x2 = x*x;
		double y2 = y*y;
		double norm = Math.sqrt(x2+y2);
		this.x /= norm;
		this.y /= norm;
	}

	/**
	 * Returns the angle in radians formed by the vector 
	 * 
	 * @return the angle in radians formed by the vector
	 */
	public double getAngle(Vector v) {
		double dotProduct = v.x*x + v.y*y;
		double firstNorm = Math.sqrt(x*x + y*y);
		double secondNorm = Math.sqrt(v.x*v.x + v.y*v.y);
		return Math.acos(dotProduct/(firstNorm * secondNorm));
	}

	/**
	 * Getter of the y coordinate
	 *
	 * @return the y coordinate of the vector
	 */
	public double getY() {
		return y;
	}

	/**
	 * Setter of the y coordinate of a vector
	 *
	 * @param y the new y coordinate
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Getter of the x coordinate of a vector
	 *
	 * @return the x coordinate of the vector
	 */
	public double getX() {
		return x;
	}

	/**
	 * Setter of the x coordinate of a vector
	 *
	 * @param x the new x coordinate
	 */
	public void setX(double x) {
		this.x = x;
	}
}
