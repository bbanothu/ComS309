package com.cychess.game.logic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.cychess.game.Assets;
import com.cychess.game.ChessGame;

/**
 * Used by classes in the pieces package to create an Actor to visually see on screen
 * Created by Peter on 3/24/2018.
 */
public class Piece extends Actor {
    /**
     * If a piece is white or not.
     */
    public boolean isWhite;

    /**
     * X coordinate of piece.
     */
    public int x;

    /**
     * Y coordinate of piece.
     */
    public int y;

    /**
     * Name of piece.
     */
    public String name;

    /**
     * Texture for piece.
     */
    private final TextureRegion textureRegion;

    /**
     * Called by all classes inside pieces package. Creates a new piece given
     * the input parameters and sets the bounds for this actor.
     * @param x
     * @param y
     * @param isWhite
     * @param regionName
     */
    public Piece(int x, int y, boolean isWhite, String regionName) {
        setBounds(x*(ChessGame.VWIDTH/8), y*(ChessGame.VWIDTH/8), (ChessGame.VWIDTH/8), (ChessGame.VWIDTH/8));
        this.isWhite = isWhite;
        this.textureRegion = Assets.gameAtlas.findRegion(regionName);
        this.name = regionName;
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the piece to the screen
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(this.textureRegion, this.getX(), this.getY(), (ChessGame.VWIDTH/8), (ChessGame.VWIDTH/8));
    }
}
