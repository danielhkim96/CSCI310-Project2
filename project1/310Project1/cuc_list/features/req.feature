Feature: Non-Func 1: Available via the localhost interface of a web server

Scenario: Search for the website
 Given I am on localhost
 Then I will see ImHungry

# List Management Page: Requirements for All Pages F1, F2
Scenario: Display Favorites

  Given I am on the results page
  Then I select predefined list item Favorites
  Then I will see the list Favorites
  Then the items for favorites are listed

  # All Pages F1
  Then background should be gray

  # All Pages F2a
  Then standard text size should be the browser default text size

  # All Pages F2b
  Then Titles of pages and columns of results must be clearly larger than standard text

# List Management Page: all Pages
# F1: All items (recipes and restaurants) that have been added to the list L are to be displayed
Scenario: Display "Favorites" List

  Given I am on the results page
  Then I select predefined list item Favorites
  Then I will see the list Favorites
  Then all items that have been added to the list Favorites are to be displayed

# F1: All items (recipes and restaurants) that have been added to the list "To Explore" are to be displayed
Scenario: Display "To Explore" List

  Given I am on the results page
  Then I select predefined list item To Explore
  Then I will see the list To Explore
  Then all items that have been added to the list To Explore are to be displayed

# F1: All items (recipes and restaurants) that have been added to the list "Do Not Show" are to be displayed
Scenario: Display "Do Not Show" List

  Given I am on the results page
  Then I select predefined list item Do Not Show
  Then I will see the list Do Not Show
  Then all items that have been added to the list Do Not Show are to be displayed

# F2: Clicking on an item takes the user to the corresponding restaurant or recipe page
Scenario: Click on a restaurant

  Given I am on the List Management page
  Then clicking on an item takes the user to the corresponding restaurant or recipe page

# F3: An item may be removed from the list or moved to another list
Scenario: Click on a recipe

  Given I am on the List Management page
  Then an item may be removed from the list or moved to another list


# F4: A button labeled “Results Page” that takes the user back to the Results Page generated for F
Scenario: Click on button "Results Page"

  Given I am on the List Management page
  Then I am in results
  Then the title is F
  Then results are for F

# F5: a button labeled “Return to Search Page” that transitions the user back to the Search Page
Scenario: Click on button "Return to Search"

  Given I am on the List Management page
  Then I click on "Return to Search" button
  Then I am on Search page

# F6: a drop down box that allows the user to select one of the predefined lists. No list will be selected by default.
Scenario: Drop down list

  Given I am on the List Management page
  Then list is not selected by default
  Then I click on the list
  Then I see "Favorites", "To Explore", "Do Not Show"

# F7: A button labeled “Manage List” that when clicked transitions the user to the List Management page for the list selected in the drop down box.
Scenario: Drop down list Favorites

  Given I am on the List Management page
  Then I select Favorites
  Then I see Favorites list

Scenario: Drop down list "To Explore"

  Given I am on the List Management page
  Then I select "To Explore"
  Then I see "To Explore" list

Scenario: Drop down list "Do Not Show"

  Given I am on the List Management page
  Then I select "Do Not Show"
  Then I see "Do Not Show" list
