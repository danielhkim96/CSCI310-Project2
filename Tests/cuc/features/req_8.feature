Feature: Radius Search

Background:
	Given I am on the search page

Scenario: Search with Radius set to Zero
	When I type "chicken" into the search bar
	And I type "6" into the number bar
	And I type "0" into the radius bar
	And click search
	Then I should be on the "Search" page

Scenario: Search with Negative Radius
	When I type "tacos" into the search bar
	And I type "9" into the number bar
	And I type "-5" into the radius bar
	And click search
	Then I should be on the "Search" page

Scenario: Search with Non-Integer Radius
	When I type "salad" into the search bar
	And I type "9" into the number bar
	And I type "8.321" into the radius bar
	And click search
	Then I should be on the "Search" page

Scenario: Search with No Radius
	When I type "tacos" into the search bar
	And I type "5" into the number bar
	And click search
	Then I should be on the "Results" page

Scenario: Results page only displays restaurants within the given radius
	When I type "tacos" into the search bar
	And I type "5" into the number bar
	And I type "9" into the radius bar
	And click search
	Then I should be on the "Results" page
	And I should NOT see a radius over 9
