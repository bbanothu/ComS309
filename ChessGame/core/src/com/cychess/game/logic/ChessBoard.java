package com.cychess.game.logic;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.cychess.game.ChessGame;
import com.cychess.game.controller.BoardController;
import com.cychess.game.logic.pieces.Bishop;
import com.cychess.game.logic.pieces.King;
import com.cychess.game.logic.pieces.Knight;
import com.cychess.game.logic.pieces.Pawn;
import com.cychess.game.logic.pieces.Queen;
import com.cychess.game.logic.pieces.Rook;

/**
 * Creates the chessboard table. Handles all the logic for adding tiles
 * and pieces to the chessboard. Can relocate or remove a piece.
 * Created by Peter on 3/24/2018.
 */
public class ChessBoard extends Table {

    /**
     * The piece selected by the user.
     */
    public static Piece selectedPiece;

    /**
     * The current round.
     */
    public static int round;

    /**
     * The array of tiles for the chessboard.
     */
    public static Tile[][] tiles = new Tile[8][8];

    /**
     * The array of pieces for the chessboard.
     */
    public static Piece[][] pieces = new Piece[8][8];


    private King wKing;
    private King bKing;

    /**
     * Returns the tile at a given x and y location.
     * @param x
     * @param y
     * @return
     */
    public static Tile getTileAt(int x, int y){
        return tiles[x][y];
    }

    /**
     * Returns the piece at a given x and y location.
     * @param x
     * @param y
     * @return
     */
    public static Piece getPieceAt(int x, int y){
        return pieces[x][y];
    }

    /**
     * Creates the chessboard and populates it with tiles and adds them as
     * actors to the stage. Sets the bounds for the table and creates a
     * new listener which is the BoardController.
     */
    public ChessBoard() {
        setBounds(0, ChessGame.SCREEN_Y, ChessGame.VWIDTH, ChessGame.VWIDTH);
        addListener(new BoardController(this));
        //setDebug(true);

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                this.tiles[i][j] = new Tile(i, j, ((i + j) % 2) == 0);
                this.addActor(this.tiles[i][j]);
            }
        }
    }

    /**
     * Initializes the pieces of the board based on if the player
     * is black or white.
     * @param isWhite
     */
    public void initPieces(boolean isWhite) {
        //Adds the pawns to the screen
        for (int i = 0; i < 8; i++) {
            addPiece(new Pawn(i, 1, isWhite ? true : false));
            addPiece(new Pawn(i, 6, isWhite ? false : true));
        }

        //Adds the rooks
        addPiece(new Rook(0, 0, isWhite ? true : false));
        addPiece(new Rook(7, 0, isWhite ? true : false));
        addPiece(new Rook(0, 7, isWhite ? false : true));
        addPiece(new Rook(7, 7, isWhite ? false : true));

        //Adds the knights
        addPiece(new Knight(1, 0, isWhite ? true : false));
        addPiece(new Knight(6, 0, isWhite ? true : false));
        addPiece(new Knight(1, 7, isWhite ? false : true));
        addPiece(new Knight(6, 7, isWhite ? false : true));

        //Adds the bishops
        addPiece(new Bishop(2, 0, isWhite ? true : false));
        addPiece(new Bishop(5, 0, isWhite ? true : false));
        addPiece(new Bishop(2, 7, isWhite ? false : true));
        addPiece(new Bishop(5, 7, isWhite ? false : true));

        //Adds the queens
        addPiece(new Queen(4, 0, isWhite ? true : false));
        addPiece(new Queen(4, 7, isWhite ? false : true));

        //Adds and sets the kings
        if( isWhite ){
            wKing = new King(3, 0, true);
            bKing = new King(3, 7, false);
        }else{
            wKing = new King(3, 7, true);
            bKing = new King(3, 0, false);
        }
        addPiece(wKing);
        addPiece(bKing);
    }

    /**
     * Relocates a piece.
     * @param oldX
     * @param oldY
     * @param x
     * @param y
     */
    public void relocatePiece(int oldX, int oldY, int x, int y) {
        Piece piece = pieces[oldX][oldY];

        pieces[x][y] = piece;
        pieces[oldX][oldY] = null;
        piece.x = x;
        piece.y = y;
        piece.setX(x*(ChessGame.VWIDTH/8));
        piece.setY(y*(ChessGame.VWIDTH/8));
    }

    /**
     * Removes at piece at a given location.
     * @param x
     * @param y
     */
    public void removePieceAt(int x, int y) {
        Piece piece = pieces[x][y];

        if(piece != null){
            piece.remove();
            pieces[x][y] = null;
        }
    }

    /**
     * Adds a new piece to the pieces array and adds it as
     * an actor to the stage.
     * @param piece
     */
    public void addPiece(Piece piece) {
        this.addActor(piece);
        this.pieces[piece.x][piece.y] = piece;
    }

    /**
     * Loads a board using an array/string from the server.
     * If string is null/empty make a new initial board
     *
     */
    public void loadBoard() {
        //TODO call server and return a string for the board
    }
}
