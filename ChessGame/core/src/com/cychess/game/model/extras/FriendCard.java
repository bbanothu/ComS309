package com.cychess.game.model.extras;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.cychess.game.Assets;
import com.cychess.game.ChessGame;

/**
 * Creates an object that holds information about a specific user in the database.
 * Used by the FriendsList class to make a list of users.
 * Created by Peter on 4/16/2018.
 */

public class FriendCard extends Image {

    /**
     * Name of the user associated with this friend card
     */
    private String name;
    /**
     * Username of the user associated with this friend card
     */
    private String username;
    /**
     * Score of the user associated with this friend card
     */
    private int score;

    //TODO Make a special Image for this that holds the username and name (FriendCard)
    //This allows the user to click a card and given a prompt to message/invite
    //Has online status shown
    //Has rank shown
    /**
     * Creates an intractable friend card that is displayed on screen showing
     * the users name, username, and score.
     */
    public FriendCard(TextureRegion textureRegion, String username, String name, int score) {
        super(textureRegion);
        this.username = username;
        this.name = name;
        this.score = score;
        //TODO REMOVE LATER
        //setDebug(true);
    }

    /**
     * Sets the username associated with this friend card
     * @param username A string containing the score of this user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the username associated with this friend card
     * @return A string
     */
    public String getUsername() {
        return username;
    }

    /**
     * Overrides the normal set name and alters it to set the
     * name of the user associated with this friend card
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Overrides the normal get name and alters it to return the
     * name of the user associated with this friend card
     * @return A string
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets the score associated with this friend card
     * @param score An int containing the score of this user
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Returns the score associated with this friend card
     * @return An int
     */
    public int getScore() {
        return score;
    }
}
