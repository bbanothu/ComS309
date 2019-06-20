package com.cychess.sockets.game.pieces;

import com.cychess.sockets.game.GameBoard;
import com.cychess.sockets.game.Pieces;
/**
 * Created by bbanothu on 1/19/2018.
 */

public class Pawn extends Pieces {
	
    public String color;
    public String type = "pawn";

    public boolean isSelected;
    public boolean isWhite;
    public boolean firstMove;
    public boolean checking;
    
    public int y;
    public int x;

    /**
     * Initializes Y and X values and sets its color
     * @param y			Initial Y val
     * @param x			Initial X val
     * @param isWhite	Set color
     */
    public Pawn(int y, int x, boolean isWhite)
    {
        this.y = y;
        this.x = x;
        this.isWhite = isWhite;
        if(isWhite){
            this.color = "white";
        }else{
            this.color = "black";
        }
        firstMove = true;
    }

    /**
     * Sets the interval x and y values to the new position;
     * Also Changes position on gameBoard
     * @param y				Initial Y val
     * @param x				Initial X val
     * @param gameBoard		Current Gameboard
     */
    public void move(int y, int x, GameBoard gameBoard)
    {
        int oldX = this.x;
        int oldY = this.y;

        gameBoard.chessBoard[y][x] = this;
        gameBoard.chessBoard[oldY][oldX] = null;

        this.y = y;
        this.x = x;
        gameBoard.clearMoves();
        

        if(firstMove){
            firstMove = false;
        }
    	if(gameBoard.whiteMove) {
    		gameBoard.whiteMove = false;
    	}else {
    		gameBoard.whiteMove = true;
    	}
      //  gameBoard.clearMoves();
    }

    /**
     * populates gameBoard.move with ones after checking for valid moves
     * @param gameBoard		Current gameboard
     * @return 				Whether or not a valid move was selected
     */
    public boolean moveShower(GameBoard gameBoard)
    {
    	
    	boolean validMove;
    	validMove = false;

           if(isWhite){
               if(firstMove) {
                   if (gameBoard.chessBoard[this.y - 2][this.x] == null) {
                       gameBoard.move[this.y - 2][this.x] = 3;
                       validMove = true;
                   }
               }


              if(gameBoard.chessBoard[this.y - 1][this.x] == null ){
                  gameBoard.move[this.y - 1][this.x] = 3;
                  validMove = true;
              }


               if(this.x - 1 >= 0 ){
                   if(gameBoard.chessBoard[this.y - 1][this.x-1] != null && gameBoard.chessBoard[this.y - 1][this.x-1].isWhite() != this.isWhite){
                       gameBoard.move[this.y - 1][this.x-1] = 2;
                       validMove = true;
                   }
               }

               if(this.x + 1 <= 7){
                   if(gameBoard.chessBoard[this.y - 1][this.x+1] != null  && gameBoard.chessBoard[this.y - 1][this.x+1].isWhite() != this.isWhite){
                       gameBoard.move[this.y - 1][this.x+1] = 2;
                       validMove = true;
                   }
               }

           }else{

               //checking first move extra step
               if(firstMove) {
                   if (gameBoard.chessBoard[this.y + 2][this.x] == null) {
                       gameBoard.move[this.y + 2][this.x] = 3;
                       validMove = true;
                   }
               }

               //checking normal move
               if(gameBoard.chessBoard[this.y + 1][this.x] == null ){
                   gameBoard.move[this.y + 1][this.x] = 3;
                   validMove = true;
               }


               // Checking potential attackers to its left
               if(this.x - 1 >= 0 ){
                   if(gameBoard.chessBoard[this.y + 1][this.x-1] != null && gameBoard.chessBoard[this.y + 1][this.x-1].isWhite() != this.isWhite){
                       gameBoard.move[this.y + 1][this.x-1] = 2;
                       validMove = true;
                   }
               }

               // Checking potential attackers to its right
               if(this.x + 1 <= 7){
                   if(gameBoard.chessBoard[this.y + 1][this.x+1] != null &&  gameBoard.chessBoard[this.y + 1][this.x+1].isWhite() != this.isWhite){
                       gameBoard.move[this.y + 1][this.x+1] = 2;
                       validMove = true;
                   }
               }
           }
		return validMove;
    }
    
    /**
     * Attacks a piece if there is a piece in the next destination
     * @param gameBoard		Current gameboard
     */
    public void attack(GameBoard gameBoard) {
    	if(isWhite) {
            if(this.x - 1 > 0 ){
            	gameBoard.move[this.y - 1][this.x-1] = 2;
            }
            if(this.x + 1 < 7){
                    gameBoard.move[this.y - 1][this.x+1] = 2;
            }
    		
    	}else {
            if(this.x - 1 > 0 ){
            	gameBoard.move[this.y + 1][this.x-1] = 2;
                
            }

            // Checking potential attackers to its right
            if(this.x + 1 < 7){
            	gameBoard.move[this.y + 1][this.x+1] = 2;
                
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
            return "WP";
        }
        return "BP";
    }


	@Override
	public int toString_number() {
        if(isWhite){
            return 12;
        }
        return 5;
	}

	@Override
	public void checking(GameBoard gameBoard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void danger_Shower(GameBoard gameBoard) {
    	if(isWhite) {
            if(this.x - 1 > 0 ){
            	gameBoard.move[this.y - 1][this.x-1] = 2;
            }
            if(this.x + 1 < 7){
                    gameBoard.move[this.y - 1][this.x+1] = 2;
            }
    		
    	}else {
            if(this.x - 1 > 0 ){
            	gameBoard.move[this.y + 1][this.x-1] = 2;
                
            }

            // Checking potential attackers to its right
            if(this.x + 1 < 7){
            	gameBoard.move[this.y + 1][this.x+1] = 2;
                
            }
    		
    	}
		
	}

}


