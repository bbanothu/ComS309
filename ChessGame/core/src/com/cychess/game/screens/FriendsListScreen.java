package com.cychess.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cychess.game.Assets;
import com.cychess.game.ChessGame;
import com.cychess.game.model.Announcements;
import com.cychess.game.model.FriendsList;
import com.cychess.game.scenes.BottomHUD;
import com.cychess.game.scenes.Menu;
import com.cychess.game.scenes.TopHUD;

import static com.cychess.game.ChessGame.State.ANNOUNCEMENT;
import static com.cychess.game.ChessGame.State.FRIENDSLIST;

/**
 * LibGDX class which will render the FriendsListScreen using the FriendsList class
 * Created by Peter on 4/9/2018.
 */

public class FriendsListScreen implements Screen {
    private FriendsList friendsList;
    private TopHUD topHUD;
    private BottomHUD bottomHUD;
    private ChessGame game;

    private final Stage stage = new Stage(new FitViewport(ChessGame.VWIDTH, ChessGame.VHEIGHT, ChessGame.CAMERA));


    public FriendsListScreen(ChessGame game) {
        Gdx.input.setInputProcessor(this.stage);
        ChessGame.SCREEN_STATE = FRIENDSLIST;
        this.game = game;
    }

    /**
     * Called when the screen is set to this one. Loads the appropriate table,
     * top and bottoms huds, and menu then adds them to the stage so they can
     * be seen.
     */
    @Override
    public void show() {
        Assets.loadScreens();
        friendsList = new FriendsList(game);
        topHUD = new TopHUD(game);
        bottomHUD = new BottomHUD(game);
        ChessGame.menu = new Menu(game);
        stage.addActor(friendsList);
        stage.addActor(topHUD);
        stage.addActor(bottomHUD);
        stage.addActor(ChessGame.menu);

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                //Wait to receive friends list from server
                friendsList.addFriendsToScreen();
            }
        }, 1f);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    private void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        Assets.dispose();
    }
}
