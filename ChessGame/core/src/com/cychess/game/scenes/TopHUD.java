package com.cychess.game.scenes;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.cychess.game.Assets;
import com.cychess.game.ChessGame;

/**
 * Creates the top hud table. Shows the user visually, based on the screen, different
 * information and actions they can do.
 * Created by Peter on 3/28/2018.
 */

public class TopHUD extends Table {

    private ChessGame game;

    private final TextureRegion navBarRegion;
    private TextureRegion topRegion;

    private Image navBarBtn;
    private Image topImage;

    /**
     * Called by many of the screen calsses. Sets the bounds for the table, which
     * is what is visually shown to the user. Tells the user at the top what screen
     * they are currently on.
     * @param game
     */
    public TopHUD(ChessGame game) {
        //TODO add label for screens
        this.game = game;
        setBounds(0, ChessGame.TOP_HUD_Y, ChessGame.VWIDTH, ChessGame.HUD_HEIGHT);
        navBarRegion = Assets.gameAtlas.findRegion("navBar");


//        navBarBtn = new Image(textureRegion);
//        navBarBtnListener();
//        add(navBarBtn).expandX().width(ChessGame.HUD_HEIGHT).align(Align.left);

        switch (ChessGame.SCREEN_STATE) {
            case CHESSBOARD:
                topRegion = Assets.gameAtlas.findRegion("chessboardBar");
                setupChessboard();
                break;
            case CURRENTGAMES:
                topRegion = Assets.gameAtlas.findRegion("currentMatchesBar");
                setupCurrentGames();
                break;
            case CHATROOM:
                //setupChatRoom();
                break;
            case FRIENDSLIST:
                topRegion = Assets.gameAtlas.findRegion("friendsBar");
                setupFriendsList();
                break;
            case LEADERBOARD:
                topRegion = Assets.gameAtlas.findRegion("leaderboardsBar");
                setupLeaderboard();
                break;
            case ANNOUNCEMENT:
                topRegion = Assets.gameAtlas.findRegion("announcementsBar");
                setupAnnouncements();
                break;
            case SETTINGS:
                topRegion = Assets.gameAtlas.findRegion("settingsBar");
                setupSettings();
                break;
            case OBSERVER:
                topRegion = Assets.gameAtlas.findRegion("chessboardBar");
                setupChessboard();
                break;
            default:
                break;
        }

        //setDebug(true);
    }

    /**
     * Sets up the top hud for the ChessBoardsScreen.
     */
    private void setupChessboard() {
        navBarBtn = new Image(navBarRegion);
        navBarBtnListener();
        add(navBarBtn).expandX().width(ChessGame.HUD_HEIGHT).align(Align.left);
        topImage = new Image(topRegion);
        add(topImage).expandX();
    }

    /**
     * Sets up the top hud for the CurrentGamesScreen.
     */
    private void setupCurrentGames() {
        navBarBtn = new Image(navBarRegion);
        navBarBtnListener();
        add(navBarBtn).expandX().width(ChessGame.HUD_HEIGHT).align(Align.left);
        topImage = new Image(topRegion);
        add(topImage);
    }

    /**
     * Sets up the top hud for the AnnouncementsScreen.
     */
    private void setupAnnouncements() {
        navBarBtn = new Image(navBarRegion);
        navBarBtnListener();
        add(navBarBtn).expandX().width(ChessGame.HUD_HEIGHT).align(Align.left);
        topImage = new Image(topRegion);
        add(topImage);
    }

    /**
     * Sets up the top hud for the FriendsListScreen.
     */
    private void setupFriendsList() {
        navBarBtn = new Image(navBarRegion);
        navBarBtnListener();
        add(navBarBtn).expandX().width(ChessGame.HUD_HEIGHT).align(Align.left);
        topImage = new Image(topRegion);
        add(topImage);
    }

    /**
     * Sets up the top hud for the FriendsListScreen.
     */
    private void setupLeaderboard() {
        navBarBtn = new Image(navBarRegion);
        navBarBtnListener();
        add(navBarBtn).expandX().width(ChessGame.HUD_HEIGHT).align(Align.left);
        topImage = new Image(topRegion);
        add(topImage);
    }

    /**
     * Sets up the top hud for the SettingsScreen
     */
    private void setupSettings() {
        navBarBtn = new Image(navBarRegion);
        navBarBtnListener();
        add(navBarBtn).expandX().width(ChessGame.HUD_HEIGHT).align(Align.left);
        topImage = new Image(topRegion);
        add(topImage);
    }

    /*
     * Listeners for buttons below
     */

    /**
     * Make the navigation buttons visible when clicked if its not and vice versa.
     */
    private void navBarBtnListener() {
        navBarBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                //game.setScreen(new ChessBoardScreen(game));
                if( ChessGame.menu.isVisible() )
                    ChessGame.menu.setVisible(false);
                else
                    ChessGame.menu.setVisible(true);
            }
        });
    }
}
