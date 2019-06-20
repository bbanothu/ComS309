package com.cychess.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.cychess.game.Assets;
import com.cychess.game.ChessGame;
import com.cychess.game.model.extras.LeaderboardCard;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Creates the leader boards table. Shows the user visually on the screen the leader boards.
 * Created by PJ on 3/28/2018.
 */

public class Leaderboards extends Table {
    /**
     * Holds all of the LeaderboardCards of people on the leaderboards
     */
    private static ArrayList<LeaderboardCard> leaderboardList;

    private ChessGame game;

    /**
     * Called by the LeaderboardsScreen. Sets the bounds for the able, which
     * is what is visually shown on screen.
     * @param game
     */
    public Leaderboards(ChessGame game) {
        setBounds(0, 0, ChessGame.VWIDTH, ChessGame.VHEIGHT - ChessGame.HUD_HEIGHT);
        this.game = game;
        leaderboardList = new ArrayList<LeaderboardCard>();
        //Sends a message to server to retrieve leaderboard information
        ChessGame.client.loadLeaderboard();
        setTransform(true);
        //setDebug(true);
    }

    /**
     * Takes the leader board from the server and parses the information
     * @param leaderboard The string containing all of the leaderboard information
     */
    public static void parseLeaderboard(String leaderboard) {
        //TODO Make sure convert from string to correct type
        System.out.println(leaderboard);

        int index = leaderboard.indexOf('[');
        System.out.println(leaderboard.substring(index+1, leaderboard.length()-1));
        leaderboard = leaderboard.substring(index+1, leaderboard.length()-1);


        Scanner scanner = new Scanner(leaderboard);
        scanner.useDelimiter(",");

        while(scanner.hasNext()){
                String rank = scanner.next();
                String username = scanner.next();
                System.out.println(rank + " ," + username );
                //Contains rank (place on leaderboard), username, score (actual rank of player), & win/loss ratio
                LeaderboardCard user = new LeaderboardCard(0, username, Integer.parseInt(rank), 10.01);
                leaderboardList.add(user);
        }

        int counter = 1;
        //Loop through the leaderboard list backwards and assign the correct rank placement to each person
        for(int i = leaderboardList.size()-1; i >= 0; i--) {
            leaderboardList.get(i).setRank(counter);
            counter++;
        }

        System.out.println(leaderboardList.size());
        scanner.close();
    }

    /**
     * Adds the leader board entries to the screen by looping over
     * an array list of LeaderboardCards
     */
    public void addLeaderboardsToScreen() {
        if( leaderboardList.isEmpty() ){
            ChessGame.client.loadLeaderboard();
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    //Wait to receive friends list from server
                    addLeaderboardsToScreen();
                }
            }, 2);
            Gdx.app.log("ERROR", "leaderboardList is empty!");
            return;
        }
        addHeaders();
        for(int i = leaderboardList.size()-1; i >= 0; i--){
            addLeaderboardCard(i);
        }
    }

    /**
     * Adds the headings for the leaderboard screen to know what each value is
     */
    private void addHeaders() {
        Label placement = new Label("Placement", Assets.skin);
        Label username = new Label("Username", Assets.skin);
        Label rank = new Label("Rank", Assets.skin);
        Label ratioWL = new Label("W/L Ratio", Assets.skin);

        add(placement).expandX();
        add(username).expandX();
        add(rank).expandX();
        add(ratioWL).expandX();
        row();
    }

    /**
     * Adds a leaderboard card containing the users information to the screen
     * @param index The index in the arraylist
     */
    private void addLeaderboardCard(int index) {
        //TODO Sort them by rank properly if not already done on server side
        Label rank = new Label(Integer.toString(leaderboardList.get(index).getRank()), Assets.skin);
        Label username = new Label(leaderboardList.get(index).getUsername(), Assets.skin);
        Label score = new Label(Integer.toString(leaderboardList.get(index).getScore()), Assets.skin);
        Label ratioWL = new Label(Double.toString(leaderboardList.get(index).getRatioWL()), Assets.skin);
        float y = ChessGame.VHEIGHT - ChessGame.HUD_HEIGHT;

        //Add the rank
        add(rank).expandX();
        //Add the username
        add(username).expandX();
        //Add the score
        add(score).expandX();
        //Add the win/loss ratio
        add(ratioWL).expandX();
        //Create another row of the table
        row();
    }
}
