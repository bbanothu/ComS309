package com.cychess.game.logic.pieces;

import com.cychess.game.logic.Piece;

/**
 * Creates a Pawn piece
 * Created by PJ on 3/26/2018.
 */
public class Pawn extends Piece {

    /**
     * Creates a new Pawn by using a call the extended Piece class.
     * @param x
     * @param y
     * @param isWhite
     */
    public Pawn(int x, int y, boolean isWhite) {
        super(x, y, isWhite, isWhite ? "wPawn" : "bPawn");
    }
}
