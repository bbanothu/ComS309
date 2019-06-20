package com.cychess.sockets;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.cychess.model.UserDAO;
import com.cychess.sockets.game.GameBoard;
import com.cychess.sockets.game.Player;

/**
 * Initiates a casual game between two players
 * @author Kienan bbanothu
 *
 */
public class CasualGame extends Thread{

	/**
	 * Waiting list for casual players
	 */
	public static Queue<User> casualList;
	
	/**
	 * Initializes the casual waiting list.
	 */
	public CasualGame() {
		casualList = new LinkedList<User>();
	}
	
	/**
	 * Waits for two or more players to search for a game and matches them up in duos in the order they searched.
	 */
	public void run() {
		
		int gameCount = 0;	//for debugging
		
		System.out.println("Casual game server is running!");
		
		try {
			while (true) {
				if (casualList.size() > 1) {

					User userA = (User) casualList.poll();
					casualList.remove(userA);
					User userB = (User) casualList.poll();
					casualList.remove(userB);
					
					System.out.println("New Casual Game Starting: " + gameCount++);
					GameBoard game = new GameBoard();
					
					
					userA.player = new Player(userA.socket , 'W', game, userA );
					userA.inGame = true;
					System.out.println("Player 1 Connected");
					

					userB.player = new Player(userB.socket , 'B', game, userB);
					userB.inGame = true;
					System.out.println("Player 2 Connected");
					
					
					userA.player.setOpponent(userB.player);
					userB.player.setOpponent(userA.player);
					
					game.currentPlayer = userA.player;
					int[]arr = game.print_toString();
					System.out.println(arr.toString());
					
					new UserDAO().createNewChessboard(userA.username,userB.username, arr  );
					int id = 	new UserDAO().getIdAtUsername_gameBoard(userA.username);
					
					userA.player.gameBoard_id = id;
					userB.player.gameBoard_id = id;
					
					userA.player.start();
					userB.player.start();
				}
			}
		} finally {
			System.out.println("A casual game has ended.");
		}
	}

	public static boolean contains(User user) {
		Iterator<User> Iterator = casualList.iterator();
		while (Iterator.hasNext()) {
			User s1 = (User) Iterator.next();
			if ( s1.username.equals(user.username)) {
				System.out.println("sike, youre already in queue");
				return true;
			}
		}
		return false;
		
	}
	public static void remove(User user) {
		Iterator<User> Iterator = casualList.iterator();
		while (Iterator.hasNext()) {
			User s1 = (User) Iterator.next();
			if ( s1.username.equals(user.username)) {
				System.out.println("User is contained, prolly going to remove");
				casualList.remove(user);
				break;

			}
		}
	}
}
