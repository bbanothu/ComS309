package com.cychess.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.cychess.game.Assets;
import com.cychess.game.ChessGame;
import com.cychess.game.model.extras.FriendCard;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Creates the friends list table. Shows the user visually on the screen the friends they have
 * and shows them information about each.
 * Created by Peter on 4/9/2018.
 */

public class FriendsList extends Table {

    /**
     * Holds all the usernames of people in the database
     */
    private static ArrayList<String> usernameList;
    /**
     * Holds all of the names of people in the database
     */
    private static ArrayList<String> nameList;
    /**
     * Holds all of the emails of people in the database
     */
    private static ArrayList<String> emailList = new ArrayList<String>();
    /**
     * Texture for friend card
     */
    private final TextureRegion textureRegion;

    private ChessGame game;

    /**
     * Called by the FriendsListScreen. Sets the bounds for the table, which
     * is what is visually shown on screen.
     * @param game
     */
    public FriendsList(ChessGame game) {
        setBounds(0, 0, ChessGame.VWIDTH, ChessGame.VHEIGHT - ChessGame.HUD_HEIGHT);
        this.game = game;
        this.textureRegion = Assets.gameAtlas.findRegion("FriendsListBar");
        usernameList = new ArrayList<String>();
        nameList = new ArrayList<String>();
        //Sends a message to server to retrieve users in database;
        ChessGame.client.loadFriends();
    }

    /**
     * Takes the friends list from the server and parses the information into
     * two separate array lists containing the names and usernames of each
     * user.
     * @param friendsList The string containing all of names and usernames of users
     *                    in the database
     */
    public static void parseFriendsList(String friendsList) {
        Scanner scanner = new Scanner(friendsList.substring(0, friendsList.length()-1));
        scanner.useDelimiter(",");
        while( scanner.hasNext() ) {
            //Get username
            String username = scanner.next();
            username = username.substring(11);
            //Get name
            String name = scanner .next();
            name = name.substring(6, name.length()-1);
            //Check to see if the user name is yours or not
            if( !username.equals(ChessGame.username) ){
                usernameList.add(username);
                nameList.add(name);
            }
        }
        scanner.close();

        //System.out.println("Size of Username List " + usernameList.size());
        //Gdx.app.log("Friends Username List", usernameList.toString());
        //Gdx.app.log("Friends Name List", nameList.toString());
    }

    /**
     * Adds the friends to the screen by looping through the usernameList &
     * nameList arrays calling addFriendCard on each one, that isn't you.
     */
    public void addFriendsToScreen() {
        //Check to see if the arrays are empty
        if( usernameList.isEmpty() || nameList.isEmpty() ){
            ChessGame.client.loadFriends();
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    //Wait to receive friends list from server
                    addFriendsToScreen();
                }
            }, 2);
            Gdx.app.log("ERROR", "usernameList or nameList are empty!");
            return;
        }
        for(int i = 0; i < usernameList.size(); i++){
            addFriendCard(i);
        }
    }

    /**
     * Adds a friend card that the user can interact with containing the friends
     * username, full name, and rank. This allows the user to invite or message
     * the selected friend.
     * @param index The index in the arraylists usernameList & nameList for a particular friend
     */
    private void addFriendCard(int index) {
        //TODO make the friendCards into an arraylist to get info whenever you like
        Label username = new Label(usernameList.get(index), Assets.skin);
        Label name = new Label(nameList.get(index), Assets.skin);
        //Image friendCard = new Image(textureRegion);
        FriendCard friendCard = new FriendCard(textureRegion, usernameList.get(index), nameList.get(index), 0);
        //System.out.println("Friend Card Info: " + friendCard.getUsername() + " " + friendCard.getUsername() + " " + friendCard.getScore());
        float y = (ChessGame.VHEIGHT - ChessGame.HUD_HEIGHT - friendCard.getHeight());

        //Add the background for the card
        friendCard.setPosition(0, y - (index * friendCard.getHeight()));
        addActor(friendCard);

        //Add the name above the background
        name.setPosition(5, y - (index * friendCard.getHeight()) + friendCard.getHeight()/2);
        //name.setScale(.5f);
        addActor(name);

        //Add the username above the background
        username.setPosition(5, y - (index * friendCard.getHeight()) + friendCard.getHeight()/6);
        username.setFontScale(.75f);
        addActor(username);
    }
}
