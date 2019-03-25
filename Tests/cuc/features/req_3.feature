Scenario: User can go to different pages of results
  Given I search for 30 results for "chicken"
  Then I should see 10 results
  When I click "next page"
  Then I should see "Page 2"

Scenario: Different results are displayed on different pages
  Given I search for 30 results for "potato"
  Then I should see 10 results
  When I click "next page"
  Then I should see 10 results
  And the results should all be different

Scenario: A maximum of 10 results are shown
  Given I search for 30 results for "tacos"
  Then I should see 10 results
  When I click "next page"
  Then I should see 10 results

Scenario: There is no pagination for less than 10 results
  Given I search for 5 results for "salad"
  Then I should see 5 results
  When I click "next page"
  Then I should see 5 results
  And the results should be the same

Scenario: Returning to an older page shows the same results it showed earlier
  Given I search for 30 results for "sushi"
  When I click "next page"
  And I click "previous page"
  Then the results should be the same
