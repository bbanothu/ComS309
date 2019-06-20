package com.cychess.game.logic.pieces;

import com.cychess.game.logic.Piece;

/**
 * Creates a Bishop piece.
 * Created by PJ on 3/26/2018.
 */
public class Bishop extends Piece {

    /**
     * Creates a new Bishop by using a call the extended Piece class.
     * @param x
     * @param y
     * @param isWhite
     */
    public Bishop(int x, int y, boolean isWhite) {
        super(x, y, isWhite, isWhite ? "wBishop" : "bBishop");
    }
}
