package org.mvfbla.cgs2012.base;

import java.util.HashMap;

public class TiledObject {
	private int x, y, width, height;
	private String type;
	private HashMap<String, String> properties = new HashMap<String, String>();
	/**
	 * Creates a new TileObject with the specified dimensions
	 * @param x - X value of object
	 * @param y - Y value of object
	 * @param width - width of object
	 * @param height - height of object
	 * @param type - type of object
	 */
	public TiledObject(int x, int y, int width, int height, String type) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
	}
	/**
	 * Adds a property of the object
	 * @param name - Name of the property to add
	 * @param value - Value of added property
	 */
	public void addProperty(String name, String value) {
		properties.put(name, value);
	}
	/**
	 * Gets the height of this object
	 * @return Height of this object
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * Returns the value of the property with the given name
	 * @param name - Name of the property to find
	 * @return Value of the property to find, null if not found
	 */
	public String getProperty(String name) {
		return properties.get(name);
	}
	/**
	 * Gets the type of this object
	 * @return The type of this object
	 */
	public String getType() {
		return type;
	}
	/**
	 * Gets the width of this object
	 * @return The width of this object
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * Gets the X position of this object
	 * @return The X position of this object
	 */
	public int getX() {
		return x;
	}
	/**
	 * Gets the Y position of this object
	 * @return THe Y position of this object
	 */
	public int getY() {
		return y;
	}
	/**
	 * Sets the height of this object
	 * @param height - The new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * Sets the type of this object
	 * @param type - THe new type of this image
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Sets the width of this object
	 * @param width - The new width of this image
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * Sets the X position of this object
	 * @param x - The new x of this object
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Sets the y position of this object
	 * @param y - The new y of this object
	 */
	public void setY(int y) {
		this.y = y;
	}
}
