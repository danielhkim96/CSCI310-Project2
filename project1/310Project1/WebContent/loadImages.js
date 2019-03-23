function loadClient() {
    gapi.client.setApiKey("AIzaSyD3fAsRudvFdzPwWvMsK0ecbH0djMTKFKM");
    return gapi.client.load("https://content.googleapis.com/discovery/v1/apis/customsearch/v1/rest")
	.then(function() { console.log("GAPI client loaded for API"); },
	      function(err) { console.error("Error loading GAPI client for API", err); });
  }
  // Make sure the client is loaded before calling this method.
  function execute(str, callback) {
    return gapi.client.search.cse.list({
	"q": str,
	"cx": "016840863471797630171:epcxh8r9u00",
	"gl": "us",
	"imgType": "photo",
	"num": 10,
	"searchType": "image"
    })
    .then(function(response) {
        var images = [];
        response.result.items.forEach(function(element) {
            var image = {};
            image.url = element.link;
            image.pos_x = Math.floor(Math.random() * 100);
            image.pos_y = Math.floor(Math.random() * 100);
            var degrees = Math.random() * Math.PI/4;
            degrees *= Math.floor(Math.random() * 2) == 1 ? 1 : -1;
            image.degrees = degrees;
            images.push(image);
        });
        JSON.stringify(images);
        loadImages(images);
      },
      function(err) { console.error("Execute error", err); });
  }
  gapi.load("client");