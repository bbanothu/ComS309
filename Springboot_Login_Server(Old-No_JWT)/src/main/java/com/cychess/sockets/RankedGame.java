package com.cychess.sockets;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.cychess.model.UserDAO;
import com.cychess.sockets.game.GameBoard;
import com.cychess.sockets.game.Player;

/**
 * Initializes a ranked game between two players after they've both selected this as a game option.
 * @author Kienan bbanothu
 *
 */
public class RankedGame extends Thread{

	/**
	 * Waiting list for people in rank 1
	 */
	public static Queue<User> rankedList1;
	/**
	 * Waiting list for people in rank 2
	 */
	public static Queue<User> rankedList2;
	/**
	 * Waiting list for people in rank 3
	 */
	public static Queue<User> rankedList3;

	/**
	 * Initializes the ranked waiting lists
	 */
	public RankedGame() {
		rankedList1 = new LinkedList<User>();
		rankedList2 = new LinkedList<User>();
		rankedList3 = new LinkedList<User>();
	}
	
	/**
	 * Waits for at least two players to select play ranked game and matches them if up skill levels are the same.
	 */
	public void run() {
		int gameCount = 0;	//for debugging
		
		System.out.println("Ranked game server is running!");
		
		try {
			while (true) {
				if (rankedList1.size() > 1) {
					System.out.println("New Ranked Game Starting: " + gameCount++);
					User a = (User) rankedList1.poll();
					User b = (User) rankedList1.poll();
					rankedList1.remove(a);
					rankedList1.remove(b);
					startGame(a, b);	
					
				}
				if (rankedList2.size() > 1) {
					System.out.println("New Ranked Game Starting: " + gameCount++);
					User a = (User) rankedList2.poll();
					User b = (User) rankedList2.poll();
					rankedList2.remove(a);
					rankedList2.remove(b);
					startGame(a, b);					
				}
				if (rankedList3.size() > 1) {
					System.out.println("New Ranked Game Starting: " + gameCount++);
					User a = (User) rankedList3.poll();
					User b = (User) rankedList3.poll();
					rankedList3.remove(a);
					rankedList3.remove(b);
					startGame(a, b);					
				}
				
			}
		} finally {
			System.out.println("A ranked game has ended.");
		}
	}
	
	public static void remove(User user) {
		Iterator<User> Iterator1 = rankedList1.iterator();
		Iterator<User> Iterator2 = rankedList2.iterator();
		Iterator<User> Iterator3 = rankedList3.iterator();
		
		while (Iterator1.hasNext()) {
			User s1 = (User) Iterator1.next();
			if ( s1.username.equals(user.username)) {
				System.out.println("User is contained, prolly going to remove");
				rankedList1.remove(user);
			}
		}
		
		while (Iterator2.hasNext()) {
			User s1 = (User) Iterator2.next();
			if ( s1.username.equals(user.username)) {
				System.out.println("User is contained, prolly going to remove");
				rankedList2.remove(user);
			}
		}
		
		while (Iterator3.hasNext()) {
			User s1 = (User) Iterator3.next();
			if ( s1.username.equals(user.username)) {
				System.out.println("User is contained, prolly going to remove");
				rankedList3.remove(user);
			}
		}
	}
	
	/**
	 * Starts a game between two players
	 * @param a User A
	 * @param b User B
	 */
	private void startGame(User a, User b) {
		User userA = a;
		User userB = b;
		
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
