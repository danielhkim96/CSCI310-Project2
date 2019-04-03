Feature: Grocery List Requirement

Scenario: Adding a recipe to the Grocery List
  Given I search for "3" results for "tacos"
  When I click "Best Ground Beef Taco Meat"
  And I add to Grocery List
  And I click "Back to Results"
  And I navigate to the Grocery page
  Then I should see "1 tablespoon Chili Powder"
  And I should see "1/2 teaspoon Salt"
  And I should see "3/4 teaspoon Cumin"
  And I should see "1/4 teaspoon Garlic Powder"

Scenario: Adding the same recipe twice
  Given I search for "3" results for "tacos"
  When I click "Best Ground Beef Taco Meat"
  And I add to Grocery List
  And I add to Grocery List
  And I click "Back to Results"
  And I navigate to the Grocery page
  Then I should see "1 tablespoon Chili Powder"
  And I should see "1/2 teaspoon Salt"
  And I should see "3/4 teaspoon Cumin"
  And I should see "1/4 teaspoon Garlic Powder"
