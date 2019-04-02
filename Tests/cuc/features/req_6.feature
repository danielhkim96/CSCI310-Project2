Feature: Grocery List Requirement

Scenario: Adding a recipe to the Grocery List
  Given I search for "30" results for "chicken"
  When I select the first recipe
  And I click "Add to Grocery List"
  And I click "Back to Results"
  And I navigate to the Grocery page
  Then I should see the ingredients from the recipe

Scenario: Adding the same recipe twice
  Given I search for "30" results for "chicken"
  When I select the first recipe
  And I click "Add to Grocery List"
  And I click "Add to Grocery List"
  And I click "Back to Results"
  And I navigate to the Grocery page
  Then I should see the ingredients from the recipe
