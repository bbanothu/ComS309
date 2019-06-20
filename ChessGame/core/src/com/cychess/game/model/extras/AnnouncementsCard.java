package com.cychess.game.model.extras;

/**
 * Creates an object that holds information about a certain announc
 * Created by PJ on 4/22/2018.
 */

public class AnnouncementsCard {

    /**
     * The username for who posted this announcement
     */
    private String username;
    /**
     * The date this announcement was posted
     */
    private String date;
    /**
     * The actual announcement posted
     */
    private String announcement;

    /**
     * Creates a card that the Announcement class uses to store information
     * regarding a particular announcement
     * @param date The date this announcement was posted
     * @param username The username of the user who posted this announcement
     * @param announcement The announcement itself
     */
    public AnnouncementsCard(String date, String username, String announcement) {
        this.date = date;
        this.username = username;
        this.announcement = announcement;
    }

    /**
     * Get the date of this announcement
     * @return a string containing the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Get the username of this announcement
     * @return a string containing the announcement
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the announcement
     * @return a string containing the announcement
     */
    public String getAnnouncement() {
        return announcement;
    }
}
