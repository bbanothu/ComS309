package com.cychess.game.scenes;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.cychess.game.Assets;
import com.cychess.game.ChessGame;

/**
 * Creates the bottom hud table. Shows the user visually, based on the screen, different
 * buttons for actions they can do.
 * Created by Peter on 3/28/2018.
 */

public class BottomHUD extends Table {

    private ChessGame game;

    /**
     * Text button for finding a casual game
     */
    private TextButton findCasualGameBtn;
    /**
     * Text button for finding a ranked game
     */
    private TextButton findRankedGameBtn;
    /**
     * Text button for forfeiting a game
     */
    private TextButton forfeitBtn;

    /**
     * Text button for backing out of a game
     */
    private TextButton backBtn;

    /**
     * Called by many of the screen classes. Sets the bounds for the table, which is what is
     * visually shown to the user. Gives the ability to add a navigation button to the
     * bottom of the screen based on what screen you're on.
     * @param game
     */
    public BottomHUD(ChessGame game) {
        this.game = game;
        setBounds(0, 0, ChessGame.VWIDTH, ChessGame.HUD_HEIGHT);

        switch (ChessGame.SCREEN_STATE) {
            case CHESSBOARD:
                setupChessboard();
                break;
            case CURRENTGAMES:
                setupCurrentGames();
                break;
            case CHATROOM:
                break;
            case FRIENDSLIST:
                break;
            case LEADERBOARD:
                break;
            case ANNOUNCEMENT:
                break;
            case SETTINGS:
                break;
            case OBSERVER:
                setupObserver();
                break;
            default:
                break;
        }

        //setDebug(true);
    }

    /**
     * Sets up the bottom hud for the ChessBoardScreen by making a button to forfeit
     * a game.
     */
    private void setupChessboard() {
        forfeitBtn = new TextButton("Ask to forfeit!", Assets.skin);
        forfeitBtnListener();
        add(forfeitBtn).expandX().width(ChessGame.VWIDTH/2);
        backBtn = new TextButton("Back", Assets.skin);
        backBtnListener();
        add(backBtn).expandX().width(ChessGame.VWIDTH/2);
    }

    /**
     * Sets up the bottom hud for the CurrentGamesScreen by making a button to find
     * a game.
     */
    private void setupCurrentGames() {
        findCasualGameBtn = new TextButton("Find a Casual Game", Assets.skin);
        findCasualGameBtnListener();
        add(findCasualGameBtn).expandX();
        findRankedGameBtn = new TextButton("Find a Ranked Game", Assets.skin);
        findRankedGameBtnListener();
        add(findRankedGameBtn).expandX();
    }

    private void setupObserver() {
        backBtn = new TextButton("Back", Assets.skin);
        backBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                ChessGame.client.output.println("BACK");
            }
        });
        add(backBtn).expandX().width(ChessGame.VWIDTH);
    }


    /*
     * Listeners for buttons below
     */

    /**
     * Button listener for the back button. When clicked sends a message to the server to
     * back out of current game.
     */
    private void backBtnListener() {
        backBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                ChessGame.client.quitGame();
                //game.setScreen(new ChessBoardScreen(game));
            }
        });
    }

    /**
     * Button listener for the find casual game button. When clicked sends a message to the
     * server to find a game.
     */
    private void findCasualGameBtnListener() {
        findCasualGameBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                ChessGame.client.findCasualGame();
                //game.setScreen(new ChessBoardScreen(game));
            }
        });
    }

    /**
     * Button listener for the find ranked game button. When clicked sends a message to the
     * server to find a game.
     */
    private void findRankedGameBtnListener() {
        findRankedGameBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                ChessGame.client.findRankedGame();
                //game.setScreen(new ChessBoardScreen(game));
            }
        });
    }

    /**
     * Button listener for the forfeit game button. When clicked sends a message to the
     * server to forfeit the game.
     */
    private void forfeitBtnListener() {
        forfeitBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                ChessGame.client.sendForfeit();
            }
        });
    }
}
