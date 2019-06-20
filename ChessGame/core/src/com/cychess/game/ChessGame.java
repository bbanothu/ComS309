package com.cychess.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cychess.game.online.Client;
import com.cychess.game.online.SocketClient;
import com.cychess.game.scenes.Menu;
import com.cychess.game.screens.ChessBoardScreen;
import com.cychess.game.screens.CurrentGamesScreen;
import com.cychess.game.screens.LoginScreen;
import com.cychess.game.screens.SettingsScreen;
import com.cychess.game.screens.SignupScreen;

import java.io.IOException;

public class ChessGame extends Game {

	/**
	 * Virtual height for the application
	 */
	public static final int VHEIGHT = 640;

	/**
	 * Virtual width for the application
	 */
	public static final int VWIDTH = 512;

	/**
	 * Height of the HUD for the top for bottom
	 */
	public static final int HUD_HEIGHT = ((VHEIGHT-VWIDTH)/2);

	/**
	 * Y location of where the TOP HUD starts
	 */
	public static final int TOP_HUD_Y = VHEIGHT-((VHEIGHT-VWIDTH)/2);

	/**
	 * Bottom Y location for where a screen starts
	 */
	public static final int SCREEN_Y = (VHEIGHT-VWIDTH)/2;

	/**
	 * Bottom Y location for where the menu starts
	 */
	public static final int MENU_Y = VHEIGHT-(VHEIGHT/2)-(VHEIGHT/5);
	/**
	 * Width of the meun
	 */
	public static final int MENU_WIDTH = ChessGame.VWIDTH/2;

	/**
	 * URL used for GET/POST requests to the server
	 */
	public static final String url = "http://localhost:8080"/*"http://proj-309-yt-5.cs.iastate.edu:8080"*/;

	/**
	 * URL used for connecting to the socket
	 */
	public static final String socketURL = "localhost"/*"proj-309-yt-5.cs.iastate.edu"*/;
	/**
	 * Port used for socket connection
	 */
	public static final int port = 8091;

	/**
	 * Aspect ratio of the screen
	 */
	public static float ASPECT_RATIO;
	/**
	 * LibGDX camera used for viewing from a certain perspective, height and width
	 */
	public static Camera CAMERA;

	/**
	 * Menu button
	 */
	public static Menu menu;

	/**
	 * Socket client
	 */
	public static SocketClient client;
	/**
	 * Token returned by the server for a given login/signup
	 */
	public static String token;
	/**
	 * Determines what color you are for a given chess game
	 */
	public static boolean isWhite;

	/**
	 * Contains the current users username
	 */
	public static String username;
	/**
	 * Contains the current users email
	 */
	public static String email;
	/**
	 * Contains your role. Can be 1 = ADMIN, 2 = MOD, 3 = USER
	 */
	public static int role;

	/**
	 * States used for the menu to determine which options are available given a specific screen
	 */
	public enum State {
		CURRENTGAMES,
		CHESSBOARD,
		LEADERBOARD,
		CHATROOM,
		FRIENDSLIST,
		ANNOUNCEMENT,
		SETTINGS,
		OBSERVER
	}

	/**
	 * Value of the current screen state
	 */
	public static State SCREEN_STATE;

	/**
	 * Called once when the application is first started. Gets the aspect ratio, creates
	 * a camera, and sets the screen to the LoginScreen.
	 */
	@Override
	public void create () {
		//TODO remove debug for tables
		//TODO fix rendering vertically
		ASPECT_RATIO = Gdx.graphics.getHeight()/Gdx.graphics.getWidth();
		CAMERA = new OrthographicCamera(VWIDTH, VHEIGHT);
		isWhite = true;
		setScreen(new LoginScreen(this));	//Default opens the setting screens
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		client.output.println("QUIT-ALL");
		client.output.println("QUIT-ALL");
	}
}
