package com.becicka.barrenlandanalysis;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for Rectangle class
 * @author Todd
 *
 */
public class RectangleTest {

	@Test
	public void testParse() {
		Rectangle r = Rectangle.parse("4 5 8 9");
		assertEquals(4, r.getBottomLeft().getX());
		assertEquals(5, r.getBottomLeft().getY());
		assertEquals(8, r.getTopRight().getX());
		assertEquals(9, r.getTopRight().getY());
	}
	
	@Test
	public void testParseWithQuotes() {
		Rectangle r = Rectangle.parse("\"4 5 8 9\"");
		assertEquals(4, r.getBottomLeft().getX());
		assertEquals(5, r.getBottomLeft().getY());
		assertEquals(8, r.getTopRight().getX());
		assertEquals(9, r.getTopRight().getY());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testParseTooFew() {
		Rectangle.parse("4 5 8");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testParseTooMany() {
		Rectangle.parse("4 5 8 9 11");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNegative() {
		new Rectangle(new Location(-4, 5), new Location(8, 9));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNegative2() {
		new Rectangle(new Location(4, -5), new Location(8, 9));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNegative3() {
		new Rectangle(new Location(4, 5), new Location(-8, 9));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNegative4() {
		new Rectangle(new Location(4, 5), new Location(8, -9));
	}
	
	@Test(expected = NumberFormatException.class)
	public void testParseNotANumber() {
		Rectangle.parse("4 5 8 e");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorPointsIncorrectOrderX() {
		new Rectangle(new Location(4, 5), new Location(3, 9));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorPointsIncorrectOrderY() {
		new Rectangle(new Location(4, 5), new Location(8, 2));
	}

}
