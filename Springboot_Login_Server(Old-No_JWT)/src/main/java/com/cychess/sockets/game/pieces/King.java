package com.cychess.sockets.game.pieces;

import com.cychess.sockets.game.GameBoard;
import com.cychess.sockets.game.Pieces;
/**
 * Created by bbanothu on 1/19/2018.
 */

public class King extends Pieces {

	public String color;
	public String type = "king";
	
	public boolean isSelected;
	public boolean isWhite;
    public boolean checking;
	
	public int y;
	public int x;
	
	public int inDanger[][];

	public boolean firstMove;
	public boolean castle;
	public boolean check;
	public boolean checkMate;

    /**
     * Initializes Y and X values and sets its color
     * @param y			Initial Y val
     * @param x			Initial X val
     * @param isWhite	Set color
     */
	public King(int y, int x, boolean isWhite) {
		this.y = y;
		this.x = x;
		this.isWhite = isWhite;
		this.firstMove = true;
		if (isWhite) {
			this.color = "white";
		} else {
			this.color = "black";
		}
		inDanger = new int[8][8];
	}

    /**
     * Sets the interval x and y values to the new position;
     * Also Changes position on gameBoard
     * @param y				Initial Y val
     * @param x				Initial X val
     * @param gameBoard		Current Gameboard
     */
	public void move(int y, int x, GameBoard gameBoard) {
		int oldX = this.x;
		int oldY = this.y;
		
		if(gameBoard.move[y][x] == 3) {
			Rook temp_rook = (Rook) gameBoard.chessBoard[y][x+1];
			gameBoard.chessBoard[y][x+1] = null;
			gameBoard.chessBoard[oldY][oldX] = null;
			gameBoard.chessBoard[y][x] = this;
			gameBoard.chessBoard[oldY][oldX+1] = temp_rook;
			gameBoard.Castle = true;
			gameBoard.currentPlayer = gameBoard.currentPlayer.opponent;
			gameBoard.currentPlayer.otherPlayerMoved_castle( oldX,  oldY,  y,  x,  x+1,  y,  oldX+1,  oldY);
		}else if(gameBoard.move[y][x] == 4) {
			Rook temp_rook = (Rook) gameBoard.chessBoard[y][x-1];
			gameBoard.chessBoard[y][x-1] = null;
			gameBoard.chessBoard[oldY][oldX] = null;
			gameBoard.chessBoard[y][x] = this;
			gameBoard.chessBoard[oldY][oldX-2] = temp_rook;
			gameBoard.Castle = true;
			gameBoard.currentPlayer = gameBoard.currentPlayer.opponent;
			gameBoard.currentPlayer.otherPlayerMoved_castle( oldX,  oldY,  y,  x,  x+1,  y,  oldX+1,  oldY);
		}else {
	        gameBoard.chessBoard[y][x] = this;
	        gameBoard.chessBoard[oldY][oldX] = null;
		}
 


		this.y = y;
		this.x = x;
    	if(gameBoard.whiteMove) {
    		gameBoard.whiteMove = false;
    	}else {
    		gameBoard.whiteMove = true;
    	}
		gameBoard.clearMoves();
		this.firstMove = false;
	}
	
