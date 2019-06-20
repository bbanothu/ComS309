package com.cychess.game.logic.pieces;

import com.cychess.game.logic.Piece;

/**
 * Creates a Knight piece
 * Created by PJ on 3/26/2018.
 */
public class Knight extends Piece {

    /**
     * Creates a new Knight by using a call the extended Piece class.
     * @param x
     * @param y
     * @param isWhite
     */
    public Knight(int x, int y, boolean isWhite) {
        super(x, y, isWhite, isWhite ? "wKnight" : "bKnight");
    }
}
