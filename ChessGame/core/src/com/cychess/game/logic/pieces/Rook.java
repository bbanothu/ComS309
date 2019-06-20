package com.cychess.game.logic.pieces;

import com.cychess.game.logic.Piece;

/**
 * Creates a Rook piece
 * Created by PJ on 3/26/2018.
 */
public class Rook extends Piece {

    /**
     * Creates a new Rook by using a call the extended Piece class.
     * @param x
     * @param y
     * @param isWhite
     */
    public Rook(int x, int y, boolean isWhite) {
        super(x, y, isWhite, isWhite ? "wRook" : "bRook");
    }
}
