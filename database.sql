DROP DATABASE IF EXISTS project2;

CREATE DATABASE project2;
USE project2;

CREATE TABLE User (
	username VARCHAR(100) PRIMARY KEY,
	password VARCHAR(100) NOT NULL
);


CREATE TABLE ListRestaurants (
	listID		int 			PRIMARY KEY AUTO_INCREMENT,
	listName		VARCHAR(100) 	NOT NULL,
	username		VARCHAR(100)	NOT NULL,
	listIndex		VARCHAR(100)	NOT NULL,
	restaurantName		VARCHAR(100)	NOT NULL,
	restaurantAddress	VARCHAR(200)	NOT NULL,
	restaurantPhone		VARCHAR(100)	NOT NULL,
	restaurantURL		VARCHAR(300)	NOT NULL,
	restaurantRating 	VARCHAR(100)	NOT NULL,
	restaurantDriveTime	VARCHAR(100)	NOT NULL,
	FOREIGN KEY (username) REFERENCES User(username)
);

CREATE TABLE ListRecipes (
	listID		int 			PRIMARY KEY AUTO_INCREMENT,
	listName	VARCHAR(100) 	NOT NULL,
	username	VARCHAR(100)	NOT NULL,
	listIndex		VARCHAR(100)	NOT NULL,
	recipeName			VARCHAR(100) 	NOT NULL,
	recipeImageURL		VARCHAR(300) 	NOT NULL,
	recipeCookTime		VARCHAR(50) 	NOT NULL,
	recipePrepTime		VARCHAR(50)		NOT NULL,
	FOREIGN KEY (username) REFERENCES User(username)
);

CREATE TABLE Grocery (
	ingredientName	VARCHAR(100) 	PRIMARY KEY,
	username	VARCHAR(100)	NOT NULL,
	listIndex	VARCHAR(100)	NOT NULL,
	FOREIGN KEY (username) REFERENCES User(username)
);

CREATE TABLE Instructions (
	instruction	VARCHAR(1000) 	PRIMARY KEY,
	username	VARCHAR(100)	NOT NULL,
	recipeName		VARCHAR(100)	NOT NULL,
	listIndex	VARCHAR(100)	NOT NULL,
	FOREIGN KEY (username) REFERENCES User(username),
	FOREIGN KEY (recipeName) REFERENCES User(recipeName)
);

CREATE TABLE Ingredients (
	ingredient	VARCHAR(1000) 	PRIMARY KEY,
	username	VARCHAR(100)	NOT NULL,
	recipeName		VARCHAR(100)	NOT NULL,
	listIndex	VARCHAR(100)	NOT NULL,
	FOREIGN KEY (username) REFERENCES User(username),
	FOREIGN KEY (recipeName) REFERENCES User(recipeName)
);
