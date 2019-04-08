DROP DATABASE IF EXISTS project2;

CREATE DATABASE project2;
USE project2;

CREATE TABLE User (
	username VARCHAR(100) PRIMARY KEY,
	password VARCHAR(100) NOT NULL
);

CREATE TABLE Restaurant (
	restaurantName		VARCHAR(100)	PRIMARY KEY,
	restaurantAddress	VARCHAR(100)	NOT NULL,
	restaurantPhone		VARCHAR(100)	NOT NULL,
	restaurantURL		VARCHAR(100)	NOT NULL,
	restaurantRating 	VARCHAR(100)	NOT NULL,
	restaurantDriveTime	VARCHAR(100)	NOT NULL
);

CREATE TABLE Recipe (
	recipeName			VARCHAR(100)	PRIMARY KEY,
	recipeImageURL		VARCHAR(100) 	NOT NULL,
	recipeCookTime		VARCHAR(50) 	NOT NULL,
	recipePrepTime		VARCHAR(50)		NOT NULL,
	recipeInstructions	VARCHAR(1000)	NOT NULL,
	recipeIngredients	VARCHAR(1000)	NOT NULL
);

CREATE TABLE ListRestaurants (
	listName	VARCHAR(100) 	PRIMARY KEY,
	username		VARCHAR(100)	NOT NULL,
	restaurantID	INT(11)			NOT NULL,
	FOREIGN KEY (username) REFERENCES User(username),
	FOREIGN KEY (restaurantName) REFERENCES Restaurant(restaurantName)
);

CREATE TABLE ListRecipes (
	listName	VARCHAR(100) 	PRIMARY KEY,
	username	VARCHAR(100)	NOT NULL,
	recipeID	INT(11)			NOT NULL,
	FOREIGN KEY (username) REFERENCES User(username),
	FOREIGN KEY (recipeName) REFERENCES Recipe(recipeName)
);

