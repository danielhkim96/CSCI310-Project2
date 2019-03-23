# 310Project1
CSCI 310 Group 11 Project 1 Repository

## APIAccess Servlet

### Render results page

#### Request
POST Form submission 
```
{
    "q": "pizza",
    "limit": 5,
}
```

#### Response

Render results.html

### getRestaurants

#### Request
Ajax request
```
{
    type: "getRestaurants"
}
```

#### Response

Restaurant JSON Schema
```
{
    "name": val,
    "address": val,
    "phone": val,
    "id": val,
    "rating": val,
    "price": val,
    "url": val
}

```
YelpAPI.java:
```
Set python3 absolute path
Set yelp.py absolute path
```

Import json-simple-1.1.jar:
```
Right click project -> properties
Go to Java Build Path -> select Libraries tab
Select Classpath -> Add External JARs -> Choose json-simple-1.1.jar
```
    
Google Image Result Format:
```
{
    "result":
        "items":
            {
                "kind": "customsearch#result",
                "title": string,
                "htmlTitle": string,
                "link": string,
                "displayLink": string,
                "snippet": string,
                "htmlSnippet": string,
                "cacheId": string,
                "mime": string,
                "fileFormat": string,
                "formattedUrl": string,
                "htmlFormattedUrl": string,
                "image":
                {
                    "contextLink": string,
                    "height": integer,
                    "width": integer,
                    "byteSize": integer,
                    "thumbnailLink": string,
                    "thumbnailHeight": integer,
                    "thumbnailWidth": integer
                }
            }
}
items[]         list        The current set of custom search results.
items[].link	string	    The full URL to which the search result is pointing, e.g. http://www.example.com/foo/bar.
items[].image	object      Encapsulates all information about an image returned in search results.
```

Recipe Format:
```
{
    "id": "edimame.com/abcd123",
    "title": "Cheese Pizza",
    "imageURL": "https://www.ocregister.com/wp-content/uploads/2018/11/USC-logo-zont-1.jpg?w=560",
    "cookTime": 20,
    "url": "helloURL",
    "ingredients":
    [
        {
            "text": "Cheese"
        },
        {
        	"text": "Dough"
        },
        {
        	"text": "Pizza Sauce"
        }, 
        {
        	"text": "Red Pepper"
        },
        {
        	"text": "Onions"
        }
    ]
}
```
        
## ListManagement Servlet

#### Request
POST Form Submissions

choose one
```
{
	"action": {"add", “delete"}
	"listName": {"Favorites", "ToExplore", "DoNotShow"}
	"id": "id of restaurant or recipe"
}
```

#### Response
GET Form Submissions
```
{
	listName: {"Favorites", "ToExplore", "DoNotShow"}
}
```
Format will be the same as restaurant or recipe
