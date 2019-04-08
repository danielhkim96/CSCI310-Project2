Feature: Changing the order of the list

Scenario: Changing the order of Favorites bar
	Given I search for "2" results for "chicken"
	When I click on the drop-down list
	And I drag the "Favorites" bar down
	Then the "Favorites" bar order should change by 1
	
Scenario: Keeping the order the same
	Given I search for "2" results for "chicken"
	When I click on the drop-down list
	And I hold the "Favorites" bar down
	Then the order of the "Favorites" bar should change by 0