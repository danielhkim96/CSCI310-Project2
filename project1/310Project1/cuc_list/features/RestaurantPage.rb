Given("I am on Restaurant page") do
  expect(page).to have_current_path('/project1/restaurant.html?id=TDCDiaAsehZ0fuB65_yVQw')
end

Then("I see the restaurant's Title") do
  expect(page).to have_css('#name')
end

Then("I see restaurant's name") do
  expect(page).to have_css('#name')
end

Then("I see the restaurant's phone number") do
  expect(page).to have_css('#phone')
end

Then("I see the restaurant's website") do
  expect(page).to have_css('#website')
end

Then("I will be on Google Map with direction filled out") do

    expect(page).to have_current_path('/maps/dir/?api=1&origin=Tommy+Trojan+Los+Angeles+CA&destination=3584+S+Figueroa+St%2C+Los+Angeles%2C+CA+90007&travelmode=driving')
end

Then("I will be on Yelp page") do
  expect(page).to have_current_path('/biz/pizza-studio-los-angeles-5?adjust_creative=fmArEYGl2rOxigkaNOHddQ&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=fmArEYGl2rOxigkaNOHddQ')
end