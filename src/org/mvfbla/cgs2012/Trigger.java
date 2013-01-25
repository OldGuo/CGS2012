package org.mvfbla.cgs2012;

import java.util.ArrayList;



public class Trigger extends TiledObject{

	private TriggerListener listener;
	private ArrayList<GameObject> contained = new ArrayList<GameObject>();
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Trigger(TiledObject to, TriggerListener al) {
		super(to.getX(), to.getY(), to.getWidth(), to.getHeight(), "trigger");
		listener = al;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param type
	 */
	public Trigger(int x, int y, int width, int height, TriggerListener tl) {
		super(x, y, width, height, "trigger");
		listener = tl;
		// TODO Auto-generated constructor stub
	}

	public void hit(GameObject source) {
		listener.triggered(source);	
		if(!contains(source)) {
			contained.add(source);
			listener.onEnter(source);
		}
	}
	public boolean contains(GameObject test) {
		return contained.contains(test);
	}
	public void exit(GameObject source) {
		contained.remove(source);
		listener.onExit(source);
	}
	public boolean collides(GameObject test) {
		GameObject go = new Tile(getX(), getY(), getWidth(), getHeight());
		return test.collides(go) || go.contains(test.getCollision()) || test.getCollision().contains(go);
	}
}
