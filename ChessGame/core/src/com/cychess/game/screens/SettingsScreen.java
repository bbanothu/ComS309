package com.cychess.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cychess.game.Assets;
import com.cychess.game.ChessGame;
import com.cychess.game.model.Settings;
import com.cychess.game.scenes.BottomHUD;
import com.cychess.game.scenes.Menu;
import com.cychess.game.scenes.TopHUD;

import static com.cychess.game.ChessGame.State.SETTINGS;

/**
 * Created by PJ on 4/24/2018.
 */

public class SettingsScreen implements Screen {

    private Settings settings;
    private TopHUD topHUD;
    private BottomHUD bottomHUD;
    private ChessGame game;

    private final Stage stage = new Stage(new FitViewport(ChessGame.VWIDTH, ChessGame.VHEIGHT, ChessGame.CAMERA));


    public SettingsScreen(ChessGame game) {
        Gdx.input.setInputProcessor(this.stage);
        ChessGame.SCREEN_STATE = SETTINGS;
        this.game = game;
    }

    @Override
    public void show() {
        Assets.loadScreens();
        settings = new Settings(game);
        topHUD = new TopHUD(game);
        bottomHUD = new BottomHUD(game);
        ChessGame.menu = new Menu(game);
        stage.addActor(settings);
        stage.addActor(topHUD);
        stage.addActor(bottomHUD);
        stage.addActor(ChessGame.menu);
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
