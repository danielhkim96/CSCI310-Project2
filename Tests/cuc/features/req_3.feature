Feature: Pagination

Scenario: User can go to different pages of results
  Given I search for "6" results for "chicken"
  Then I should see 5 results
  And I should see "Melt In Your Mouth (MIYM) Chicken Breasts"
  And I should see "Last Minute Chicken Recipe"
  And I should see "Honey Butter Chicken"
  When I click "2"
  Then I should see "Easy Italian Baked Chicken"

Scenario: Different results are displayed on different pages
  Given I search for "6" results for "potato"
  Then I should see 5 results
  And I should see "Crispy Garlic Roasted Potatoes"
  And I should see "Easy Oven Roasted Potatoes"
  And I should see "Garlic Roasted Potatoes"
  And I should see "Oven Roasted Potatoes"
  When I click "2"
  Then I should see 1 results
  Then I should see "Easily The Best Garlic Herb Roasted Potatoes Recipe by Tasty"

Scenario: A maximum of 5 results are shown
  Given I search for "6" results for "tacos"
  Then I should see 5 results
  And I should see "Best Ground Beef Taco Meat"
  And I should see "Ground Beef Tacos"
  And I should see "Just Like Taco Bell Tacos"
  And I should see "Baked Beef Tacos"
  When I click "2"
  Then I should see 1 results
  And I should see "Beef Tacos"

Scenario: There is no pagination for less than 5 results
  Given I search for "2" results for "salad"
  Then I should see 2 results
  And I should see "Everyday Salad"
  And I should see "That Good Salad"
  When I click "Next"
  Then I should see 2 results
  And I should see "Everyday Salad"
  And I should see "That Good Salad"

Scenario: Returning to an older page shows the same results it showed earlier
  Given I search for "6" results for "sushi"
  Then I should see 5 results
  And I should see "Homemade Sushi: Tips, Tricks, and Toppings!"
  And I should see "Sushi Rolls"
  And I should see "California Sushi Rolls"
  And I should see "Dragon Roll"
  When I click "2"
  And I click "1"
  Then I should see 5 results
  And I should see "Homemade Sushi: Tips, Tricks, and Toppings!"
  And I should see "Sushi Rolls"
  And I should see "California Sushi Rolls"
  And I should see "Dragon Roll"