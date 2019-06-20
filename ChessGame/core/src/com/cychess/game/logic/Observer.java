package com.cychess.game.logic;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.cychess.game.ChessGame;
import com.cychess.game.logic.pieces.Bishop;
import com.cychess.game.logic.pieces.King;
import com.cychess.game.logic.pieces.Knight;
import com.cychess.game.logic.pieces.Pawn;
import com.cychess.game.logic.pieces.Queen;
import com.cychess.game.logic.pieces.Rook;

import java.util.Scanner;

/**
 * Created by PJ on 4/24/2018.
 */

public class Observer extends Table {

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

    /**
     * The array for the gameboard which will be used to convert pieces
     */
    public static int[][] gameboard = new int[8][8];

    /**
     * Holds the string values of the board
     */
    public static String newBoard;

    public Observer() {
        setBounds(0, ChessGame.SCREEN_Y, ChessGame.VWIDTH, ChessGame.VWIDTH);

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                this.tiles[i][j] = new Tile(i, j, ((i + j) % 2) == 0);
                this.addActor(this.tiles[i][j]);
            }
        }
    }

    public static void parseBoard(String board) {
        int index = board.indexOf('[');
        board = board.substring(index+1, board.length()-1);
        newBoard = board;
        //System.out.println("Old Board: " + oldBoard);
        //System.out.println("New Board: " + newBoard);
        gameboard = new int[8][8];

        Scanner scanner = new Scanner(board);
        scanner.useDelimiter(", ");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (scanner.hasNext()) {
                    String in = scanner.next();
                    gameboard[i][j] = Integer.parseInt(in);
                }
            }
        }
        scanner.close();
        System.out.println("Board parsed!");
    }

    public void updateBoard() {
        System.out.println("INIT PIECES");

        reset();

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                this.tiles[i][j] = new Tile(i, j, ((i + j) % 2) == 0);
                this.addActor(this.tiles[i][j]);
            }
        }

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(gameboard[i][j] == 0){
                    addPiece(new Rook(j, Math.abs(i - 7), false));
                }else if(gameboard[i][j] == 1){
                    addPiece(new Knight(j, Math.abs(i - 7), false));
                }else if(gameboard[i][j] == 2){
                    addPiece(new Bishop(j, Math.abs(i - 7), false));
                }else if(gameboard[i][j] == 3){
                    addPiece(new Queen(j, Math.abs(i - 7), false));
                }else if(gameboard[i][j] == 4){
                    addPiece(new King(j, Math.abs(i - 7), false));
                }else if(gameboard[i][j] == 5){
                    addPiece(new Pawn(j, Math.abs(i - 7), false));
                }else if(gameboard[i][j] == 6){
                    //removePieceAt(j, Math.abs(i - 7));
                }else if(gameboard[i][j] == 7){
                    addPiece(new Rook(j, Math.abs(i - 7), true));
                }else if(gameboard[i][j] == 8){
                    addPiece(new Knight(j, Math.abs(i - 7), true));
                }else if(gameboard[i][j] == 9){
                    addPiece(new Bishop(j, Math.abs(i - 7), true));
                }else if(gameboard[i][j] == 10){
                    addPiece(new Queen(j, Math.abs(i - 7), true));
                }else if(gameboard[i][j] == 11){
                    addPiece(new King(j, Math.abs(i - 7), true));
                }else if(gameboard[i][j] == 12){
                    addPiece(new Pawn(j, Math.abs(i - 7), true));
                }
            }
        }
    }

//    public static void updateBoard(){
//        System.out.println("Update board!");
//
//        for(int i = 0; i < 8; i++){
//            for(int j = 0; j < 8; j++){
//                if(gameboard[i][j] == 0){
//                    new Rook(j, Math.abs(i - 7), false);
//                }else if(gameboard[i][j] == 1){
//                    addPiece(new Knight(j, Math.abs(i - 7), false));
//                }else if(gameboard[i][j] == 2){
//                    addPiece(new Bishop(j, Math.abs(i - 7), false));
//                }else if(gameboard[i][j] == 3){
//                    addPiece(new Queen(j, Math.abs(i - 7), false));
//                }else if(gameboard[i][j] == 4){
//                    addPiece(new King(j, Math.abs(i - 7), false));
//                }else if(gameboard[i][j] == 5){
//                    addPiece(new Pawn(j, Math.abs(i - 7), false));
//                }else if(gameboard[i][j] == 7){
//                    addPiece(new Rook(j, Math.abs(i - 7), true));
//                }else if(gameboard[i][j] == 8){
//                    addPiece(new Knight(j, Math.abs(i - 7), true));
//                }else if(gameboard[i][j] == 9){
//                    addPiece(new Bishop(j, Math.abs(i - 7), true));
//                }else if(gameboard[i][j] == 10){
//                    addPiece(new Queen(j, Math.abs(i - 7), true));
//                }else if(gameboard[i][j] == 11){
//                    addPiece(new King(j, Math.abs(i - 7), true));
//                }else if(gameboard[i][j] == 12){
//                    addPiece(new Pawn(j, Math.abs(i - 7), true));
//                }
//            }
//        }
//    }

    /**
     * Adds a new piece to the pieces array and adds it as
     * an actor to the stage.
     * @param piece
     */
    public void addPiece(Piece piece) {
        this.addActor(piece);
        this.pieces[piece.x][piece.y] = piece;
    }
}
