# My Personal Project

## What will the application do

This program is a local multiplayer game, supporting two to four players to play.
Players are placed in a two-dimensional map and each player is assigned a color. 
They color the map by moving around the map or using props.
After a certain amount of time, the game will settle the final color area as the final score for each player.

## Who will use it

Everyone is welcome to play the game.

## Why is this project interest to you

I've been thinking about how to design some fun party games 
that anyone can play at the party and enjoy the games.
This will promote friendship, unless they are overly concerned about winning or losing the game.

## User Stories

* As a player, I want to be able to add multiple strongholds in the map
* As a player, I want to move my character in the map
* As a player, I want to add and remove players can play in a game
* As a player, I want to view the final score of a game
* As a player, when I quit the unfinished match, I have the option to save all state of the match
* As a player, when I start the application, I want to be given the option to resume my last match from file.

# Instructions for Grader

* You can always use "W" and "S" to move the cursor of any menu, and hit "Enter" to confirm your choice
* You can generate the events related to adding players to the game by
  * after starting game, hit "A" and "D" to choose player and hit "Enter" when selecting "ADD PLAYER" to add player
  * after starting game, use "A" and "D" to choose player and hit "Enter" when selecting "REMOVE PLAYER" to remove player
* You can generate the events related to adding strongholds to the map by
  * after starting a match, hit "WSAD" to occupy strongholds and add it to the map
  * after starting a match, make a circle to gain bonus strongholds
* Player images are the visual components, you can find them in "./data/players"
* You can save the match by hitting "ESC" and select "SAVE AND QUIT MATCH" when playing in a match
* You can load the match by selecting "RESUME GAME" on title screen, i.e. the opening page of the game