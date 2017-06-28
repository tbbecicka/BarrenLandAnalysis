package com.becicka.barrenlandanalysis;

/**
 * Represents location of land via X and Y Coordinates
 * @author Todd
 *
 */
public class Location {
	private final int x;
	private final int y;
	private boolean barren;
	//has location been hit during graph traversal
	private boolean visited;
	
	
	public Location(int x, int y)
	{
		this(x, y, false);
	}
	
	public Location(int x, int y, boolean barren)
	{
		this.x = x;
		this.y = y;
		this.barren = barren;
		this.visited = false;
	}

	/**
	 * x coordinate
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * y coordinate
	 * @return
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * has location been visited during graph traversal
	 * @return
	 */
	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	/**
	 * true if location is barren, false if fertile
	 * @return
	 */
	public boolean isBarren() {
		return barren;
	}
	
	public void setBarren(boolean barren) {
		this.barren = barren;
	}
	
	@Override
	public String toString()
	{
		return "(" + x + "," + y + ")";
	}
	
	
}
