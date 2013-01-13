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
	 * Creates a Vector from the given vector
	 * @param v
	 */
	public Vector(Vector2f v) {
		super(v);
	}
	/**
	 * Creates an empty vector
	 */
	public Vector() {
		super();
	}
	/**
	 * Creates a unit vector with angle theta
	 * @param theta - Angle of the vector in degrees
	 */
	public Vector(double theta) {
		super(theta);
	}
	/**
	 * Creates a vector with the given coordinates
	 * @param coords - float array containing x and y coordinates
	 */
	public Vector(float[] coords) {
		super(coords);
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
	public strictfp Vector setLength(float length) {
		scale(length/length());
		return this;
	}
	@Override
	public strictfp Vector sub(Vector2f v) {
		super.sub(v);
		return this;
	}
	@Override
	public strictfp Vector add(double theta) {
		super.add(theta);
		return this;
	}
	@Override
	public strictfp Vector sub(double theta) {
		super.sub(theta);
		return this;
	}
	@Override
	public strictfp Vector set(float x, float y) {
		super.set(x, y);
		return this;
	}
	@Override
	public strictfp Vector getPerpendicular() {
		return new Vector(super.getPerpendicular());
	}
	@Override
	public strictfp Vector set(float[] pt) {
		super.set(pt);
		return this;
	}
	@Override
	public strictfp Vector negate() {
		return new Vector(super.negate());
	}
	@Override
	public strictfp Vector negateLocal() {
		super.negateLocal();
		return this;
	}
	@Override
	public strictfp Vector add(Vector2f v) {
		super.add(v);
		return this;
	}
	@Override
	public strictfp Vector normalise() {
		super.normalise();
		return this;
	}
	@Override
	public strictfp Vector getNormal() {
		return new Vector(super.getNormal());
	}
	@Override
	public strictfp Vector copy() {
		return new Vector(this);
	}
	@Override
	public strictfp Vector scale(float a) {
		super.scale(a);
		return this;
	}
}