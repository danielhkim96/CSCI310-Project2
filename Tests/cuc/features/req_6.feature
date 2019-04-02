Feature: Grocery List Requirement

Scenario: Adding a recipe to the Grocery List
  Given I search for "3" results for "tacos"
  When I click "Best Ground Beef Taco Meat"
  And I add to Grocery List
  And I click "Back to Results"
  And I navigate to the Grocery page
  Then I should see "Salt and pepper"
  And I should see "2.5 tbsp honey"
  And I should see "Finely chopped parsley"
  And I should see "1 tbsp olive oil"

Scenario: Adding the same recipe twice
  Given I search for "3" results for "tacos"
  When I click "Best Ground Beef Taco Meat"
  And I add to Grocery List
  And I add to Grocery List
  And I click "Back to Results"
  And I navigate to the Grocery page
  Then I should see "Salt and pepper"
  And I should see "2.5 tbsp honey"
  And I should see "Finely chopped parsley"
  And I should see "1 tbsp olive oil"
