package com.becicka.barrenlandanalysis;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for Location class
 * @author Todd
 *
 */
public class LocationTest {

	@Test
	public void testConstructor() {
		Location l = new Location(3, 6);
		assertEquals(3, l.getX());
		assertEquals(6, l.getY());
		assertFalse(l.isBarren());
		assertFalse(l.isVisited());
	}
	
	@Test
	public void testConstructor2() {
		Location l = new Location(3, 6, true);
		assertEquals(3, l.getX());
		assertEquals(6, l.getY());
		assertTrue(l.isBarren());
		assertFalse(l.isVisited());
	}
	
	@Test
	public void testSetVisited() {
		Location l = new Location(3, 6, true);
		assertEquals(3, l.getX());
		assertEquals(6, l.getY());
		assertTrue(l.isBarren());
		assertFalse(l.isVisited());
		l.setVisited(true);
		assertTrue(l.isVisited());
		
	}

}
