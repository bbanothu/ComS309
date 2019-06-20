package com.cychess.game.model.extras;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.cychess.game.ChessGame;

/**
 * Creates an object that holds a user's information about their ongoing
 * games. Used by the CurrentGames class.
 * Created by Peter on 4/16/2018.
 */

public class CurrentGamesCard extends Image {

    private int gameID;

    private String p1;

    private String p2;

    private int round;

    public CurrentGamesCard(TextureRegion textureRegion, int gameID, String p1, String p2, int round) {
        super(textureRegion);
        this.gameID = gameID;
        this.p1 = p1;
        this.p2 = p2;
        this.round = round;
        listener();
    }

    private void listener() {
        addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                ChessGame.client.output.println("CURRGAMES-OBSERVER-" + gameID);
            }
        });
    }

    public int getGameID() {
        return gameID;
    }

    public String getP1() {
        return p1;
    }

    public String getP2() {
        return p2;
    }

    public int getRound() {
        return round;
    }

    @Override
    public String toString() {
        return "GAMEID: " + gameID + " P1: " + p1 + " P2: " + p2 + " ROUND: " + round;
    }
}
