package com.cychess.sockets.game;

import com.cychess.sockets.game.GameBoard;
/**
 * Created by bbanothu on 1/18/2018.
 */

public abstract class Pieces
{
    public abstract String color();
    public abstract String type();
    
    public abstract boolean isWhite();
    public abstract boolean isSelected();
    
    
    
    /**
     * Checks to make it is whites move and its a white piece
     * or that its a black move and a black piece
     * @param gameBoard		Current gameboard
     * @return 				Whether or not the game is in sync with the board
     */
    public abstract boolean onClick(GameBoard gameBoard);


    /**
     * Shows which moves are valid to make for the selected piece
     * @param gameBoard		Current gameboard
     * @return 				Whether or not a valid move was selected
     */
    public abstract boolean moveShower(GameBoard gameBoard);

    /**
     * Sets the interval x and y values to the new position;
     * Also Changes position on gameBoard
     * @param y				Initial Y val
     * @param x				Initial X val
     * @param gameBoard		Current Gameboard
     */
    public abstract void move(int y, int x, GameBoard gameBoard);
	public abstract int toString_number();
	public abstract void checking(GameBoard gameBoard);
	public abstract void danger_Shower(GameBoard gameBoard);
		// TODO Auto-generated method stub
		
	

}
