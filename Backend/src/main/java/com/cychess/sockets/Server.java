package com.cychess.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import com.cychess.model.UserDAO;


/**
 * The main class for when a user initiates a connection to the server.
 * @author bbanothu
 *
 */
public class Server extends Thread {
	
	/**
	 * Server socket
	 */
	public ServerSocket listener;

	/**
	 * List of users connected to server
	 */
	public static ArrayList<User> users;	
	
	Socket socket;
	BufferedReader input;
	PrintWriter output;
	
	/**
	 * Initializes the ServerSocket listener and a list of users.
	 * @throws IOException
	 */
	public Server() throws IOException {
		listener = new ServerSocket(8091);
		users = new ArrayList<User>();
	}
	
	/**
	 * init socket values
	 */
	public void initSocket() {
		try {
			socket = listener.accept();
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			System.out.println("User connected, getting credentials");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	/**
	 * main function to add users to the server list. calls on newUser and initSocket
	 */
	@SuppressWarnings("deprecation")
	public void run() {
		try {
			Iterator<User> itr = users.iterator();
		
			while(true) {
				System.out.println("Waiting for user to connect!");
				String token = "DEFAULT";
				initSocket();

				//Wait for user token to be sent
				while( token.equals("DEFAULT") ) {
					token = input.readLine();
					if( token.startsWith("TOKEN") ) {
						token = token.substring(6);
						if( new UserDAO().checkIfTokenExists(token) == 1 ) {
							
							System.out.println("Token is valid!\nChecking to see if user is already in the list!");
							String username = new UserDAO().getUsernameAtToken(token);
							int id = new UserDAO().getIdAtUsername(username);	

								System.out.println("New user added to the users list!");
								User s = new User(socket, username, id);
								users.add(s);
								s.start();

							
							itr = users.iterator();

							while( itr.hasNext() ) {
								User s1 = (User) itr.next();
								System.out.println( "SOCKET: " + s1.socket.toString() + " ID: " + s1.id + " NAME: " + s1.username);
							}

						}
					}
				}
			}
		

		} catch (IOException a){
			a.printStackTrace();
		}finally {
			try {
				socket.close();
			} catch (IOException e) {
				
			}
		}
	}
}
