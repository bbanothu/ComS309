package com.cychess.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Peter on 3/24/2018.
 */

/**
 * Loads or destroys the assets for the game screen or other screens.
 */
public class Assets {
    /**
     * The skin used by a screen
     */
    public static Skin skin;

    /**
     * The texture atlas loaded for a specific screen
     */
    public static TextureAtlas gameAtlas;

    /**
     * Loads all assets required by ChessBoardScreen
     */
    public static void loadGame() {
        gameAtlas = new TextureAtlas(Gdx.files.internal("atlas/ChessBoardPiecesAndTiles.pack"));
        skin = new Skin(Gdx.files.internal("data/gdx-holo/skin/uiskin.json"));
    }

    /**
     * Loads all assets required by all other screens
     */
    public static void loadScreens() {
        gameAtlas = new TextureAtlas(Gdx.files.internal("atlas/OtherScreens.pack"));
        skin = new Skin(Gdx.files.internal("data/gdx-holo/skin/uiskin.json"));
    }


    /**
     * Disposes of all assets
     */
    public static void dispose() {
        gameAtlas.dispose();
    }
}
