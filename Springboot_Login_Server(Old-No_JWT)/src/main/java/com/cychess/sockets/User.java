package com.cychess.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.cychess.model.UserDAO;
import com.cychess.service.AnnoucementsService;
import com.cychess.service.UserService;
import com.cychess.sockets.CasualGame;
import com.cychess.sockets.game.Player;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *  This is the user class. It contains user info such as sockets, username, id, and rank.
 * @author bbanothu
 *
 */
public class User extends Thread {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private AnnoucementsService AnnoucementsService;
	@Autowired
	private UserService userService;
	
	class leaderboards{
		public int rank;
		public String username;
		public leaderboards(Integer object, String object2) {
			rank = (int) object;
			username = object2;
		}

	}
	

	public String username;
	int id;
	int rank;
	public boolean inGame;
	public boolean stopUser;
	
	Player player;
	
	
	Socket socket;
	BufferedReader input;
	PrintWriter output;
	ObjectOutputStream data;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private BCryptPasswordEncoder BCryptPasswordEncoder;
	
	
	/** 
	 *  Constructor class for the user
	 * @param socket	The socket connection initiated when the user logs into the server
	 * @param username	Username selected by the user
	 * @param id		User ID
	 */
	public User(Socket socket, String username, int id) {
		this.socket = socket;
		this.username = username;
		this.id = id;
		
		try {
			data = new ObjectOutputStream(socket.getOutputStream());
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			
		}
	}
	
	public static int getUserAtUsername(String username) {
		Iterator<User> itr = Server.users.iterator();
		int count = 0;
		
		while( itr.hasNext() ) {
			User s1 = (User) itr.next();
			System.out.println( "SOCKET: " + s1.socket.toString() + " ID: " + s1.id + " NAME: " + s1.username);
			count++;
		}
		return count;
	}
	
	public int getUserAtSocket(Socket socket) {
		//TODO 	Implement in UserDAO()
		return 0;
	}
	
