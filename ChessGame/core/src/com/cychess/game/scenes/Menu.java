package com.cychess.game.scenes;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.cychess.game.Assets;
import com.cychess.game.ChessGame;
import com.cychess.game.screens.AnnouncementsScreen;
import com.cychess.game.screens.CurrentGamesScreen;
import com.cychess.game.screens.FriendsListScreen;
import com.cychess.game.screens.LeaderboardScreen;
import com.cychess.game.screens.SettingsScreen;

/**
 * Creates the menu table. Shows the user visually, based on the screen, different
 * buttons to navigate to other screens when clicked on.
 * Created by Peter on 3/28/2018.
 */

public class Menu extends Table {

    private ChessGame game;
    /**
     * Text button for navigating to the FriendsListScreen
     */
    private TextButton friendsListBtn;
    /**
     * Text button for navigating to the ChatRoomScreen
     */
    private TextButton chatRoomBtn;
    /**
     * Text button for navigating to the LeaderboardScreen
     */
    private TextButton leaderboardBtn;
    /**
     * Text button for navigating to the AnnouncementsScreen
     */
    private TextButton announcementBtn;
    /**
     * Text button for navigating to the CurrentGamesScreen
     */
    private TextButton currentGamesBtn;

    /**
     * Text button for navigating to the SettingsScreen
     */
    private TextButton settingsBtn;

    /**
     * Called my many of the screen classes. Sets the bounds for the table, which is what is
     * visually shown to the user. Provides the user with the ability to navigate to other
     * screens, based on what screen they're on.
     * @param game
     */
    public Menu(ChessGame game) {
        this.game = game;
        setBounds(0, ChessGame.MENU_Y, ChessGame.MENU_WIDTH, ChessGame.TOP_HUD_Y);
        setVisible(false);

        friendsListBtn = new TextButton("Friends List", Assets.skin);
        friendsListBtnListener();
        chatRoomBtn = new TextButton("Chat Room", Assets.skin);
        chatRoomBtnListener();
        leaderboardBtn = new TextButton("Leaderboards", Assets.skin);
        leaderboardBtnListener();
        announcementBtn = new TextButton("Announcements", Assets.skin);
        announcementBtnListener();
        currentGamesBtn = new TextButton("Current Games", Assets.skin);
        currentGamesBtnListener();
        settingsBtn = new TextButton("Settings", Assets.skin);
        settingsBtnListener();

        switch (ChessGame.SCREEN_STATE) {
            case CHESSBOARD:
                setupChessBoard();
                break;
            case CURRENTGAMES:
                setupCurrentGames();
                break;
            case CHATROOM:
                setupChatRoom();
                break;
            case FRIENDSLIST:
                setupFriendsList();
                break;
            case LEADERBOARD:
                setupLeaderBoards();
                break;
            case ANNOUNCEMENT:
                setupAnnouncements();
                break;
            case SETTINGS:
                setupSettings();
                break;
            default:
                break;
        }

        //setDebug(true);
    }

    /**
     * Sets up the menu navigation when on the ChessBoardScreen. Gives navigation
     * options to other screens excluding the current one.
     */
    private void setupChessBoard() {
        add(announcementBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(currentGamesBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(chatRoomBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(friendsListBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(leaderboardBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(settingsBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
    }

    /**
     * Sets up the menu navigation when on the CurrentGamesScreen. Gives navigation
     * options to other screens excluding the current one.
     */
    private void setupCurrentGames() {
        add(announcementBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(chatRoomBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(friendsListBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(leaderboardBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(settingsBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
    }

    /**
     * Sets up the menu navigation when on the AnnouncementScreen. Gives navigation
     * options to other screens excluding the current one.
     */
    private void setupAnnouncements() {
        add(currentGamesBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(chatRoomBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(friendsListBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(leaderboardBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(settingsBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
    }

    /**
     * Sets up the menu navigation when on the FriendsListScreen. Gives navigation
     * options to other screens excluding the current one.
     */
    private void setupFriendsList() {
        add(announcementBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(currentGamesBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(chatRoomBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(leaderboardBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(settingsBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
    }

    /**
     * Sets up the menu navigation when on the LeaderboardScreen. Gives navigation
     * options to other screens excluding the current one.
     */
    private void setupLeaderBoards() {
        add(announcementBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(currentGamesBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(chatRoomBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(friendsListBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(settingsBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
    }

    /**
     * Sets up the menu navigation when on the ChatRoomScreen. Gives navigation
     * options to other screens excluding the current one.
     */
    private void setupChatRoom() {
        add(announcementBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(currentGamesBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(friendsListBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(leaderboardBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(settingsBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
    }

    /**
     * Sets up the menu navigation when on the SettingsScreen. Gives navigation
     * options to other screens excluding the current one.
     */
    private void setupSettings() {
        add(announcementBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(currentGamesBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(chatRoomBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(friendsListBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
        add(leaderboardBtn).expandX().width(ChessGame.MENU_WIDTH);
        row();
    }

    /*
     * Button listeners beyond this point
     */

    private void settingsBtnListener() {
        settingsBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                ChessGame.client.ingame = false;
                game.setScreen(new SettingsScreen(game));
            }
        });
    }

    /**
     * Button listener for the friends list button. Sets the screen to the
     * FriendsListScreen when clicked.
     */
    private void friendsListBtnListener() {
        friendsListBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                ChessGame.client.ingame = false;
                game.setScreen(new FriendsListScreen(game));
            }
        });
    }

    /**
     * Button listener for the chat room button. Sets the screen to the
     * ChatRoomScreen when clicked.
     */
    private void chatRoomBtnListener() {
        chatRoomBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                ChessGame.client.ingame = false;
            }
        });
    }

    /**
     * Button listener for the leader board button. Sets the screen to the
     * LeaderboardScreen when clicked.
     */
    private void leaderboardBtnListener() {
        leaderboardBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                ChessGame.client.ingame = false;
                game.setScreen(new LeaderboardScreen(game));
            }
        });
    }

    /**
     * Button listener for the announcement button. Sets the screen to the
     * AnnouncementScreen when clicked.
     */
    private void announcementBtnListener() {
        announcementBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                ChessGame.client.ingame = false;
                game.setScreen(new AnnouncementsScreen(game));
            }
        });
    }

    /**
     * Button listener for the current games button. Sets the screen to the
     * CurrentGamesScreen when clicked.
     */
    private void currentGamesBtnListener() {
        currentGamesBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                ChessGame.client.ingame = false;
                game.setScreen(new CurrentGamesScreen(game));
            }
        });
    }
}
