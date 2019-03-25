@Test
public void testSearch() {
	
	List<Result> testResult = new ArrayList<>();
	customSearch.setRadius(5);
	testResult = customSearch.search("Wendy's");
	
	// manually find 10 restaurants within the radius and match the search result
	assertEquals("{\"displayLink\":\"blahblah\",\"htmlSnippet\":\"<b>Basketball</b> - Wikipedia\",\"htmlTitle\":\"<b>Basketball</b> - Wikipedia\",\"image\":{\"byteSize\":16947097,\"contextLink\":\"https://en.wikipedia.org/wiki/Basketball\",\"height\":3983,\"thumbnailHeight\":112,\"thumbnailLink\":\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSFb5r1FgwapyoJT1klmrrP143LrQMZsMTrF44xA5MeVIO6QuEfk67pIfey\",\"thumbnailWidth\":150,\"width\":5356},\"kind\":\"customsearch#result\",\"link\":\"https://upload.wikimedia.org/wikipedia/commons/3/3b/LeBron_James_Layup_%28Cleveland_vs_Brooklyn_2018%29.jpg\",\"mime\":\"image/jpeg\",\"snippet\":\"Basketball - Wikipedia\",\"title\":\"Basketball - Wikipedia\"}", testResult.get(0).toString());
	
}

// check that setRadius returns false when negative number is inputted 
@Test
public void testPositive() {
	
	Bool res = customSearch.setRadius(-1);
	
	assertEquals(0, res);
	
}

@Test
public void testAddGrocery() {
	
	List<String> testResult = new ArrayList<>();
	
	testResult = groceryList.addGrocery("Milk");
	
	assertEquals("Milk", testResult.get(0));
	
}


