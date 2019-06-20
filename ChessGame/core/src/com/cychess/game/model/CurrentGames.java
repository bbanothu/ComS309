package com.cychess.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;
import com.cychess.game.Assets;
import com.cychess.game.ChessGame;
import com.cychess.game.model.extras.CurrentGamesCard;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Creates the current games table. Shows the user visually on screen the current games
 * they are in and can play.
 * Created by Peter on 3/24/2018.
 */
public class CurrentGames extends Table{

    private ChessGame game;

    /**
     * Contains a list of all the different games being played
     */
    public static ArrayList<CurrentGamesCard> currentGames = new ArrayList<CurrentGamesCard>();;

    /**
     * Texture for current game card
     */
    private static TextureRegion textureRegion;

    /**
     * Called by the CurrentGamesScreen. Sets the bounds for the table, which
     * is what is visually shown on screen. Connects the user to the socket
     * connection and calls loadGames.
     * @param game
     */
    public CurrentGames(ChessGame game) {
        setBounds(0, ChessGame.SCREEN_Y, ChessGame.VWIDTH, ChessGame.VWIDTH);
        this.game = game;
        this.textureRegion = Assets.gameAtlas.findRegion("CurrentGamesBar");
        loadGames();
        //setDebug(true);
    }

    /**
     * Initializes the current games screen and populates it with
     * games being played. If you have a role permission of 1 or 2
     * you are able to spectate people's games.
     */
    public void loadGames() {
        if( ChessGame.role < 3 ) {
            ChessGame.client.loadGames();
        }
    }

    public static void parseGames(String games) {
        currentGames = new ArrayList<CurrentGamesCard>();

        int index = games.indexOf('[');
        System.out.println(games.substring(index+1, games.length()-1));
        games = games.substring(index+1, games.length()-1);
        Scanner scanner = new Scanner(games);
        scanner.useDelimiter(", ");

        while( scanner.hasNext() ) {
            String gameid = scanner.next();
            gameid = gameid.substring(9);

            String p1id = scanner.next();
            p1id = p1id.substring(6);

            String p2id = scanner.next();
            p2id = p2id.substring(6);

            String round = scanner.next();
            round = round.substring(6, round.length()-1);

            CurrentGamesCard game = new CurrentGamesCard(textureRegion, Integer.parseInt(gameid), p1id, p2id, Integer.parseInt(round));

            currentGames.add(game);

            System.out.println("GAME ID: " + gameid);
            System.out.println("P1 ID: " + p1id);
            System.out.println("P2 ID: " + p2id);
            System.out.println("ROUND: " + round);
        }
        scanner.close();

        System.out.println("Current games parsed!");

        //System.out.println("CURRENT GAMES: " + currentGames.toString());
    }


    public void addGamesToScreen() {
        System.out.println("Add games to screen!");
        //Check to see if array list of current games is empty
        if( currentGames.isEmpty() ){
            ChessGame.client.loadGames();
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    //Wait to receive games list from server
                    addGamesToScreen();
                }
            }, 2);
            Gdx.app.log("ERROR", "currentGames is empty!");
            return;
        }
        for(int i = 0; i < currentGames.size(); i++){
            addCurrentGamesCard(i);
        }
    }

    private void addCurrentGamesCard(int index) {
        CurrentGamesCard game = currentGames.get(index);
        Label p1 = new Label(game.getP1(), Assets.skin);
        Label p2 = new Label(game.getP2(), Assets.skin);
        Label vs = new Label("VS", Assets.skin);
        Label round = new Label("Round: " + String.valueOf(game.getRound()), Assets.skin);

        float y = (ChessGame.VHEIGHT - ChessGame.HUD_HEIGHT - game.getHeight()*2);

        //Adds the background for the card
        game.setPosition(0, y - (index * game.getHeight()) - 5);
        addActor(game);

        //Adds the P1 name to the card
        p1.setPosition( ChessGame.VWIDTH/2 - p1.getWidth(),  y - (index * game.getHeight()) + game.getHeight()/2);
        //p1.setFontScale(2f);
        addActor(p1);

        //Adds the VS to the card
        vs.setPosition(ChessGame.VWIDTH/2 + 20,y - (index * game.getHeight()) + game.getHeight()/2);
        addActor(vs);

        //Adds the P2 name to the card
        p2.setPosition( ChessGame.VWIDTH/2 + p2.getWidth(),  y - (index * game.getHeight()) + game.getHeight()/2);
        //p2.setFontScale(2f);
        addActor(p2);

        //Adds the round to the card
        round.setPosition(ChessGame.VWIDTH - round.getWidth(), y - (index * game.getHeight()) + round.getHeight()/4);
        addActor(round);

        System.out.println("Current game cards added!");
    }
}
