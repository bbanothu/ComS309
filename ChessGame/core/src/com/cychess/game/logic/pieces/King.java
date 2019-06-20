package com.cychess.game.logic.pieces;

import com.cychess.game.logic.Piece;

/**
 * Creates a King piece
 * Created by PJ on 3/26/2018.
 */
public class King extends Piece {

    /**
     * Creates a new King by using a call the extended Piece class.
     * @param x
     * @param y
     * @param isWhite
     */
    public King(int x, int y, boolean isWhite) {
        super(x, y, isWhite, isWhite ? "wKing" : "bKing");    }
}
