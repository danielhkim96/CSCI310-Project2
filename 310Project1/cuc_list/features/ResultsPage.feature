Feature: Results Page

Background:
  Given I am on the Search Page
  And I have entered a search query "pizza"
  And 2 for the number of results to be displayed
  And I clicked the "Feed Me!" button

Scenario: Correct content
    Given I am on the Results Page
    Then the page will have a title: "Pizza"
    And the page will have a collage of photos relevant to the query
    And the page will have a button labeled "Manage Lists"
    And the page will have a button labeled "Return to Search"
    And the page will have a dropdown with predefined lists
    And the page will have two columns of results titled: Restaurants, Recipes
    And the page will have 2 restaurant and recipe results in each column
    And each restaurant item on the page will have an address, name, minutes, rating, and price
    And each recipe item on the page will have a name, prep time, cook time, and price

Scenario: Navigating to a list by using the dropdown
    Given I am on the Results Page
    When I select "Favorites" from the dropdown
    And I click the "Manage List" button
    Then I will be on the "Favorites" list page

Scenario: Navigating to a Restaurant Page by clicking on a restaurant item
    Given I am on the Results Page
    When I click on a restaurant with name "Pizza Studio"
    Then I will be on the Restaurant Page of restaurant "Pizza Studio"

Scenario: Navigating to a Recipe Page by clicking on a recipe item
    Given I am on the Results Page
    When I click on a recipe with name "Pepperoni Pizza"
    Then I will be on the Recipe Page of recipe "Pepperoni Pizza"

Scenario: Trying to navigate to the list management page with nothing selected
    Given I am on the Results Page
    When I click the "Manage List" button
    Then I will be on the Results Page

Scenario: Return back to Search Page
    Given I am on the Results Page
    When I click Return to Search
    Then I should be on the Search Page