package org.mvfbla.cgs2012;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.GeomUtil;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;

public class Character extends AnimatedObject {

	//private float velX, velY;
	private Vector force;

	public Character(int x, int y, int width, int height, String fileLoc) throws SlickException {
		super(x, y, width, height, fileLoc);
		force = new Vector(0,0);
	}
	public int newCollision(GameObject obj) {
		int out = 0;
		Vector trans = getProjectionVector(obj, this);
		if(trans != null) {
			translate(trans);
			force.sub(trans);
			System.out.println(force);
		}
		return out;
	}
	public Vector getProjectionVector(GameObject a, GameObject b) {
		Vector v1 = getProjectionVectorHelper(a, b);
		Vector v2 = getProjectionVectorHelper(b, a);
		if(v1 != null && v2 != null) {
			if(v1.length() < v2.length())
				return v1;
			else
				return v2.negateLocal();
		} else
			return null;
	}
	private Vector getProjectionVectorHelper(GameObject aGO, GameObject bGO) {
		Shape a = aGO.getCollision();
		Shape b = bGO.getCollision();
		// Vector to return
		Vector out = null;
		// Vector connecting the centers of the two Polygons
		Vector connect = toVector(new Line(a.getCenterX(), a.getCenterY(), b.getCenterX(), b.getCenterY()));
		// float for holding the smallest vector size found
		float minDot = Float.MAX_VALUE;
		GeomUtil g = new GeomUtil();
		// Loop through every side of Polygon a
		for(int i = 0; i < a.getPointCount(); i++) {
			// Current side
			Line side = g.getLine(a, i, i == a.getPointCount()-1 ? 0 : i+1);
			// Vector for the line we will be projecting onto
			Vector project = toVector(side).getPerpendicular().normalise();
			// Check if the side is facing Polygon b
			float facing = project.dot(connect);
			if(facing >= 0) {
				// Vector representing the side of Polygon a
				Vector vectorA = new Vector(side.getX1(), side.getY1());
				vectorA.projectOntoUnit(project, vectorA);
				// Find the correct point to project from Polygon b
				float min = Float.MAX_VALUE;
				// The minimum point found
				Vector minPoint = new Vector();
				for(int j = 0; j < b.getPointCount(); j++) {
					// The point currently being checked
					Vector point = new Vector(b.getPoint(j));
					Vector comp = new Vector();
					point.projectOntoUnit(project, comp);
					// Do comparisons
					if(project.dot(comp) < min) {
						min = project.dot(comp);
						minPoint = point;
					}
				}	
				// Get the projection of the two polygons
				Vector perp = getPerpendicular(minPoint, side);
				float dotp = perp.dot(project);
				// If the projection and the side are not in the same direction, there is no collision
				if(dotp < 0)
					return null;
				else if(dotp < minDot) {
					minDot = dotp;
					out = perp;
				}
			}
		}
 		return out;
	}
	public Vector getPerpendicular(Vector p, Line l) {
		Vector v = toVector(l);
		Vector d = new Vector(v.getNormal());
		Vector a = new Vector(l.getX1(), l.getY1());
		Vector x = new Vector(a).add(new Vector(d).scale(new Vector(p).sub(a).dot(d)));
		Vector result = new Vector(x).sub(p);
		return result;
	}
	public Vector toVector(Line l) {
		return new Vector(l.getX2()-l.getX1(), l.getY2() - l.getY1());
	}
	public float getVelX() {
		return force.x;
	}
	public void setVelX(float velX) {
		force.x = velX;
	}
	public float getVelY() {
		return force.y;
	}
	public void setVelY(float velY) {
		force.y = velY;
	}
	public Vector getForce() {
		return force;
	}
	public void setForce(Vector v) {
		force = v;
	}

}
