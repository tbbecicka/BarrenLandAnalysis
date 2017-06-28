package com.becicka.barrenlandanalysis;

/**
 * Represents a rectangular plot of land
 * @author Todd
 *
 */
public class Rectangle {
	private final Location bottomLeft;
	private final Location topRight;
	
	/**
	 * Constructor
	 * @param botLeft bottom left point of rectangle
	 * @param topRight top right point of rectangle
	 */
	public Rectangle(Location botLeft, Location topRight)
	{
		if (botLeft.getX() < 0 || botLeft.getY() < 0 || topRight.getX() < 0|| topRight.getY() < 0)
		{
			throw new IllegalArgumentException("Coordinates cannot be negative.");
		}
		if (botLeft.getX() > topRight.getX() || botLeft.getY() > topRight.getY())
		{
			throw new IllegalArgumentException("First point must be below and to the left and second point");
		}
		this.bottomLeft = botLeft;
		this.topRight = topRight;
	}
	
	/**
	 * 
	 * @param inputString Four integers separated by single spaces, 
	 * with no additional spaces in the string. The first two integers are the coordinates of the bottom left 
	 * corner in the given rectangle, and the last two integers are the coordinates of the top right corner.
	 * @return
	 */
	public static Rectangle parse(String inputString)
	{
		String input = inputString.replace("\"", "").trim();
		String[] coords = input.split(" ");
		if (coords.length != 4)
		{
			throw new IllegalArgumentException("Invalid String format: String must contain exactly 4 integers separated by single spaces.");
		}
		return new Rectangle(new Location(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])), new Location(Integer.parseInt(coords[2]), Integer.parseInt(coords[3])));	
	}	

	/**
	 * Get bottom left location
	 * @return
	 */
	public Location getBottomLeft() {
		return bottomLeft;
	}

	/**
	 * Get top right location
	 * @return
	 */
	public Location getTopRight() {
		return topRight;
	}
	
	@Override
	public String toString()
	{
		return "Bottom Left: " + bottomLeft.toString() + "  Top Right: " + topRight.toString();
	}
	
	
}
