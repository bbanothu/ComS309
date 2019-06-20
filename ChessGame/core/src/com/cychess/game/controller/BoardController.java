package com.cychess.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;
import com.cychess.game.ChessGame;
import com.cychess.game.logic.ChessBoard;
import com.cychess.game.logic.Piece;
import com.cychess.game.logic.Tile;

/**
 * Handles the input from the user when they tap on a piece or tile on the screen.
 * Moves pieces and selects a piece for highlighting. Sends information to the
 * server about which piece to select or move.
 * Created by PJ on 3/26/2018.
 */
public class BoardController extends ActorGestureListener {
    /**
     * The chessboard that holds the tiles which holds the pieces for a chess game.
     */
    private static ChessBoard board;

    /**
     * An array of tiles that will visually show on screen which moves are valid.
     */
    private static Array<Tile> highlightedTiles = new Array<Tile>();

    public static boolean validMove;

    /**
     * Valid move array. 0 = non valid move, 1, 2, or 3 = valid moves.
     */
    public static int validMoves[][];

    /**
     * Uses the chess tile board to detect which piece is being tapped on to
     * calculate valid moves, move a piece, or capture another piece.
     *
     * @param board The chessboard of the current game being played.
     */
    public BoardController(ChessBoard board) {
        this.board = board;
        validMoves = new int[8][8];
    }

    /**
     * When the screen, but more specifically the chessboard, is tapped this
     * method is called to determine which piece was selected or where to move
     * the selected piece.
     * @param e InputEvent
     * @param x X location tapped on screen
     * @param y Y location tapped on screen
     * @param count count
     * @param button button
     */
    @Override
    public void tap(InputEvent e, float x, float y, int count, int button) {
        Actor target = e.getTarget();
        int targetX = (int) target.getX();
        int targetY = (int) target.getY();

        //Gdx.app.log("Target", "Y: " + (targetY/8)/8 + " X: " + (targetX/8)/8);

        if( target.getClass().getSuperclass().equals(Piece.class) ){
            Piece piece = (Piece) target;

            //TODO redo x value calculation to make board render/play properly
            //TODO highlighting of movable spaces
            if( ChessGame.isWhite && piece.isWhite && board.round % 2 == 0){
                board.selectedPiece = piece;
                ChessGame.client.sendMove(piece.x, Math.abs(piece.y - 7));
                //selectPiece(piece, true);

                //ChessGame.client.output.println("MOVE " + piece.x + Math.abs(piece.y - 7));
                Gdx.app.log("Selected Piece", piece.name + " Y: " + Math.abs(piece.y - 7) + " X: " + piece.x);
            }else if( !ChessGame.isWhite && !piece.isWhite && board.round % 2 == 1){
                board.selectedPiece = piece;
                ChessGame.client.sendMove(Math.abs(piece.x-7), piece.y);
                //selectPiece(piece, false);

                Gdx.app.log("Selected Piece", piece.name + " Y: " + piece.y + " X: " + piece.x);
                //ChessGame.client.output.println("MOVE " + piece.x + piece.y);
            }else{
                if( ChessGame.isWhite ){
                    ChessGame.client.sendMove(targetX/8/8, Math.abs((targetY/8/8)-7));
                    Gdx.app.log("Target after MOVE", "Y: " + Math.abs((targetY/8/8)-7) + " X: " + (targetX/8)/8);
                    //ChessGame.client.output.println("MOVE " + (targetX/8)/8 + Math.abs((targetY/8/8)-7));
                }else{
                    ChessGame.client.sendMove(Math.abs(targetX/8/8 - 7), targetY/8/8);
                    //ChessGame.client.output.println("MOVE " + (targetX/8)/8 + targetY/8/8);
                    Gdx.app.log("Target after MOVE", "Y: " + (targetY/8)/8 + " X: " + (targetX/8)/8);
                }
                ChessGame.client.targetX = targetX/8/8;
                ChessGame.client.targetY = targetY/8/8;
            }
        }else{
            if( ChessGame.isWhite ){
                ChessGame.client.sendMove(targetX/8/8, Math.abs((targetY/8/8)-7));
                //ChessGame.client.output.println("MOVE " + (targetX/8)/8 + Math.abs((targetY/8/8)-7));
                Gdx.app.log("Target after MOVE", "Y: " + Math.abs((targetY/8/8)-7) + " X: " + (targetX/8)/8);
            }else{
                ChessGame.client.sendMove(Math.abs(targetX/8/8 - 7), targetY/8/8);
                //ChessGame.client.output.println("MOVE " + (targetX/8)/8 + targetY/8/8);
                Gdx.app.log("Target after MOVE", "Y: " + (targetY/8)/8 + " X: " + (targetX/8)/8);
            }
            ChessGame.client.targetX = targetX/8/8;
            ChessGame.client.targetY = targetY/8/8;
        }
    }

    /**
     * Moves the selected piece to the new x and y coordinates.
     * @param piece The selected piece to move
     * @param x New x position to move the piece to
     * @param y New y position to move the piece to
     */
    public static void movePiece(Piece piece, int x, int y) {
        if( (piece == null) /*|| !board.getTileAt(x, y).isHighlighted*/ ){
            return;
        }

        int oldX = piece.x;
        int oldY = piece.y;

        removeHighlights();

        //Capturing a piece
        if(board.getPieceAt(x, y) != null){
            board.removePieceAt(x, y);
        }

        //Moving a piece
        board.relocatePiece(oldX, oldY, x, y);

        board.selectedPiece = null;
        board.round++;
    }

    /**
     * Selects a given piece to show the possible valid moves it can make.
     *
     * @param piece The selected piece to display valid moves for
     * @param isWhite If the piece is white or not
     */
    private void selectPiece(Piece piece, boolean isWhite) {
        if( isWhite ){
            removeHighlights();
            board.selectedPiece = piece;
            addMoveWhiteHighlights(piece);
        }else{
            removeHighlights();
            board.selectedPiece = piece;
            addMoveBlackHighlights(piece);
        }
    }

    /**
     * Finds the tiles that a valid moves for a given white piece and adds them
     * to the highlightedTiles array.
     * @param piece Piece to add highlights to
     */
    private void addMoveWhiteHighlights(Piece piece){
        Tile[][] tiles = board.tiles;

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                int x = (int) tiles[i][j].getX();
                int y = (int) tiles[i][j].getY();
                if( validMoves[Math.abs(y-7)][x] == 1 || validMoves[Math.abs(y-7)][x] == 3 ){
                    this.highlightedTiles.add(tiles[i][j]);
                    tiles[i][j].isHighlighted = true;
                }
            }

        }
    }

    /**
     * Finds the tiles that a valid moves for a given black piece and adds them
     * to the highlightedTiles array.
     * @param piece Piece to add highlights to
     */
    private void addMoveBlackHighlights(Piece piece){

    }

    /**
     * Remove the highlights from the highlightedTiles array.
     */
    private static void removeHighlights() {
        while (highlightedTiles.size > 0) {
            highlightedTiles.pop().isHighlighted = false;
        }
    }
}
