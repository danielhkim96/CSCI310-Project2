package edu.usc.cs.group8.ImHungry;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class IHSearchTest_Yvette {
	
	@Test
	public void getRestaurantReturnsCorrectRestaurantsWithRadius() {
		IHSearch tester = new IHSearch();
		ArrayList<Restaurant> testRestaurants = new ArrayList<Restaurant>();
	
		testRestaurants = tester.doRestaurantSearch("Chicken", "5", ".2");
		
		System.out.println("Restaurant size: " + testRestaurants.size());
		for(int i = 0; i < testRestaurants.size(); i++) {
			System.out.println(testRestaurants.get(i).getAddress());
		}	
		int x = 2;
		assertEquals("RestaurantURL0", testRestaurants.get(0).getAddress(), "The first result should be ____");
		assertEquals("RestaurantURL1", testRestaurants.get(1).getAddress(), "The second result should be ____");
		assertEquals(x, testRestaurants.size(), "We should only display X restaurants");
		
		testRestaurants = tester.doRestaurantSearch("Taco", "3", "100");
		
		System.out.println("Restaurant size: " + testRestaurants.size());
		for(int i = 0; i < testRestaurants.size(); i++) {
			System.out.println(testRestaurants.get(i).getAddress());
		}
		
		assertEquals("RestaurantURL0", testRestaurants.get(0).getAddress(), "The first result should be ____");
		assertEquals("RestaurantURL1", testRestaurants.get(1).getAddress(), "The second result should be ____");
		assertEquals(3, testRestaurants.size(), "None of the restaurants are limited by radius");		
	}
		
	@Test
	public void testHaversineFunction() {
		double HaversineTest = IHSearch.haversine(1000, 2000, 3000, 4000);
		assertEquals(15619, HaversineTest, 100, "Checking Haversine works with all positive latitude and longitude");
		
		HaversineTest = IHSearch.haversine(-100, -200, -300, -400);
		assertEquals(15619, HaversineTest, 100, "Checking Haversine works with all negative latitude and longitude");

		HaversineTest = IHSearch.haversine(-100, 200, 300, -400);
		assertEquals(15619, HaversineTest, 100, "Checking Haversine works with mixed positive and negative latitude and longitude");
	}
	
}
