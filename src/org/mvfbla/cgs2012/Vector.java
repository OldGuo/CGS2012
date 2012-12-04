package org.mvfbla.cgs2012;

import org.newdawn.slick.geom.Vector2f;
import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;
import static java.lang.StrictMath.toRadians;


/**
 * @author PenguinToast
 * A simple vector class to represent a 2d vector.
 */
public class Vector extends Vector2f {
	
	/**
	 * Creates a vector with the given X and Y components.
	 * @param x - The X component of the vector
	 * @param y - The Y component of the vector
	 */
	public Vector(float x, float y) {
		super(x,y);
	}
	/**
	 * Creates an empty vector
	 */
	public Vector() {
		super();
	}
	/**
	 * Creates a vector given the length and the angle
	 * @param theta - The angle of the vector to be created, in degrees
	 * @param length - The length of the vector to be created
	 */
	public Vector(double theta, float length) {
		this((float)(length * cos(toRadians(theta))), (float)(length * sin(toRadians(theta))));
	}
	/**
	 * Sets the length/magnitude of the vector
	 * @param length - The length to set the vector to
	 */
	public void setLength(float length) {
		scale(length/length());
	}
}
