package com.cychess.sockets.game.pieces;

import com.cychess.sockets.game.GameBoard;
import com.cychess.sockets.game.Pieces;

/**
 * Created by bbanothu on 1/19/2018.
 */

public class Knight extends Pieces {
	
    public String color;
    public String type = "knight";

    public boolean isSelected;
    public boolean isWhite;
    public boolean checking;
    
    public int y;
    public int x;

    /**
     * Initializes Y and X values and sets its color
     * @param y			Initial Y val
     * @param x			Initial X val
     * @param isWhite	Set color
     */
    public Knight(int y, int x, boolean isWhite)
    {
        this.y = y;
        this.x = x;
        this.isWhite = isWhite;
        if(isWhite){
            this.color = "white";
        }else{
            this.color = "black";
        }
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
    	if(gameBoard.whiteMove) {
    		gameBoard.whiteMove = false;
    	}else {
    		gameBoard.whiteMove = true;
    	}
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
    	
        int x = this.x;
        int y = this.y;
        
		int posy[]={x+1,x+1,x+2,x+2,x-1,x-1,x-2,x-2};
		int posx[]={y-2,y+2,y-1,y+1,y-2,y+2,y-1,y+1};
		for(int i=0;i<8;i++) {
			if((posx[i]>=0&&posx[i]<8&&posy[i]>=0&&posy[i]<8)) {
				if((gameBoard.chessBoard[posx[i]][posy[i]]==null||gameBoard.chessBoard[posx[i]][posy[i]].color()!=this.color()))
				{
					gameBoard.move[posx[i]][posy[i]] = 1;
			    	validMove = true;
				}
				if((gameBoard.chessBoard[posx[i]][posy[i]]!=null&&gameBoard.chessBoard[posx[i]][posy[i]].color()!=this.color()))
				{
					gameBoard.move[posx[i]][posy[i]] = 2;
					validMove = true;
				}
			}
		}
		return validMove;
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
            return "WK";
        }
        return "BK";
    }


	@Override
	public int toString_number() {
        if(isWhite){
            return 8;
        }
        return 1;
	}

	@Override
	public void checking(GameBoard gameBoard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void danger_Shower(GameBoard gameBoard) {
    	boolean validMove;
    	validMove = false;
    	
        int x = this.x;
        int y = this.y;
        
		int posy[]={x+1,x+1,x+2,x+2,x-1,x-1,x-2,x-2};
		int posx[]={y-2,y+2,y-1,y+1,y-2,y+2,y-1,y+1};
		for(int i=0;i<8;i++) {
			if((posx[i]>=0&&posx[i]<8&&posy[i]>=0&&posy[i]<8)) {
				if((gameBoard.chessBoard[posx[i]][posy[i]]==null||gameBoard.chessBoard[posx[i]][posy[i]].color()==this.color()))
				{
					gameBoard.move[posx[i]][posy[i]] = 1;
			    	validMove = true;
				}
				if((gameBoard.chessBoard[posx[i]][posy[i]]!=null&&gameBoard.chessBoard[posx[i]][posy[i]].color()==this.color()))
				{
					gameBoard.move[posx[i]][posy[i]] = 2;
					validMove = true;
				}
			}
		}
		
	}

}
