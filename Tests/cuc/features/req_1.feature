Feature: Log-In Security

Scenario: SQL Injection
	Given I am on the search page
	When I type "Chicken; DROP DATABASE" into the search bar
	And I type "2" into the number bar
	And I type "0" into the radius bar
	And click search
	Then I should be on the "/ImHungry/IHSearch?search_query=chicken&num_results=2&radius=0" page
	
Scenario: SQL Injection type 2
	Given I am on the search page
	When I type "Chicken; SELECT username FROM User Where 1=1" into the search bar
	And I type "2" into the number bar
	And I type "0" into the radius bar
	And click search
	Then I should be on the "/ImHungry/IHSearch?search_query=chicken&num_results=2&radius=0" page