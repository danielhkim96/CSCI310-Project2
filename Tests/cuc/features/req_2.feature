Feature: Log-In Functionality

Background:
	Given I am on the login page

Scenario: Creating an Account
	When I click "Create an account"
	And I type "Hello@usc.edu" in the Username bar
	And I type "World" in the Password bar
	And I create an account
	Then I should be logged in with account "Hello@usc.edu"
	
Scenario: Successful Logging In Valid Account
	Given I have an account with username "User@usc.edu" and password "123456789"
	When I type "User@usc.edu" in the Username bar
	And I type "123456789" in the Password bar
	And I log-in
	Then I should be logged in with account "User@usc.edu"

Scenario: Unsuccessful Loggin in Valid Account
	Given I have an account with username "User@usc.edu" and password "123456789"
	When I type "User@usc.edu" in the Username bar
	And I type "Invalid" in the Password bar
	And I log-in
	Then I should NOT be logged in with account "User@usc.edu"
	
Scenario: Logging In Invalid Account
	When I type "User@usc.edu" in the Username bar
	And I type "Password" in the Password bar
	And I log-in
	Then I should NOT be logged in with account "User@usc.edu"
	
Scenario: Log-In preserving Grocery List data between sessions
	Given I have an account with username "User@usc.edu" and password "123456789"
	Given I search for "2" results for "taco"
	When I click "Best Ground Beef Taco Meat"
	And I add to Grocery List
	And I click "Back to Results"
	And I click "Back to Search"
	Given I search for "1" results for "potato"
	When I navigate to the Grocery page
	Then I should see "12 taco shells"
	And I should see "2 cups shredded Mexican cheese blend, divided"
	
Scenario: Log-In Grocery List data is not shared with different sessions
	Given I have an account with username "User@usc.edu" and password "123456789"
	Given I have an account with username "UserTwo@usc.edu" and password "987654321"
	Given I search for "2" results for "taco"
	When I click "Best Ground Beef Taco Meat"
	And I add to Grocery List
	Given I am on the login page
	And I type "UserTwo@usc.edu" in the Username bar
	And I type "987654321" in the Password bar
	And I log-in
	Given I search for "1" results for "taco"
	When I navigate to the Grocery page
	Then I should NOT see content "11 taco shells"