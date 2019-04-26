Feature: Reordering Elements in List

Scenario: Dragging a single element in a populated list
	Given I search for "3" results for "tacos"
	When I click "Best Ground Beef Taco Meat"
	And I add to Favorites List
	And I click "Back to Results"
	And I click "Ground Beef Tacos"
	And I add to Favorites List
	And I click "Back to Results"
	And I click "Baked Beef Tacos"
	And I add to Favorites List
	And I click "Back to Results"
	And I navigate to the Favorites Page
	And I drag "Best Ground Beef Taco Meat" to "Baked Beef Tacos"
	Then I should see these elements in a different order
	
Scenario: Dragging a single element with no other elements
	Given I search for "2" results for "chicken"
	When I click "Last Minute Chicken Recipe"
	And I add to Favorites List
	And I click "Back to Results"
	And I navigate to the Favorites Page
	And I drag "Last Minute Chicken Recipe" to "Last Minute Chicken Recipe"
	Then I should see no change in order