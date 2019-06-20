# CyChess
For our Computer Science 309 course, at Iowa State University during the Fall of 2018, me and three other team members created a chess application for desktop and/or android. We were able to run this easily by using the Java libGDX framework for client-side development. For server-side we used Springboot, another Java development platform and we used a MySQL database to store user information.

## Features on Client (99% done by me)
- Three roles: admin, mod, and user
- Playing a game against another player in real time
- Friendslist that displayed all users in the database
- Observer who could watch two users play a game (only if you are a mod or admin)
- Announcements page
- Settings page to change user name, email, password, or delete account
- Login page and ability to create an account
- Leader boards
- Casual and Ranked games

## Features on Server (20% done by me)
- Website
- View and post announcements
- Login page and ability to create an account
- Delete users information from database (if you are an admin)
- Settings page to change user name, email, password, or delete account

## New Things Learned
- Setting up a SpringBoot server that can handle GET/POST requests
- Interact with a MySQL database. Included storing user data, querying for user data based on certain parameters.
- POST requests to send information to the server and what to do with the information received back.
- Using Java's object oriented programming to create objects like a chessboard that took in chess tiles then put chess pieces on those tiles, and render this all to the screen using libGDX.
- Using a thread for the socket connection so the app could still function properly.
- Using Sockets to send and receive information once logged in on the client. I had to separate in-game socket requests (sending piece movements and receiving them) from everything else, like loading the friendlist or announcements pages. For all of these requests I needed to parse the information received in order to display it on the screen to the user. For the server-side sockets I made a list of users that included their socket connection, and other information we may need to use. This allowed us to search through the list to find the user and take their socket connection in order to connect them to a game with another user.

## Pictures
![friendslist](https://user-images.githubusercontent.com/28559051/39897088-6793bf9a-5476-11e8-972f-da7bc878a628.png)
![leaderboards](https://user-images.githubusercontent.com/28559051/39897089-67a7cdb4-5476-11e8-9613-bfc038809770.png)
