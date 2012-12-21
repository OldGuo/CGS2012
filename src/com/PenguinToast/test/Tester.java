package com.PenguinToast.test;

import java.util.ArrayList;

import org.mvfbla.cgs2012.Vector;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.GeomUtil;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;



public class Tester extends BasicGame{
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	ArrayList<Shape> shapes2 = new ArrayList<Shape>();
	ArrayList<Vector> vectors = new ArrayList<Vector>();
	Shape poly, poly2;
	Line con;
	boolean changed;
	public Tester()
	{
		super("Vector Helper");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setVSync(true);
//		gc.setTargetFrameRate(3);
/*		poly = new Polygon(new float[] {
				10, 10,
				20, 50, 
				60, 40,
				30, -10
		}); */
/*		poly = new Polygon(new float[] {
			10, 10,
			10, 70,
			70, 70,
			70, 10
		});*/
		poly = new Polygon(new float[] {
			10, 10,
			10, 50,
			70, 10
		});
		poly2 = new Polygon(new float[] {
				40, 40,
				40, 80,
				80, 80,
				80, 40
		});
		shapes.add(poly);
		shapes.add(poly2);
	}
	public Vector getVector(Line l) {
		return new Vector(l.getX2()-l.getX1(), l.getY2() - l.getY1());
	}
	public Vector getPerpendicular(Vector p, Line l) {
		Vector v = getVector(l);
		Vector d = new Vector(v.getNormal());
		Vector a = new Vector(l.getX1(), l.getY1());
		Vector x = new Vector(a).add(new Vector(d).scale(new Vector(p).sub(a).dot(d)));
		Vector result = new Vector(x).sub(p);
		return result;
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		shapes.clear();
		shapes2.clear();
		vectors.clear();
		float speed = 1;
		if(gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
			changed = true;
			poly2 = poly2.transform(Transform.createTranslateTransform(speed, 0));
		}
		if(gc.getInput().isKeyDown(Input.KEY_LEFT)) {
			changed = true;
			poly2 = poly2.transform(Transform.createTranslateTransform(-speed, 0));
		}
		if(gc.getInput().isKeyDown(Input.KEY_UP)) {
			changed = true;
			poly2 = poly2.transform(Transform.createTranslateTransform(0, speed));
		}
		if(gc.getInput().isKeyDown(Input.KEY_DOWN)) {
			changed = true;
			poly2 = poly2.transform(Transform.createTranslateTransform(0, -speed));
		}
		Vector trans = getProjectionVector(poly, poly2);
		Vector trans2 = getProjectionVector(poly2, poly);
		if(trans != null && trans2 != null) {
			if(trans.length() < trans2.length())
				applyVec(poly2, trans);
			else
				applyVec(poly2, trans2.negateLocal());
		}
		shapes.add(poly);
		shapes.add(poly2);
	}
	public Vector getProjectionVector(Shape a, Shape b) {
		// Vector to return
		Vector out = null;
		// Vector connecting the centers of the two Polygons
		Vector connect = getVector(new Line(a.getCenterX(), a.getCenterY(), b.getCenterX(), b.getCenterY()));
		// float for holding the smallest vector size found
		float minDot = Float.MAX_VALUE;
		GeomUtil g = new GeomUtil();
		// Loop through every side of Polygon a
		for(int i = 0; i < a.getPointCount(); i++) {
			// Current side
			Line side = g.getLine(a, i, i == a.getPointCount()-1 ? 0 : i+1);
			// Vector for the line we will be projecting onto
			Vector project = getVector(side).getPerpendicular().normalise();
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
	public int getSign(Vector v) {
		double theta = v.getTheta();
		if(theta < 180)
			return 1;
		else
			return -1;
	}
	public void applyVec(Shape s, Vector v) {
		s.setX(s.getX()+v.getX());
		s.setY(s.getY()+v.getY());
	}
	public int getOverlap() {
		int out = 0;
		return out;
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.scale(2, -2);
		g.translate(gc.getWidth()/4, -gc.getHeight()/4);
		g.setBackground(Color.white);
		g.setColor(Color.red);
		g.draw(new Line(-400, 0, 400, 0));
		g.draw(new Line(0, -300, 0, 300));
		g.setColor(Color.blue);
		for(Shape s : shapes)
			g.draw(s);
		g.setColor(Color.black);
		for(Vector v : vectors)
			g.draw(new Line(v.getX(), v.getY()));
		g.setColor(Color.green);
		for(Shape s : shapes2)
			g.draw(s);;
	}

	public static void main(String[] args) 
			throws SlickException
			{
		AppGameContainer app = 
				new AppGameContainer(new Tester());

		app.setDisplayMode(800, 600, false);
		app.start();
			}
}
