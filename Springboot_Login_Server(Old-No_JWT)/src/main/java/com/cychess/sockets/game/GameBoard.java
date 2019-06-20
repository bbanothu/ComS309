package com.cychess.sockets.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.cychess.model.UserDAO;
import com.cychess.sockets.game.pieces.Bishop;
import com.cychess.sockets.game.pieces.King;
import com.cychess.sockets.game.pieces.Knight;
import com.cychess.sockets.game.pieces.Pawn;
import com.cychess.sockets.game.pieces.Queen;
import com.cychess.sockets.game.pieces.Rook;

/**
 * Contains the movement and recording logic for the chess board used in a game.
 * @author bbanothu
 */
public class GameBoard {

	public boolean Castle = false;
	public String castle_pos = "";
	public Player currentPlayer;
	
	public boolean Over;
	public boolean whiteWinner;

	public Pieces chessBoard[][];
	public boolean whiteMove;
	public int move[][];

	public boolean click1 = false;
	public boolean click2 = false;

	public int currentX;
	public int currentY;

	public GameBoard() {
		initGameBoard();
	}

	// TODO
	/**
	 * Populates the chessBoard 2D array with the black and white chess pieces in
	 * their appropriate locations.
	 */
	public void initGameBoard() {
		/*
		 * 0 = BLACK ROOK, 1 = BLACK KNIGHT, 2 = BLACK BISHOP, 3 = BLACK QUEEN, 4 =
		 * BLACK KING, 5 = BLACK PAWN 6 = OPEN 7 = WHITE ROOK, 8 = WHITE KNIGHT, 9 =
		 * WHITE BISHOP, 10 = WHITE QUEEN, 11 = WHITE KING, 12 = WHITE PAWN
		 */
		move = new int[8][8];
		int intChessBoard[][] = new int[][] { { 0, 1, 2, 3, 4, 2, 1, 0 }, { 5, 5, 5, 5, 5, 5, 5, 5 },
				{ 6, 6, 6, 6, 6, 6, 6, 6 }, { 6, 6, 6, 6, 6, 6, 6, 6 }, { 6, 6, 6, 6, 6, 6, 6, 6 },
				{ 6, 6, 6, 6, 6, 6, 6, 6 }, { 12, 12, 12, 12, 12, 12, 12, 12 }, { 7, 8, 9, 10, 11, 9, 8, 7 } };
		chessBoard = new Pieces[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				switch (intChessBoard[i][j]) {
				case 0:
					chessBoard[i][j] = new Rook(i, j, false);
					break;
				case 1:
					chessBoard[i][j] = new Knight(i, j, false);
					break;
				case 2:
					chessBoard[i][j] = new Bishop(i, j, false);
					break;
				case 3:
					chessBoard[i][j] = new Queen(i, j, false);
					break;
				case 4:
					chessBoard[i][j] = new King(i, j, false);
					break;
				case 5:
					chessBoard[i][j] = new Pawn(i, j, false);
					break;
				case 6:
					chessBoard[i][j] = null;
					break;
				case 7:
					chessBoard[i][j] = new Rook(i, j, true);
					break;
				case 8:
					chessBoard[i][j] = new Knight(i, j, true);
					break;
				case 9:
					chessBoard[i][j] = new Bishop(i, j, true);
					break;
				case 10:
					chessBoard[i][j] = new Queen(i, j, true);
					break;
				case 11:
					chessBoard[i][j] = new King(i, j, true);
					break;
				case 12:
					chessBoard[i][j] = new Pawn(i, j, true);
					break;
				}
			}
		}
		clearMoves();
		whiteMove = true;
	}

	/**
	 * Clears the moves
	 */
	public void clearMoves() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				move[i][j] = 0;
			}
		}
	}
	
	public void check_check(GameBoard gameboard) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (chessBoard[i][j] != null) {
					if(whiteMove) {
						if(chessBoard[i][j].toString() == "WKK") {
							
							chessBoard[i][j].checking(this);
						}
					}else {
						if(chessBoard[i][j].toString() == "BKK") {
							chessBoard[i][j].checking(this);
						}
						
					}
					
				}
			}
		}
		
	}
	
	/**
	 * 
	 * @param locationy desired Y location
	 * @param locationx desired X location
	 * @param player	The player trying to make a move
	 * @return	Whether or not the desired location is a legal move for the player to make
	 */
	public synchronized boolean legalMove(int locationy, int locationx, Player player) {
		if (player == currentPlayer) {
			currentPlayer = currentPlayer.opponent;
			currentPlayer.otherPlayerMoved(locationy, locationx, currentY, currentX);
            return true;
		}
        return false;

	}

	/**
	 * Moves the selected piece
	 * @param y	Future Y location
	 * @param x	Future X location
	 */
	public void movePiece(int y, int x) {
		if (click1 == true) {
			if (move[y][x] == 1 || move[y][x] == 2 || move[y][x] == 3 || move[y][x] == 4 ) {
				onClick2(y, x, currentY, currentX);
				currentPlayer.output.println("VALID_MOVE");
				if(Castle) {
					Castle = false;
				}else {
					legalMove(y, x, currentPlayer);
					Castle = false;
				}
				System.out.print(y + "," + x + " new position ");
				System.out.println(currentY + "," + currentX + " old position");
				clearMoves();
				click1 = false;
				click2 = false;
				currentX = 0;
				currentY = 0;
				int id =  currentPlayer.gameBoard_id;
				new UserDAO().uploadTurn(print_toString(),id );
				check_check(this);
				return;
			} else {
				currentPlayer.output.println("MESSAGE");
				clearMoves();
				click1 = false;
				click2 = false;
				currentX = 0;
				currentY = 0;
				return;
			}
		}
		if (click1 == false) {
			onClick1(y, x);
			currentX = x;
			currentY = y;
		}
	}
	
	/**
	 * Returns a list of legal moves 
	 */
	public void validMoves() {
		String validMoves = "VALID";
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				validMoves += move[i][j];
			}
			validMoves += "|";
		}
	}

	/**
	 * The user's selected piece to be moved.
	 * @param y	Y location of the piece
	 * @param x X location of the piece
	 */
	public void onClick1(int y, int x) {
		if (((x >= 0 && x < 8) && (y >= 0 && y < 8))) {
			if (chessBoard[y][x] == null) {
				return;
				
			}else if(  (chessBoard[y][x].isWhite() && !whiteMove) ||(!chessBoard[y][x].isWhite() && whiteMove) ) {
					return;
			}
			if(chessBoard[y][x].onClick(this)) {
				click1 = true;
			}
		}
	}

	/**
	 * Updates the board with the selected piece's movement
	 * @param y Initial Y location
	 * @param x Initial X location
	 * @param yPiece Future Y location
	 * @param xPiece Future X location
	 */
	public void onClick2(int y, int x, int yPiece, int xPiece) {
		if (((x >= 0 && x < 8) && (y >= 0 && y < 8))) {
				chessBoard[yPiece][xPiece].move(y, x, this);

		}
		clearMoves();
		click1 = false;
		click2 = false;
	}

	/**
	 * Prints the board to the console for debugging purposes
	 */
	public void print() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (chessBoard[i][j] != null) {
					if (move[i][j] == 2) {
						System.out.print("2\t");
					} else {
						System.out.print(chessBoard[i][j].toString() + "\t");
					}
				} else {
					if (move[i][j] == 1 || move[i][j] == 3 || move[i][j] == 4) {
						System.out.print("1\t");

					} else if (move[i][j] == 2) {
						System.out.print("2\t");
					} else {
						System.out.print("\t");
					}
				}
				System.out.print("|");
			}
			System.out.println();
		}
	}
	
	public int[] print_toString() {
		int[] game_String = new int[64];
		int k = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (chessBoard[i][j] != null) {
						game_String[k] = chessBoard[i][j].toString_number();
						k++;

					}else {
						game_String[k] = 6;
						k++;
					}

			}
		}
		return game_String;

	}



}
