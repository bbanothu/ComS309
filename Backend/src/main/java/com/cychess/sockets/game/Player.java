package com.cychess.sockets.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.cychess.model.UserDAO;
import com.cychess.sockets.CasualGame;
import com.cychess.sockets.RankedGame;
import com.cychess.sockets.User;

/**
 * Class that listens for user's game moves.
 * @author bbanothu
 *
 */
public class Player extends Thread {
	char mark;
	public boolean in_Game;
	public boolean inCheck;
	public boolean whiteWin;
	
	User this_user;
	
	public Player opponent;

	GameBoard game;

	Socket socket;
	BufferedReader input;
	PrintWriter output;
	ObjectOutputStream data;
	User user;
	public int gameBoard_id;

	/**
	 * Constructs a handler thread for a given socket and mark initializes the
	 * stream fields, displays the first two welcoming messages.
	 * @param socket	User's socket
	 * @param mark		User's color for this game
	 * @param game		Current gameboard
	 * @param user		Current user
	 * @param casual 
	 */
	public Player(Socket socket, char mark, GameBoard game, User user) {
		this.game = game;
		this.socket = socket;
		this.mark = mark;
		this.this_user = user;
		this.gameBoard_id = 0;
		try {
			data = new ObjectOutputStream(socket.getOutputStream());
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			
			output.println("WELCOME " + mark);
			output.println("MESSAGE Waiting for opponent to connect");
		} catch (IOException e) {
			System.out.println("GamePlay Constructor: Player disconnected: " + e);
		}
	}

	/**
	 * Accepts notification of who the opponent is.
	 * @param opponent		Current player's opponent
	 */
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

/**
 * Handles the otherPlayerMoved message.
 * @param locationX		Opponent's piece's previous X location
 * @param locationY		Opponent's piece's previous Y location
 * @param currentX		Opponent's piece's new X location
 * @param currentY		Opponent's piece's new Y Location
 */
	public void otherPlayerMoved(int locationX, int locationY, int currentY, int currentX) {
		output.println("OPPONENT_MOVED " + locationX + locationY + currentX + currentY);
		
	}
	
	public void otherPlayerMoved_castle(int locationX, int locationY, int currentY, int currentX, int locationX_rook, int locationY_rook, int currentY_rook, int currentX_rook) {
		output.println("OPPONENT_MOVED " + locationX + locationY + currentX + currentY + locationX_rook + locationY_rook + currentX_rook + currentY_rook);
		
	}

	
	
	/**
	 * The run method of this thread.
	 */

	public void run() {
		try {
			 in_Game = true;
			output.println("MESSAGE All players connected");
			
			while (in_Game) {
				if(input.ready()) {
				String command = input.readLine();
				System.out.println("COMMAND FROM GAME: " + command);
				if (command.startsWith("MOVE")) {
					if (mark == 'W' && game.whiteMove) {
						char x = command.charAt(5);
						char y = command.charAt(6);
						int locationx = x - '0';
						int locationy = y - '0';
						System.out.println("Y: " + locationy);
						System.out.println("X: " + locationx);
						game.movePiece(locationy, locationx);
						game.print();

						new UserDAO().uploadRound(gameBoard_id );
					} else if (mark == 'B' && !game.whiteMove) {
						char x = command.charAt(5);
						char y = command.charAt(6);
						int locationx = x - '0';
						int locationy = y - '0';
						System.out.println("Y: " + locationy);
						System.out.println("X: " + locationx);
						game.movePiece(locationy, locationx);
						game.print();
					}
					if(game.Over) {
						game_over();
						output.println("BACK");
						break;
					}

				}
				else if (command.startsWith("OVER")) {	//Format of OVERW or OVERB
				//	game_over();
					break;
				}
				else if (command.startsWith("DRAW")) {
					game_draw();
					output.println("BACK");
					break;
					
				} else if (command.startsWith("QUIT-ALL")) {
					opponent.output.println("BACK");
					opponent.stop_game();
					System.out.println("Quit");
					stop_game();
					break;
				} else if (command.startsWith("QUIT")) {
					opponent.output.println("BACK");
					opponent.stop_game();
					System.out.println("Quit");
					stop_game();
					output.println("BACK");
					break;
				}
			}
			
		}

		} catch (IOException e) {
			System.out.println("Thread: Player disconnected: " + e);
		} finally {
			this.interrupt();
		}
	}

	public void stop_game() {
		in_Game = false;
		this_user.inGame = false;
		new UserDAO().deleteGameBoardAtId(gameBoard_id);
		
	}
	
	public void game_over() {
		opponent.stop_game();
		opponent.output.println("BACK");
		System.out.println("Quit");
		stop_game();
		int id1 = new UserDAO().getIdAtUsername(this_user.username);
		int id2 = new UserDAO().getIdAtUsername(opponent.this_user.username);
		if(mark == 'W') {
			if(game.whiteWinner) {
				new UserDAO().addWin(id1);
				new UserDAO().addLoss(id2);
			}else {
				new UserDAO().addLoss(id1);
				new UserDAO().addWin(id2);
			}
			new UserDAO().addTotal(id1);
			new UserDAO().addTotal(id2);
			
		}else {
			if(!game.whiteWinner) {
				new UserDAO().addWin(id2);
				new UserDAO().addLoss(id1);
			}else {
				new UserDAO().addLoss(id2);
				new UserDAO().addWin(id1);
			}
			new UserDAO().addTotal(id1);
			new UserDAO().addTotal(id2);
			
		}
		
	}
	
	public void game_draw() {
		stop_game();
		int id1 = new UserDAO().getIdAtUsername(this_user.username);
		int id2 = new UserDAO().getIdAtUsername(opponent.this_user.username);
		
		new UserDAO().addWin(id1);
		new UserDAO().addWin(id2);
		new UserDAO().addLoss(id1);
		new UserDAO().addLoss(id2);
	}

}