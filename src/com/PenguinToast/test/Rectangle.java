package com.PenguinToast.test;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import org.newdawn.slick.geom.Polygon;

public class Rectangle extends Polygon{
	private static final long serialVersionUID = -3752843176428390925L;

	private float width;
	private float height;
	
	public Rectangle(float x, float y, float width, float height) {
		super(new float[] {
			x, y,
			x+width, y,
			x+width, y+height,
			x, y+height
		});
		this.width = width;
		this.height = height;
	}
	public Line2D.Float getBottom() {
		return new Line2D.Float(x, y+height, x+width, y+height);
	}
	public Line2D.Float getTop() {
		return new Line2D.Float(x, y, x+width, y);
	}
	public Line2D.Float getRight() {
		return new Line2D.Float(x+width, y, x+width, y+height);
	}
	public Line2D.Float getLeft() {
		return new Line2D.Float(x, y, x, y+height);
	}
	public boolean intersects(Rectangle r) {
		return getRect().intersects(r.getRect());
	}
	public Rectangle2D.Float getRect() {
		return new Rectangle2D.Float(x, y, width, height);
	}
	public boolean intersects(Line2D l) {
		return getRect().intersectsLine(l);
	}
	public float getWidth() {
		return width;
	}
	public float getHeight() {
		return height;
	}
}
