Feature: Log-In Functionality

Background:
	Given I am on the login page

Scenario: Creating an Account
	When I click "Create an Account"
	And I type "Username" in the Username bar
	And I type "Password" in the Password bar
	And I type "Password" in the Confirm bar
	And I click "Create and Account"
	Then I should be on the "Login" page
	When I type "Username" in the Username bar
	And I type "Password" in the Password bar
	And I click "Log In"
	Then I should be on the Search Page

Scenario: Successful Logging In Valid Account
	Given I have an account with username "User" and password "123456789"
	When I type "User" in the Username bar
	And I type "123456789" in the Password bar
	And I click "Log In"
	Then I should be on the Search Page

Scenario: Unsuccessful Loggin in Valid Account
	Given I have an account with username "User" and password "123456789"
	When I type "User" in the Username bar
	And I type "Invalid" in the Password bar
	And I click "Log In"
	Then I should be on the Login Page
	
Scenario: Logging In Invalid Account
	When I type "User" in the Username bar
	And I type "Password" in the Password bar
	And I click "Log In"
	Then I should be on the Login Page

Scenario: Logging In SQL Injection
	When I type "User; SELECT username FROM User Where 1=1" in the Username bar
	And I type "Pass; SELECT username FROM User Where 1=1" in the Password bar
	And I click "Log In"
	Then I should be on the Login Page