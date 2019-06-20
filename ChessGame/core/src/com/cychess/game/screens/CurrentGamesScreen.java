package com.cychess.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.cychess.game.Assets;
import com.cychess.game.ChessGame;
import com.cychess.game.model.CurrentGames;
import com.cychess.game.scenes.BottomHUD;
import com.cychess.game.scenes.Menu;
import com.cychess.game.scenes.TopHUD;

import static com.cychess.game.ChessGame.State.CURRENTGAMES;

/**
 * LibGDX class which will render the CurrentGamesScreen using the CurrentGames class
 * Created by Peter on 3/24/2018.
 */

public class CurrentGamesScreen implements Screen {
    private CurrentGames games;
    private TopHUD topHUD;
    private BottomHUD bottomHUD;
    private ChessGame game;

    private final Stage stage = new Stage(new FitViewport(ChessGame.VWIDTH, ChessGame.VHEIGHT, ChessGame.CAMERA));

    public CurrentGamesScreen(ChessGame game) {
        Gdx.input.setInputProcessor(this.stage);
        ChessGame.SCREEN_STATE = CURRENTGAMES;
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
        games = new CurrentGames(game);
        topHUD = new TopHUD(game);
        bottomHUD = new BottomHUD(game);
        ChessGame.menu = new Menu(game);
        stage.addActor(games);
        stage.addActor(topHUD);
        stage.addActor(bottomHUD);
        stage.addActor(ChessGame.menu);

        if( ChessGame.role < 3 ) {
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    //Wait to receive current games list from server
                    games.addGamesToScreen();
                }
            }, 1);
        }
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