	public int getUserAtId(int id) {
		//TODO	Implement in UserDAO()
		return 0;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
		try {
			this.data = new ObjectOutputStream(socket.getOutputStream());
			this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.output = new PrintWriter(socket.getOutputStream(), true);
		} catch (Exception e) {
			
		}
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	//Returns most up to date rank as recorded on the database
	private int getRank() {
		return new UserDAO().getUserRank(id);
	}
	/**
	 * Run method for the user's server thread. Listens for user's main menu actions.
	 */
	
	public void cassStart(String choice) {
		if(choice.startsWith("CASSTART-QUIT")){
			System.out.println(this.username + " has been removed from casual waiting list.");
			CasualGame.casualList.remove(this);
			inGame = false;	
		}else {
			System.out.println(this.username + " has been added to casual waiting list.");
			if(!CasualGame.contains(this)) {
				CasualGame.casualList.add(this);
			}
			//inGame = true;								
		}
	}
	
	public void rankStart(String choice) {
		rank = getRank();
		if (rank == 1) {
			if(choice.startsWith("RANKSTART-QUIT")) {
				System.out.println(this.username + " has been removed from rank 1 waiting list.");
				RankedGame.rankedList1.remove(this);
				inGame = false;	
			}else {
				System.out.println(this.username + " has been added to rank 1 waiting list.");
				RankedGame.rankedList1.add(this);	
				//inGame = true;
			}

		}
		else if (rank == 2) {
			if(choice.startsWith("RANKSTART-QUIT")) {
				System.out.println(this.username + " has been removed from rank 2 waiting list.");
				RankedGame.rankedList2.remove(this);
				inGame = false;	
			}else {
				System.out.println(this.username + " has been added to rank 2 waiting list.");
				RankedGame.rankedList2.add(this);	
				//inGame = true;
			}

		}
		else { // else if Rank is 3
			if(choice.startsWith("RANKSTART-QUIT")) {
				System.out.println(this.username + " has been removed from rank 3 waiting list.");
				RankedGame.rankedList3.remove(this);
				inGame = false;	
			}else {
				System.out.println(this.username + " has been added to rank 3 waiting list.");
				RankedGame.rankedList3.add(this);
				//inGame = true;
			}
			
		}
		
	}
	

	
	public void leaderboards(int type) {
        List<Map<String, Object>> lb = new UserDAO().getLeaderboards(type);
		leaderboards[] leaderboards = new leaderboards[lb.size()];
		
		Iterator<Map<String, Object>> Iterator = lb.iterator();
		int i = 0;
		while (Iterator.hasNext()) {
			Map<String, Object> a = Iterator.next();
			leaderboards[i] = new leaderboards((Integer) a.get("rank"), (String) a.get("username"));
			i++;
		}
		selectionSort(leaderboards);
	}
	
	public void selectionSort(leaderboards[] lb) {
		for (int i = 0; i < lb.length - 1; i++) {
			int min = i;
			for (int j = i + 1; j < lb.length; j++) {
				if (lb[j].rank < lb[min].rank)
					min = j;
			}
			leaderboards temp = lb[i];
			lb[i] = lb[min];
			lb[min] = temp;
		}
		String output_leaderboards = "[";
		for (int i = 0; i < lb.length; i++) {
			output_leaderboards += lb[i].rank + "," + lb[i].username + ",";
		}
		output.println("LEADERBOARD " + output_leaderboards);
	}	   
	
	public void change_username_client(  String new_user, String new_user_1,  String email ) {
		System.out.println("user called");
    	if(!new_user.equals("")) {
    	if(new_user.equals(new_user_1)) {
			int id = new UserDAO().getIdAtEmail(email);
    		new UserDAO().updateUserName(new_user, id);
    		System.out.println("user SUCCESS");
    		output.println(  "SETTINGS-USER-SUCCESS");

    		}
    	}
   		System.out.println("user FAILED");
   		output.println(  "SETTINGS-USER-FAILED");
	}
	
	public void change_email_client(  String new_email, String new_email_1, String email ) {
		System.out.println("email called");
    	if(!new_email.equals("")) {
    	if(new_email.equals(new_email)) {
			int id = new UserDAO().getIdAtEmail(email);
    		new UserDAO().updateEmail(new_email, id);
    		output.println(  "SETTINGS-EMAIL-SUCCESS," + new_email);

    		}
    	}
    	output.println( "SETTINGS-EMAIL-FAILED");
	}
	
	public void run() {
	 inGame = false;
	 stopUser = true;
		try {
			while(stopUser) {
					if(input.ready()) {
						if(!inGame) {
							String choice = input.readLine();
							
							System.out.println("COMMAND FROM CLIENT: " + choice);
							if( choice != null && !choice.isEmpty() ) {
								if (choice.startsWith("CASSTART")) {
									cassStart(choice);
								}
								else if (choice.startsWith("RANKSTART")) {
									rankStart(choice);
								}
								else if (choice.startsWith("FRIENDLIST")) {
									output.println("FRIENDLIST " + new UserDAO().getUserList());
								}
								else if (choice.startsWith("ANNOUNCEMENTS")) {
									output.println("ANNOUNCEMENTS " + new UserDAO().getAnnouncements());
									
								}else if (choice.startsWith("LEADERBOARD")) {
									//TODO	read user input to get type
									//0(default) for rank, 1 for wins, 2 for losses, 3 for total games
									leaderboards(0);
								}
								else if (choice.startsWith("CHAT")) {
									//TODO	Pull chat messages
								}
								else if (choice.startsWith("SETTINGS")) {
									if(choice.startsWith("SETTINGS-USER,")){
										System.out.println(choice);
										Scanner s = new Scanner(choice.substring(14 ));
										s.useDelimiter(",");
										String s1 = s.next();
										String s2 = s.next();
										String s3 = s.next();
										change_username_client(s1, s2, s3);
									}else if(choice.startsWith("SETTINGS-EMAIL,")){
										System.out.println(choice);
										Scanner s = new Scanner(choice.substring(14 ));
										s.useDelimiter(",");
										String s1 = s.next();
										String s2 = s.next();
										String s3 = s.next();
										change_email_client(s1, s2, s3);
									}
								}
								else if (choice.startsWith("CURRGAMES")) {
									if(choice.startsWith("CURRGAMES-OBSERVER-")) {
										while(true) {
										//	wait(1000);
											String id_string = choice.substring(19);
											int id = Integer.parseInt(id_string);
											//System.out.println("ID:" + id);
											output.println("CURRGAMES-OBSERVER " + new UserDAO().getGameBoardAtId(id));
											if(input.ready()) {
												choice = input.readLine();
												if(choice.startsWith("BACK")) {
													output.println("OBSERVE-BACK");
													break;
												}else if(choice.startsWith("QUIT-ALL")) {
													break;
												}
											}
										}
									}else {
										output.println("CURRGAMES " + new UserDAO().getAllGames());
									}
								}else if(choice.startsWith("QUIT-ALL")) {
									break;
								}
							}
						}
					}

			}
		} catch (IOException e) {
			//e.printStackTrace();
		} finally {
			try {
				stopUser = false;
				socket.close();
				input.close();
				output.close();
				Server.users.remove(this);

				this.interrupt();
				System.out.println("User Removed");
				
			} catch (IOException e) {
				
			}
		}
		
	}

}

