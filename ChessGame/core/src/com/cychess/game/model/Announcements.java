package com.cychess.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;
import com.cychess.game.Assets;
import com.cychess.game.ChessGame;
import com.cychess.game.model.extras.AnnouncementsCard;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Creates the announcements table. Shows the user visually on the screen the announcements
 * that have been posted and allow them to post. (With the correct permissions)
 * Created by PJ on 3/28/2018.
 */

public class Announcements extends Table{

    private ChessGame game;

    /**
     * Holds all the usernames of people who posted announcements
     */
    private static ArrayList<String> usernameList;

    /**
     * Holds all the dates of announcements posted
     */
    private static ArrayList<String> dateList;

    /**
     * Holds all of the text of announcements posted
     */
    private static ArrayList<String> textList;

    /**
     * Called by the AnnouncementsScreen. Sets the bounds for the table, which
     * is what is visually shown on screen.
     * @param game
     */
    public Announcements(ChessGame game) {
        this.game = game;
        usernameList = new ArrayList<String>();
        dateList = new ArrayList<String>();
        textList = new ArrayList<String>();
        setBounds(0, 0, ChessGame.VWIDTH, ChessGame.VHEIGHT - ChessGame.HUD_HEIGHT);
        ChessGame.client.loadAnnouncements();
        //setDebug(true);
    }

    /**
     * Loads the announcements from the database. And displays them.
     * @param announcements The string containing all of the announcements posted
     *                      to the database
     */
    public static void parseAnnouncements(String announcements) {
        int index = announcements.indexOf('[');
        System.out.println(announcements.substring(index+1, announcements.length()-1));
        announcements = announcements.substring(index+1, announcements.length()-1);
        Scanner scanner = new Scanner(announcements);
        scanner.useDelimiter(",");
        while( scanner.hasNext() ) {
            //Get text
            String text = scanner.next();
            text = text.substring(11);
            textList.add(text);

            //Get date
            String date = scanner.next();
            date = date.substring(10);
            dateList.add(date);

            //Get username
            String username = scanner.next();
            username = username.substring(15);
            usernameList.add(username);

            //Skip id
            String id = scanner.next();

//            System.out.println("TEXT: " + text);
//            System.out.println("DATE: " + date);
//            System.out.println("USER: " + username);
//            System.out.println("ID: " + id);
        }
        scanner.close();

        Gdx.app.log("Username list", usernameList.toString());
        Gdx.app.log("Date list", dateList.toString());
        Gdx.app.log("Text list", textList.toString());
    }

    /**
     * Adds the announcements to the screen by looping through the username,
     * date, and text list arrays calling addAnnouncementCard on each one.
     */
    public void addAnnouncementsToScreen() {
        //Check to see if the arrays are empty
        if( usernameList.isEmpty() || dateList.isEmpty() || textList.isEmpty() ) {
            ChessGame.client.loadAnnouncements();
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    //Wait to receive announcements from server
                    addAnnouncementsToScreen();
                }
            }, 2);
            Gdx.app.log("ERROR", "usernameList, dateList, or textList are empty!");
            return;
        }
        for(int i = 0; i < usernameList.size(); i++){
            addAnnouncementCard(i);
        }

    }

    /**
     * Adds an announcement card holding all of the information regarding that specific
     * announcement. Such as the date posted, user who posted it, and the announcement itself.
     * @param index The index in the array lists username, date, & text for a particular announcement
     */
    private void addAnnouncementCard(int index) {
        //TODO make the announcementsCards into an arraylist to get info whenever you like
        Label username = new Label(usernameList.get(index), Assets.skin);
        Label date = new Label(dateList.get(index), Assets.skin);
        Label announcement = new Label(textList.get(index), Assets.skin);

        AnnouncementsCard aCard = new AnnouncementsCard(dateList.get(index), usernameList.get(index), textList.get(index));
        float y = ChessGame.VHEIGHT - ChessGame.HUD_HEIGHT;

        //Add the date
        add(date).expandX();
        //Add the username
        add(username).expandX();
        //Add the announcement
        add(announcement).expandX();
        //Create another row of the table
        row();
    }

    /**
     * Posts an announcement to the database.
     */
    private void postAnnouncement() {

    }
}
