Given(/^I am on localhost$/) do
  visit 'http://food.hiddetek.com'
end

Then(/^I will see ImHungry$/) do
  expect(page).to have_title 'ImHungry'
end

Then (/^background should be gray$/) do
  it {should have_css('div.grey') }
end


###########

Then(/^I will see "(.*?)"$/) do |expectedText|
  expect(page).to have_title => expectedText
end

Then(/^I will search for "$/) do |searchText|
  fill_in 'q', :with => searchText
end

When(/^press search$/) do
  find_field("q").native.send_key(:enter)
end

Then(/^I will click the about link$/) do
  click_link('About')
end
