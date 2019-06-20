package com.cychess.game.logic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.cychess.game.Assets;
import com.cychess.game.ChessGame;

/**
 * Creates a tile actor to add to the chessboard
 * Created by Peter on 3/24/2018.
 */
public class Tile extends Actor {

    /**
     * Determines if a tile is highlighted or not.
     */
    public boolean isHighlighted;

    /**
     * The texture for a tile.
     */
    private final TextureRegion textureRegion;

    /**
     * The highlighted texture for a highlighted tile.
     */
    private TextureRegion highlightedTextureRegion;

    /**
     * Creates a tile given the input parameters and sets the bounds for this actor.
     * @param x
     * @param y
     * @param isRed
     */
    public Tile(int x, int y, boolean isRed) {
        setBounds(x*(ChessGame.VWIDTH/8), y*(ChessGame.VWIDTH/8), (ChessGame.VWIDTH/8), (ChessGame.VWIDTH/8));

        if(isRed){
            textureRegion = Assets.gameAtlas.findRegion("redTile");
            highlightedTextureRegion = Assets.gameAtlas.findRegion("redTileHighlighted");
        }else{
            textureRegion = Assets.gameAtlas.findRegion("yellowTile");
            highlightedTextureRegion = Assets.gameAtlas.findRegion("yellowTileHighlighted");
        }
    }

    /**
     * Draws a tile or highlighted tile to the screen
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (isHighlighted) {
            batch.draw(highlightedTextureRegion, getX(), getY(),(ChessGame.VWIDTH/8), (ChessGame.VWIDTH/8));
        } else {
            batch.draw(textureRegion, getX(), getY(), (ChessGame.VWIDTH/8), (ChessGame.VWIDTH/8));
        }
    }
}
