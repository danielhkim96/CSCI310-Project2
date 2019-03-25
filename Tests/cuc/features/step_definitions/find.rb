Given(/^I am on the Google homepage$/) do
 visit 'http://www.google.co.uk'
end
Then(/^I will search for "(.*?)"$/) do |searchText|
 fill_in 'q', :with => searchText
end
When(/^press search$/) do
 find_field("q").native.send_key(:enter)
end
Then(/^I should see "([^"]*)"$/) do |expectedText|
 expect(page).to have_title expectedText + " - Google Search"
end
