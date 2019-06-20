package com.cychess.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cychess.game.Assets;
import com.cychess.game.ChessGame;
import com.cychess.game.model.Login;

import static com.cychess.game.Assets.skin;

/**
 * LibGDX class which will render the LoginScreen using the Login class
 * Created by PJ on 3/24/2018.
 */
public class LoginScreen implements Screen {
    /**
     * Error codes for the login screen. 0 = no error,
     * 1 = fields not filled, 2 = email/password wrong,
     * 3 = FAILED
     */
    public static int errorCode; //0 = no error, 1 = fields not filled in,
                                // 2 = email/password wrong, 3 = FAILED

    private Login login;
    private ChessGame game;

    private final Stage stage = new Stage(new FitViewport(ChessGame.VWIDTH, ChessGame.VHEIGHT));

    public LoginScreen(ChessGame game) {
        Gdx.input.setInputProcessor(this.stage);
        this.game = game;
        errorCode = 0;
    }

    /**
     * Called when the screen is set to this one. Loads the appropriate table,
     * top and bottoms huds, and menu then adds them to the stage so they can
     * be seen.
     */
    @Override
    public void show() {
        Assets.loadScreens();
        login = new Login(game);
        login.setup();
        stage.addActor(login);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        checkErrorCodes();
    }

    /**
     * Shows a dialog box based on the error code
     */
    private void checkErrorCodes() {
        //Fields not filled in, show error window
        if( errorCode == 1 ){
            new Dialog("Error!", skin, "default")
            {
                {
                    text("Please fill in all the information.");
                    button("Ok");
                }
            }.show(stage);
            errorCode = 0;
        }else if( errorCode == 2 ){
            new Dialog("Error!", skin, "default")
            {
                {
                    text("The email or password you entered is incorrect!");
                    button("Ok");
                }
            }.show(stage);
            errorCode = 0;
        }else if( errorCode == 3 ){
            new Dialog("Error!", skin, "default")
            {
                {
                    text("Logging in failed!");
                    button("Ok");
                }
            }.show(stage);
            errorCode = 0;
        }
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
