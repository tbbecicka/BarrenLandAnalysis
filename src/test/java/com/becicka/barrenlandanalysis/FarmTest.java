package com.becicka.barrenlandanalysis;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Unit tests for Farm class
 * @author Todd
 *
 */
public class FarmTest {

	/**
	 * Entire farm is fertile
	 */
	@Test
	public void testAllFertile() {
		Farm f = Farm.parse("{}");
		List<Integer> areas = f.calculateFertileLandAreas();
		assertEquals(1, areas.size());
		assertEquals(Farm.getMaxWidth() * Farm.getMaxHeight(), areas.get(0).intValue());
	}
	
	/**
	 * The entire farm is barren
	 */
	@Test
	public void testAllBarren() {
		Farm f = Farm.parse("{\"0 0 399 599\"}");
		List<Integer> areas = f.calculateFertileLandAreas();
		assertEquals(1, areas.size());
		assertEquals(0, areas.get(0).intValue());
	}
	
	/**
	 * Entire farm is Barren from 2 rectangles
	 */
	@Test
	public void testAllBarrenTwoRectangles() {
		Farm f = Farm.parse("{\"0 0 399 300\", \"0 301 399 599\"}");
		List<Integer> areas = f.calculateFertileLandAreas();
		assertEquals(1, areas.size());
		assertEquals(0, areas.get(0).intValue());
	}
	
	/**
	 * Barren area splits farm into 2 equal fertile areas
	 */
	@Test
	public void testTwoEqualAreas() {
		Farm f = Farm.parse("{\"0 292 399 307\"}");
		List<Integer> areas = f.calculateFertileLandAreas();
		assertEquals(2, areas.size());
		assertEquals(116800, areas.get(0).intValue());
		assertEquals(116800, areas.get(1).intValue());
	}
	
	/**
	 * Barren area is line along bottom of farm
	 */
	@Test
	public void testTopLine() {
		Farm f = Farm.parse("{\"0 0 399 0\"}");
		List<Integer> areas = f.calculateFertileLandAreas();
		assertEquals(1, areas.size());
		assertEquals(Farm.getMaxWidth() * Farm.getMaxHeight() - Farm.getMaxWidth(), areas.get(0).intValue());
	}
	
	/**
	 * Barren area splits farm into 4 equal fertile areas, + sign
	 */
	@Test
	public void testFourEqualAreas() {
		Farm f = Farm.parse("{\"0 292 399 307\", \"192 0 207 599\"}");
		List<Integer> areas = f.calculateFertileLandAreas();
		assertEquals(4, areas.size());
		assertEquals(56064, areas.get(0).intValue());
		assertEquals(56064, areas.get(1).intValue());
		assertEquals(56064, areas.get(2).intValue());
		assertEquals(56064, areas.get(3).intValue());
	}
	
	/**
	 * Hash tag shaped barren area
	 */
	@Test
	public void testHashTag() {
		Farm f = Farm.parse("{\"48 192 351 207\", \"48 392 351 407\", \"120 52 135 547\", \"260 52 275 547\"}");
		List<Integer> areas = f.calculateFertileLandAreas();
		assertEquals(2, areas.size());
		assertEquals(22816, areas.get(0).intValue());
		assertEquals(192608, areas.get(1).intValue());
	}
	
	@Test
	/**
	 * Test square meter checkered pattern
	 * |
	 * | * * * *
	 * |* * * *
	 */
	public void testSingleMeterFertileAreas() {
		Farm f = Farm.parse("{\"0 0 0 0\", \"2 0 2 0\", \"4 0 4 0\", \"6 0 6 0\", \"1 1 1 1\", \"3 1 3 1\", \"5 1 5 1\", \"7 1 7 1\"}");
		List<Integer> areas = f.calculateFertileLandAreas();
		assertEquals(4, areas.size());
		assertEquals(1, areas.get(0).intValue());
		assertEquals(1, areas.get(1).intValue());
		assertEquals(1, areas.get(2).intValue());
		assertEquals(Farm.getMaxWidth() * Farm.getMaxHeight() - 11, areas.get(3).intValue());
		//verify same result on consecutive calls
		areas = f.calculateFertileLandAreas();
		assertEquals(4, areas.size());
		assertEquals(1, areas.get(0).intValue());
		assertEquals(1, areas.get(1).intValue());
		assertEquals(1, areas.get(2).intValue());
		assertEquals(Farm.getMaxWidth() * Farm.getMaxHeight() - 11, areas.get(3).intValue());
	}
	
