Feature: Restaurant Page

Background:
    Given I am on the Search Page
    And I have entered a search query "pizza"
    And 2 for the number of results to be displayed
    And I clicked the "Feed Me!" button
    And I am on the Results Page
    And I click on a restaurant with name "Pizza Studio"

Scenario: Content
    Given I am on Restaurant page
    Then I see the restaurant's Title
    And I see restaurant's name
    And I see the restaurant's phone number
    And I see the restaurant's website

Scenario: Google Map
    Given I am on Restaurant page
    When I click the "3584 S Figueroa St, Los Angeles, CA 90007" button
    Then I will be on Google Map with direction filled out

Scenario: Website
    Given I am on Restaurant page
    When I click the "https://www.yelp.com/biz/pizza-studio-los-angeles-5?adjust_creative=fmArEYGl2rOxigkaNOHddQ&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=fmArEYGl2rOxigkaNOHddQ" button
    Then I will be on Yelp page