	public void checkCastle(GameBoard gameBoard) {

		if(isWhite) {
			
			if(gameBoard.chessBoard[this.y][this.x+1] == null && (inDanger[this.y][this.x+1] != 1 )){
				if(gameBoard.chessBoard[this.y][this.x+2] == null && (inDanger[this.y][this.x+2] != 1 )){
					if(gameBoard.chessBoard[this.y][this.x+3] != null && (inDanger[this.y][this.x+3] != 1 )) {
						if(gameBoard.chessBoard[this.y][this.x+3].toString() == "WR") {
							gameBoard.move[this.y][this.x+2] = 3;
						}
					}
				}
			}
				
			 if(gameBoard.chessBoard[this.y][this.x-1] == null&& (inDanger[this.y][this.x-1] != 1 )){
				if(gameBoard.chessBoard[this.y][this.x-2] == null&& (inDanger[this.y][this.x-2] != 1 )){
					if(gameBoard.chessBoard[this.y][this.x-3] == null && (inDanger[this.y][this.x-3] != 1 )) {
						if(gameBoard.chessBoard[this.y][this.x-4] != null && (inDanger[this.y][this.x-4] != 1  )) {
						}
						if(gameBoard.chessBoard[this.y][this.x-4].toString() == "WR") {
							gameBoard.move[this.y][this.x-3] = 4;
						}
					}
				}
			}
		}else {
			if(gameBoard.chessBoard[this.y][this.x+1] == null&& (inDanger[this.y][this.x+1] != 1 )){
				if(gameBoard.chessBoard[this.y][this.x+2] == null&& (inDanger[this.y][this.x+2] != 1 )){
					if(gameBoard.chessBoard[this.y][this.x+3] != null&& (inDanger[this.y][this.x+3] != 1 )){
						if(gameBoard.chessBoard[this.y][this.x+3].toString() == "BR") {
							gameBoard.move[this.y][this.x+2] = 3;
						}
					}
				}
			}
				
			 if(gameBoard.chessBoard[this.y][this.x-1] == null&& (inDanger[this.y][this.x-1] != 1 )){
				if(gameBoard.chessBoard[this.y][this.x-2] == null&& (inDanger[this.y][this.x-2] != 1 )){
					if(gameBoard.chessBoard[this.y][this.x-3] == null&& (inDanger[this.y][this.x-3] != 1 )) {
						if(gameBoard.chessBoard[this.y][this.x-4] != null&& (inDanger[this.y][this.x-4] != 1  )) {
						}
						if(gameBoard.chessBoard[this.y][this.x-4].toString() == "BR") {
							gameBoard.move[this.y][this.x-3] = 4;
						}
					}
				}
			}
			
		}
		
	}

    /**
     * populates gameBoard.move with ones after checking for valid moves
     * @param gameBoard		Current gameboard
     * @return 				Whether or not a valid move was selected
     */
	public boolean moveShower(GameBoard gameBoard) {
		clearDanger();
		gameBoard.clearMoves();
		checkDanger(gameBoard);

		int x = this.x;
		int y = this.y;
		boolean moveFound = false;
		
		if(firstMove) {
			checkCastle(gameBoard);
		}
		
		if(inDanger[this.y][this.x] == 1) {
			check = true;
			System.out.println(check);
		}else {
			System.out.println("no check");
			check = false;
		}


		int posy[] = { x - 1, x, x + 1, x - 1, x + 1, x - 1, x, x + 1 };
		int posx[] = { y - 1, y - 1, y - 1, y, y, y + 1, y + 1, y + 1 };
		for (int i = 0; i < 8; i++) {
			if ((posx[i] >= 0 && posx[i] < 8 && posy[i] >= 0 && posy[i] < 8)) {
				if (inDanger[posx[i]][posy[i]] != 1 ) {
					if(gameBoard.chessBoard[posx[i]][posy[i]] != null) {
						if(gameBoard.chessBoard[posx[i]][posy[i]].color() != this.color) {
							gameBoard.move[posx[i]][posy[i]] = 2;
							moveFound = true;
						}
					}else {
						gameBoard.move[posx[i]][posy[i]] = 1;
						moveFound = true;
					}
				}
			}
		}
		
		if (check) {
			if(!moveFound) {;
				in_check(gameBoard);
				System.out.println("game over");
				if(this.isWhite) {
					gameBoard.whiteWinner = true;
				}else {
					gameBoard.whiteWinner = false;
				}
			}
			
		}
/*		if(check) {
			if(moveFound == false)
				checkMate = true;
			
		}*/
		
		
		return moveFound;

	}
	
	public void in_check( GameBoard gameBoard) {
			if(isWhite) {
				gameBoard.Over = true;
				gameBoard.whiteWinner = true;
			}else {
				gameBoard.Over = true;
				gameBoard.whiteWinner = false;
			}
		
	}

