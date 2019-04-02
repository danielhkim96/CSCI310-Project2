Feature: Radius Search

Background:
	Given I am on the search page

Scenario: Search with Radius set to Zero
	When I type "chicken" into the search bar
	And I type "2" into the number bar
	And I type "0" into the radius bar
	And click search
	Then I should be on the "/ImHungry/IHSearch?search_query=chicken&num_results=2&radius=0" page

Scenario: Search with Negative Radius
	When I type "tacos" into the search bar
	And I type "3" into the number bar
	And I type "-5" into the radius bar
	And click search
	Then I should be on the "/ImHungry/IHSearch?search_query=tacos&num_results=3&radius=-5" page

Scenario: Search with No Radius
	When I type "tacos" into the search bar
	And I type "3" into the number bar
	And click search
	Then I should be on the "/ImHungry/IHSearch?search_query=tacos&num_results=3&radius=" page

Scenario: Results page only displays restaurants within the given radius
	When I type "tacos" into the search bar
	And I type "4" into the number bar
	And I type "1" into the radius bar
	And click search
	Then I should be on the "/ImHungry/IHSearch?search_query=tacos&num_results=4&radius=1" page
	And I should see less than 4 results
