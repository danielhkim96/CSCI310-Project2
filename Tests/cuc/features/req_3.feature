Feature: Pagination

Scenario: User can go to different pages of results
  Given I search for "6" results for "chicken"
  Then I should see "5" results
  And I should see "Melt In Your Mouth (MIYM) Chicken Breasts"
  And I should see "Baked Lemon Chicken"
  And I should see "Last Minute Chicken Recipe"
  And I should see "Honey Butter Chicken"
  And I should see "Quick Chicken Piccata"
  When I click "2"
  Then I should see "Creamy Garlic Chicken"

Scenario: Different results are displayed on different pages
  Given I search for "6" results for "potato"
  Then I should see "5" results
  When I click "next page"
  Then I should see "1" results
  Then I should see "Recipe Name"

Scenario: A maximum of 5 results are shown
  Given I search for "6" results for "tacos"
  Then I should see "5" results
  When I click "next page"
  Then I should see "1" results
  Then I should see "Recipe Name"

Scenario: There is no pagination for less than 5 results
  Given I search for "2" results for "salad"
  Then I should see "2" results
  Then I should see "Recipe Name"
  When I click "next page"
  Then I should see "2" results
  Then I should see "Recipe Name"

Scenario: Returning to an older page shows the same results it showed earlier
  Given I search for "6" results for "sushi"
  Then I should see "Recipe first page"
  When I click "next page"
  And I click "previous page"
  Then I should see "Recipe first page"