	/**
	 * Checks for whether or not the king is in check
	 * @param gameBoard	The current gameboard
	 */
	public void checkDanger(GameBoard gameBoard) {

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (gameBoard.chessBoard[i][j] != null) {
					if (gameBoard.chessBoard[i][j].color() != this.color) {
						if (gameBoard.chessBoard[i][j].type() != this.type)
							if(gameBoard.chessBoard[i][j].type() == "pawn"){
								((Pawn) gameBoard.chessBoard[i][j]).attack(gameBoard);
						}else {
							gameBoard.chessBoard[i][j].danger_Shower(gameBoard);
						}
					}
				}
			}
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (gameBoard.move[i][j] == 1 || gameBoard.move[i][j] == 2) {
					inDanger[i][j] = 1;
				}
			}
		}
		
		if(gameBoard.move[this.y][this.x] == 1 || gameBoard.move[this.y][this.x] == 2){
			//check = true;
			//System.out.println("in CHECK");
		}else {
			//check = false;
		}
		
		gameBoard.clearMoves();

	}

	/**
	 * Shows where the king can move to get out of check
	 */
	public void clearDanger() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				inDanger[i][j] = 0;
				
				//TODO 	can this be used to determine if there is checkmate?
			}
		}
	}

    /**
     * Checks to make it is whites move and its a white piece
     * or that its a black move and a black piece
     * @param gameBoard		Current gameboard
     * @return 				Whether or not the game is in sync with the board
     */
    public boolean onClick(GameBoard gameBoard)
    {
        if(isWhite && gameBoard.whiteMove){
     	   return  moveShower(gameBoard);
         }else if(!isWhite && !gameBoard.whiteMove){
         	return  moveShower(gameBoard);
         }
 	return false;
    }

    /**
     * Return color of piece
     * @return	color of piece
     */
    public boolean isWhite(){

        return isWhite;
    }

    /**
     * Return color in string
     * @return 	Color of piece in string
     */
    public String color(){

        return color;
    }

    /**
     * return piece type
     * @return 	type of piece
     */
    public  String type() {
    	return type;
    }
    
    /**
     * if selected
     * @return 	Whether or not is it selected
     */
    public  boolean isSelected(){

        return isSelected;
    }

    /**
     * Returns what color piece this is
     * @return 	What color and type the piece is
     */
    @Override
    public String toString() {
        if(isWhite){
            return "WKK";
        }
        return "BKK";
    }

	@Override
	public void checking(GameBoard gameBoard) {
		checkDanger( gameBoard);
	}

	@Override
	public int toString_number() {
        if(isWhite){
            return 11;
        }
        return 4;
	}

	@Override
	public void danger_Shower(GameBoard gameBoard) {
		clearDanger();
		gameBoard.clearMoves();
		checkDanger(gameBoard);

		int x = this.x;
		int y = this.y;
		boolean moveFound = false;
		
		if(firstMove) {
			checkCastle(gameBoard);
		}

		int posy[] = { x - 1, x, x + 1, x - 1, x + 1, x - 1, x, x + 1 };
		int posx[] = { y - 1, y - 1, y - 1, y, y, y + 1, y + 1, y + 1 };
		for (int i = 0; i < 8; i++) {
			if ((posx[i] >= 0 && posx[i] < 8 && posy[i] >= 0 && posy[i] < 8)) {
				if (inDanger[posx[i]][posy[i]] != 1 ) {
					if(gameBoard.chessBoard[posx[i]][posy[i]] != null) {
						if(gameBoard.chessBoard[posx[i]][posy[i]].color() != this.color) {
							gameBoard.move[posx[i]][posy[i]] = 2;
							moveFound = true;
						}
					}else {
						gameBoard.move[posx[i]][posy[i]] = 1;
						moveFound = true;
					}
				}
			}
		}
		
	}


}