	/**
	 * Test barren area that is horizontal line that is entire width - 1
	 */
	@Test
	public void testBarrenLine() {
		Farm f = Farm.parse("{\"0 292 398 292\"}");
		List<Integer> areas = f.calculateFertileLandAreas();
		assertEquals(1, areas.size());
		assertEquals(Farm.getMaxWidth() * Farm.getMaxHeight() - (Farm.getMaxWidth() - 1), areas.get(0).intValue());
	}
	
	/**
	 * Test barren area that is a single square meter
	 */
	@Test
	public void testBarrenSquareMeter() {
		Farm f = Farm.parse("{\"3 4 3 4\"}");
		List<Integer> areas = f.calculateFertileLandAreas();
		assertEquals(1, areas.size());
		assertEquals(Farm.getMaxWidth() * Farm.getMaxHeight() - 1, areas.get(0).intValue());
	}
	
	/**
	 * Test barren top right single square meter
	 */
	@Test
	public void testTopRightSquareMeter() {
		Farm f = Farm.parse("{\"399 599 399 599\"}");
		List<Integer> areas = f.calculateFertileLandAreas();
		assertEquals(1, areas.size());
		assertEquals(Farm.getMaxWidth() * Farm.getMaxHeight() - 1, areas.get(0).intValue());
	}
	
	@Test
	public void testGetMax() {
		assertEquals(600, Farm.getMaxHeight());
		assertEquals(400, Farm.getMaxWidth());
	}
	
	/**
	 * Test that locations are correctly initialized as barren
	 */
	@Test
	public void testGetLocation() {
		Rectangle r = new Rectangle(new Location(2, 2), new Location(3,3));
		List<Rectangle> rects = new ArrayList<Rectangle>();
		rects.add(r);
		Farm f = new Farm(rects);
		assertTrue(f.getLocation(2, 2).isBarren());
		assertTrue(f.getLocation(2, 3).isBarren());
		assertTrue(f.getLocation(3, 2).isBarren());
		assertTrue(f.getLocation(3, 3).isBarren());
		assertFalse(f.getLocation(1, 2).isBarren());
		assertFalse(f.getLocation(2, 4).isBarren());
		assertFalse(f.getLocation(4, 2).isBarren());
		assertFalse(f.getLocation(3, 4).isBarren());
		assertEquals(400, Farm.getMaxWidth());
	}
	
	/**
	 * Test rectangle that has bottomLeft and topRight in wrong order
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidRectangle() {
		Rectangle r = new Rectangle(new Location(100, 100), new Location(50,50));
		List<Rectangle> rects = new ArrayList<Rectangle>();
		rects.add(r);
		new Farm(rects);
	}
	
	/**
	 * Test rectangle that has X coordinate outside the farm
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRectangleXTooLarge() {
		Rectangle r = new Rectangle(new Location(100, 100), new Location(400,599));
		List<Rectangle> rects = new ArrayList<Rectangle>();
		rects.add(r);
		new Farm(rects);
	}
	
	/**
	 * Test rectangle that has Y coordinate outside the farm
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRectangleYTooLarge() {
		Rectangle r = new Rectangle(new Location(100, 100), new Location(399,600));
		List<Rectangle> rects = new ArrayList<Rectangle>();
		rects.add(r);
		new Farm(rects);
	}

}
