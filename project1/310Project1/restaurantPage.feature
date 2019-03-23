Feature: Restaurant Page Features 

Scenario: Check Restaurant Name
Given I am on Results Page 
When I click on a Restaurant 
Then I should be redirected to the Restaurant Page 
And the name of the restaurant should match the name of the restaurant clicked

Scenario: Check Restaurant Address
Given I am on Results Page 
When I click on a Restaurant 
Then I should be redirected to the Restaurant Page
And the address of the restaurant should match the address of the restaurant clicked

Scenario: Check Restaurant Phone Number
Given I am on Results Page 
When I click on a Restaurant 
Then I should be redirected to the Restaurant Page 
And I should see the phone number of the restaurant under "Phone"

Scenario: Check Restaurant Website
Given I am on Results Page 
When I click on a Restaurant 
Then I should be redirected to the Restaurant Page 
And I should see the website of the restaurant (if it exists) under "Website"

Scenario: Check for Restaurant printable version button
Given I am on Results Page 
When I click on a Restaurant 
Then I should be redirected to the Restaurant Page 
And I should see a "Printable Version" button on the top right

Scenario: Check Back to Results Button
Given I am on Results Page 
When I click on a Restaurant 
Then I should be redirected to the Restaurant Page 
And I should see "Back to Results" button on the right

Scenario: Check Restaurant Name
Given I am on Results Page 
When I click on a Restaurant 
Then I should be redirected to the Restaurant Page 
And I should see a dropdown box to select one of the predefined lists

Scenario: Check Add to List Button
Given I am on Results Page 
When I click on a Restaurant 
Then I should be redirected to the Restaurant Page 
And I should see "Add to List" button on the right