Feature: Searching Security

Scenario: SQL Injection
	Given I am on the search page
	When I type "Chicken; DROP DATABASE" into the search bar
	And I type "2" into the number bar
	And I type "0" into the radius bar
	And click search
	Then I should be on the "/ImHungry/IHSearch?search_query=Chicken%3B+DROP+DATABASE&num_results=2&radius=0&username=" page
	
Scenario: SQL Injection type 2
	Given I am on the search page
	When I type "Chicken; SELECT username FROM User Where 1=1" into the search bar
	And I type "2" into the number bar
	And I type "0" into the radius bar
	And click search
	Then I should be on the "/ImHungry/IHSearch?search_query=Chicken%3B+SELECT+username+FROM+User+Where+1%3D1&num_results=2&radius=0&username=" page
	
Scenario: Logging In SQL Injection
	Given I have an account with username "User@usc.edu" and password "123456789"
	When I type "User; SELECT username FROM User Where 1=1" in the Username bar
	And I type "Pass; SELECT username FROM User Where 1=1" in the Password bar
	And I log-in
	Then I should NOT see content "User@usc.edu"