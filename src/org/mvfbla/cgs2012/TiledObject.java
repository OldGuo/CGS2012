package org.mvfbla.cgs2012;

import java.util.HashMap;

public class TiledObject {
	private int x, y, width, height;
	private String type;
	private HashMap<String, String> properties = new HashMap<String, String>();
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param type
	 */
	public TiledObject(int x, int y, int width, int height, String type) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void addProperty(String name, String value) {
		properties.put(name, value);
	}
	public String getProperty(String name) {
		return properties.get(name);
	}
}
