package com.becicka.barrenlandanalysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main for Barren Land Analysis program
 * @author Todd
 *
 */
public class BarrenLandAnalysis {

	public static void main(String[] args) {
		System.out.println("Enter the set of rectangles: ");
		String input = null;
	    try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in)))
	    {
	    	//specs say no additional white space so only need to read a single line
	    	input = in.readLine();
	    } catch (IOException e) {
	    	System.out.println("Error reading input: " + e);
	    	return;
		}	  
		Farm farm = Farm.parse(input);
		List<Integer> areas = farm.calculateFertileLandAreas();
		System.out.println(areas.stream().map(Object::toString).collect(Collectors.joining(" ")));

	}
	


}
