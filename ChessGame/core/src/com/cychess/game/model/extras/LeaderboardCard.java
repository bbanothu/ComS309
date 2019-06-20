package com.cychess.game.model.extras;

import com.cychess.game.model.Leaderboards;

/**
 * Creates an object that holds a user's information about their placement
 * on the leaderboard. Used by the Leaderboard class.
 * Created by Peter on 4/16/2018.
 */
public class LeaderboardCard {

    /**
     * The user's rank
     */
    private int rank;
    /**
     * The user's username
     */
    private String username;
    /**
     * The user's score
     */
    private int score;
    /**
     * The user's ratio of wins and losses
     */
    private double ratioWL;

    /**
     * Creates a card that the Leaderboard class uses to store a user's information
     * @param rank The user's placement on the leaderboard
     * @param username The user's username
     * @param score The user's score
     * @param ratioWL The user's win/loss ratio
     */
    public LeaderboardCard(int rank, String username, int score, double ratioWL){
        this.rank = rank;
        this.username = username;
        this. score = score;
        this.ratioWL = ratioWL;
    }

    /**
     * Get rank of this user
     * @return an int containing the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Set rank of this user
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * Get username of this user
     * @return a string containing the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get score of this user
     * @return an int containing the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Get win/loss ratio of this user
     * @return a double containing the win/loss ratio
     */
    public double getRatioWL() {
        return ratioWL;
    }
}
