package com.cychess.game.logic.pieces;

import com.cychess.game.logic.Piece;

/**
 * Creates a Queen piece
 * Created by PJ on 3/26/2018.
 */
public class Queen extends Piece {

    /**
     * Creates a new Queen by using a call the extended Piece class.
     * @param x
     * @param y
     * @param isWhite
     */
    public Queen(int x, int y, boolean isWhite) {
        super(x, y, isWhite, isWhite ? "wQueen" : "bQueen");
    }
}
