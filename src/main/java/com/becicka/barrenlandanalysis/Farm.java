package com.becicka.barrenlandanalysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents all of the barren and fertile land in the farm
 * @author Todd
 *
 */
public class Farm {
	
	private final static int MAX_WIDTH = 400;
	private final static int MAX_HEIGHT = 600;

	private List<Rectangle> barrenRectangles;
	private List<ArrayList<Location>> locations = new ArrayList<ArrayList<Location>>(MAX_WIDTH);
	private Queue<Location> queue = new LinkedList<Location>();
	
	//has graph already been traversed and needs to be cleared
	private boolean resetGraph = false;
	
	/**
	 * Constructor
	 * @param barrenRectangles list of Rectangles of barren land
	 */
	public Farm(List<Rectangle> barrenRectangles)
	{
		this.barrenRectangles = barrenRectangles;
		if (barrenRectangles.stream().anyMatch(rect -> rect.getTopRight().getX() >= MAX_WIDTH || rect.getTopRight().getY() >= MAX_HEIGHT))
		{
			throw new IllegalArgumentException("Rectangle coordinates must be completely within the farm (0,0) -> (" + (MAX_WIDTH - 1) + ", " + (MAX_HEIGHT - 1) + ").");
		}
		IntStream.range(0, MAX_WIDTH).forEach(x -> initalizeFertileLocations(x));
		barrenRectangles.stream().forEach(rect -> initializeBarrenRect(rect));
	}
	
	/**
	 * Creates Farm with barren rectangles specified in the string in the requirements specification:
	 * These rectangles are defined in a string, which consists of four integers separated by single spaces, 
	 * with no additional spaces in the string. The first two integers are the coordinates of the bottom left 
	 * corner in the given rectangle, and the last two integers are the coordinates of the top right corner.
	 * Ex. {"0 292 399 307"} or {"48 192 351 207", "48 392 351 407", "120 52 135 547", "260 52 275 547"} 
	 * @param rectCoordinates
	 * @return
	 */
	public static Farm parse(String rectCoordinates)
	{
		String inputString = rectCoordinates.replace("{", "").replace("}", "").replace("\"", "");
		List<Rectangle> rectangles = new ArrayList<Rectangle>();
		if (!inputString.isEmpty())
		{
			String[] rectangleCoords = inputString.split(",");
			rectangles = Arrays.stream(rectangleCoords).map(coords -> Rectangle.parse(coords)).collect(Collectors.toList());
		}
		return new Farm(rectangles);
	}
	
	/**
	 * Initialize rectangle locations to be barren
	 * @param rect the barren rectangle
	 */
	private void initializeBarrenRect(Rectangle rect) {
		IntStream.rangeClosed(rect.getBottomLeft().getX(), rect.getTopRight().getX()).forEach(x -> {
			IntStream.rangeClosed(rect.getBottomLeft().getY(), rect.getTopRight().getY()).forEach(y -> locations.get(x).get(y).setBarren(true));
		});
	}
	
	/**
	 * Returns the areas of the fertile land sections in ascending order
	 * @return
	 */
	public List<Integer> calculateFertileLandAreas()
	{		
		if (resetGraph)
		{
			resetGraph();
		}
		List<Integer> result = new ArrayList<Integer>();
		//iterate over every location to ensure all are visited in case fertile land is completely surrounded by barren land
		IntStream.range(0, MAX_WIDTH).forEach(x -> {
			IntStream.range(0, MAX_HEIGHT).forEach(y -> {
				Location loc = getLocation(x, y);
				if (!loc.isVisited() && !loc.isBarren())
				{
					int area = calculateFertileLand(loc);
					result.add(area);
				}
			});
		});
		//handle all barren case
		if (result.isEmpty())
		{
			result.add(0);
		}
		resetGraph = true;
		return result.stream().sorted().collect(Collectors.toList());
	}
	
	/**
	 * Sets all Locations to not visited.
	 */
	private void resetGraph()
	{
		IntStream.range(0, MAX_WIDTH).forEach(x -> {
			IntStream.range(0, MAX_HEIGHT).forEach(y -> {
				Location loc = getLocation(x, y);
				loc.setVisited(false);
			});
		});
	}
	
	/**
	 * Traverses the farm graph, finding all locations that can be reached without crossing barren land
	 * @param loc starting location for traversal
	 * @return
	 */
	private int calculateFertileLand(Location loc)
	{
		int area = 0;
		loc.setVisited(true);
		queue.add(loc);
		while(!queue.isEmpty())
		{
			Location curLocation = queue.remove();
			area++;
			//add fertile locations that are next to current location to queue
			if (curLocation.getX() + 1 < MAX_WIDTH)
			{
				Location nextXLoc = getLocation(curLocation.getX() + 1, curLocation.getY()); 
				addLocationToQueue(nextXLoc);
			}
			if (curLocation.getY() + 1 < MAX_HEIGHT)
			{
				Location nextYLoc = getLocation(curLocation.getX(), curLocation.getY() + 1); 
				addLocationToQueue(nextYLoc);
			}
			if (curLocation.getX() - 1 >= 0)
			{
				Location nextXLoc = getLocation(curLocation.getX() - 1, curLocation.getY()); 
				addLocationToQueue(nextXLoc);
			}
			if (curLocation.getY() - 1 >= 0)
			{
				Location nextYLoc = getLocation(curLocation.getX(), curLocation.getY() - 1); 
				addLocationToQueue(nextYLoc);
			}
			
		}
		return area;
	}
	
	/**
	 * Adds location to queue during traversal if hasn't been visited and is fertile
	 * @param loc
	 */
	private void addLocationToQueue(Location loc)
	{
		if (!loc.isVisited() && !loc.isBarren())
		{
			loc.setVisited(true);
			queue.add(loc);
		}
	}

	/**
	 * Sets every location in the graph to be fertile
	 * @param x
	 */
	private void initalizeFertileLocations(int x)
	{
		ArrayList<Location> column = new ArrayList<Location>(MAX_HEIGHT);
		IntStream.range(0, MAX_HEIGHT).forEach(y -> {
			column.add(y, new Location(x, y, false));
		});
		locations.add(x, column);
	}
	
	/**
	 * Get the location at the specified coordinates
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return
	 */
	public Location getLocation(int x, int y)
	{
		if (x >= MAX_WIDTH || y >= MAX_HEIGHT)
		{
			throw new IllegalArgumentException("Coordinate is greater than farm dimensions.");
		}
		return locations.get(x).get(y);
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		barrenRectangles.stream().forEach(rect -> builder.append(rect).append(System.lineSeparator()));
		return builder.toString();

	}

	/**
	 * Get maximum width of the farm (x direction)
	 * @return
	 */
	public static int getMaxWidth() {
		return MAX_WIDTH;
	}

	/**
	 * Get maximum height of the farm (y direction)
	 * @return
	 */
	public static int getMaxHeight() {
		return MAX_HEIGHT;
	}
	
	
}
