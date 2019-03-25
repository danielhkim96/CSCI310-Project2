Feature: Find cats results
Scenario: Search for the website
Given I am on the Google homepage
Then I will search for "cats"
And press search
Then I should see "cats"
