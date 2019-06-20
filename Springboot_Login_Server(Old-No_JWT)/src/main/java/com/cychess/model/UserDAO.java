package com.cychess.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils; 

import com.mysql.jdbc.Driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * DAO used to connect with the userlist table in the database
 * @author Kurt bbanothu pjd
 *
 */
public class UserDAO {
	
    private JdbcTemplate jdbcTemplate;
	
    
    /**
     * Constructor for UserDAO object
     */
	public UserDAO() {
		try {
			this.jdbcTemplate = jdbcTemplate();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates new jdbc Template
	 * @return jdbcTemplate
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	@Bean
	JdbcTemplate jdbcTemplate() throws IllegalAccessException, InvocationTargetException, InstantiationException {
	    // extract this 4 parameters using your own logic
	    final String driverClassName = "com.mysql.jdbc.Driver";
	    final String jdbcUrl = "jdbc:mysql://mysql.cs.iastate.edu:3306/db309yt5?useSSL=false";
	    final String username = "dbu309yt5";
	    final String password = "gVTCFBgb";
	    // Build dataSource manually:
	    final Class<?> driverClass = ClassUtils.resolveClassName(driverClassName, this.getClass().getClassLoader());
	    final Driver driver = (Driver) ClassUtils.getConstructorIfAvailable(driverClass).newInstance();
	    final DataSource dataSource = new SimpleDriverDataSource(driver, jdbcUrl, username, password);
	    // and make the jdbcTemplate
	    return new JdbcTemplate(dataSource);
	}
	
	
	/**
	 * Inserts new user into user table given username, name, email, password
	 * Games lost, won, total games, rank, and online status are set to 0 as a default
	 * @param username
	 * @param realname
	 * @param email
	 * @param password
	 */
	public void insertNewUser(String username, String realname, String email, String password) {
			
		jdbcTemplate.update(String.format("INSERT INTO user (password, username, games_lost, games_won, online_status, rank, total_games, email, name) VALUES (\"%s\", \"%s\", 0, 0, 0, 0, 0, \"%s\", \"%s\");",
				password, username, email, realname));
	}
	
	/**
	 * Deletes a user from the user table given an id
	 * @param id User Id
	 */
	public void deleteUserAtId(int id) {
		jdbcTemplate.execute(String.format("DELETE FROM user_role WHERE user_id = '%d';", id));
		jdbcTemplate.execute(String.format("DELETE FROM user WHERE user_id = '%d';", id));
	}
	
	/**
	 * changes password
	 * @param id User Id
	 */
	public void changePassword(String username, String password) {
		jdbcTemplate.update(String.format("UPDATE user SET password = '%s' WHERE username = '%s';", password, username));
	}
	
	/**
	 * Deletes announcement from announcement table given an id
	 * @param id Announcement ID
	 */
	public void deleteAnnouncement(int id) {
		jdbcTemplate.execute(String.format("DELETE FROM announcements WHERE ann_id = '%d';", id));
	}
	
	/**
	 * Changes a user's username at user id to new given String
	 * @param newName New username
	 * @param id User Id
	 */
	public void updateUserName (String newName, int id) {
		
		jdbcTemplate.update(String.format("UPDATE user SET username = '%s' WHERE user_id = '%d';", newName, id));
	}
	
	/**
	 * Changes a user's username at user id to new given String
	 * @param newName New username
	 * @param id User Id
	 */
	public void updateEmail (String newEmail, int id) {
		
		jdbcTemplate.update(String.format("UPDATE user SET email = '%s' WHERE user_id = '%d';", newEmail, id));
	}


	/**
	 * Returns username of a user at a specific id
	 * @param id User Id
	 * @return
	 */
	public String getUsernameAtId(int id) {
    	
		return jdbcTemplate.queryForObject("SELECT username FROM user WHERE user_id = ?;", String.class, id);
    }
	
	/**
	 * Gets the user id at a specific username
	 * @param username 
	 * @return int User Id
	 */
	public int getIdAtUsername(String username) {
		
		return jdbcTemplate.queryForObject("SELECT user_id FROM user WHERE username = ?;", Integer.class, username);
	}
	
	/**
	 * Gets the user id at a specific email
	 * @param email
	 * @return int User Id
	 */
	public int getIdAtEmail(String email) {
		
		return jdbcTemplate.queryForObject("SELECT user_id FROM user WHERE email = ?;", Integer.class, email);
	}
	
	/**
	 * Gets the token at a specific username
	 * @param username
	 * @return String token of user
	 */
	public String getTokenAtUsername(String username) {
		
		return jdbcTemplate.queryForObject("SELECT token FROM user WHERE username = ?", String.class, username);
	}
	
	/**
	 * Gets the token at a specific email
	 * @param email
	 * @return String token of user
	 */
	public String getTokenAtEmail(String email) {
		
		return jdbcTemplate.queryForObject("SELECT token FROM user WHERE email = ?", String.class, email);
	}
	
	/**
	 * Gets the username at a specific token
	 * @param token
	 * @return String username
	 */
	public String getUsernameAtToken(String token) {
		
		return jdbcTemplate.queryForObject("SELECT username FROM user WHERE token = ?", String.class, token);
	}
	
	/**
	 * Returns user role given their role id
	 * @param id Role Id
	 * @return String User Role
	 */
	public String getUserRole(int id) {
			
		return jdbcTemplate.queryForObject("SELECT role_id FROM user_role WHERE user_id = ?;", String.class, id);
	}
	
	/**
	 * Returns user role given their username
	 * @param username username of user
	 * @return String with user role
	 */
//	public String getUserRole(String username) {
//		
//		return jdbcTemplate.queryForObject("SELECT role_id FROM user_role WHERE username = ?;", String.class, username);
//	}
	
	
	/**
	 * Checks to see if any user has the specificed token
	 * @param token	The token
	 * @return int 0 or 1 specifying if token exists or not
	 */
	public int checkIfTokenExists(String token) {
		
		return jdbcTemplate.queryForObject("SELECT 1 FROM user WHERE token = ?", Integer.class, token);
	}
	
	/**
	 * Checks the user table to see if username already exists
	 * Returns 1 if it already exists; returns 0 if it does not
	 * @param username	The username
	 * @return int 0 or 1 specifying if name exists or not
	 */
	public int checkIfNameExists (String username) {
		
		return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT 1 FROM user WHERE username = ?);", Integer.class, username);
	}
	
	/**
	 * Checks the user table to see if email already exists
	 * Returns 1 if it already exists; returns 0 if it does not
	 * @param email
	 * @return int 0 or 1 specifying if email exists or not
	 */
	public int checkIfEmailExists (String email) {
		
		return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT 1 FROM user WHERE email = ?);", Integer.class, email);
	}
	
	
	public String getPasswordAtUsername (String username) {
		
		return jdbcTemplate.queryForObject("SELECT password FROM user WHERE username = ?;", String.class, username);
	}

	/**
	 * Returns a List of all announcements in the announcement table
	 * @return List of all announcements
	 */
	public List<Map<String, Object>> getAnnouncements() {
    	
		return jdbcTemplate.queryForList("SELECT ann_text,ann_date, ann_user_name, ann_id FROM announcements;");
    }
	
	/**
	 * 
	 * @param type	0(default) for rank, 1 for wins, 2 for losses, 3 for total games
	 * @return	Selected Leaderboard 
	 */
	public List<Map<String, Object>> getLeaderboards(int type) {
		if (type == 1) {
			return jdbcTemplate.queryForList("SELECT games_won,username FROM user;");
		}
		else if (type == 2) {
			return jdbcTemplate.queryForList("SELECT games_lost,username FROM user;");
		}
		else if (type == 3) {
			return jdbcTemplate.queryForList("SELECT total_games,username FROM user;");
		}
		else {
    		return jdbcTemplate.queryForList("SELECT rank,username FROM user;");
    	}
    }
	
	/**
	 * Returns a List of all usernames in the user table
	 * @return List of all usernames
	 */
	public List<Map<String, Object>> getAllUserNames () {
		
		return jdbcTemplate.queryForList("SELECT username FROM user;");
	}
	
	/**
	 * Returns a List of all usernames and names in user table
	 * @return List of all usernames and names
	 */
	public List<Map<String, Object>> getUserList() { 
	    List<Map<String, Object>> allusers = jdbcTemplate.queryForList("SELECT username, name FROM user;");   
	    return allusers; 
	} 
	
	/**
	 * Returns a List of all data from the user table
	 * @return List of all user info
	 */
	public List<Map<String,Object>> getAllUserInfo(){
		
		return jdbcTemplate.queryForList("SELECT * FROM user;");
	}
	
	
	
	
	public List<Map<String, Object>> getUserInfo(String username){
		return jdbcTemplate.queryForList("SELECT * FROM user WHERE username = '?';", username);
	}
	
	
	public void addWin(int id) {
		int prevwins = jdbcTemplate.queryForObject("SELECT games_won FROM user WHERE user_id = ?;", Integer.class, id);
		jdbcTemplate.update(String.format("UPDATE user SET games_won = '%d' WHERE user_id = '%d';", prevwins+1, id));
	}
	
	public int getWins(int id) {
		return jdbcTemplate.queryForObject("SELECT games_won FROM user WHERE user_id = ?;", Integer.class, id);
	}
	
	public void addLoss(int id) {
		int prevlost = jdbcTemplate.queryForObject("SELECT games_lost FROM user WHERE user_id = ?;", Integer.class, id);
		jdbcTemplate.update(String.format("UPDATE user SET games_lost = '%d' WHERE user_id = '%d';", prevlost+1, id));
	}
	
	public int getLoss(int id) {
		return jdbcTemplate.queryForObject("SELECT games_lost FROM user WHERE user_id = ?;", Integer.class, id);
	}
	
	public void addTotal(int id) {
		int prevtotal = jdbcTemplate.queryForObject("SELECT total_games FROM user WHERE user_id = ?;", Integer.class, id);
		jdbcTemplate.update(String.format("UPDATE user SET total_games = '%d' WHERE user_id = '%d';", prevtotal+1, id));
	}
	
	public void adjustWins(int id, int newwins) {
		jdbcTemplate.update(String.format("UPDATE user SET games_won = '%d' WHERE user_id = '%d';", newwins, id));
	}
	
	public void adjustLoss(int id, int newlost) {
		jdbcTemplate.update(String.format("UPDATE user SET games_lost = '%d' WHERE user_id = '%d';", newlost, id));
	}
	
	public void adjustTotal(int id, int newtotal) {
		jdbcTemplate.update(String.format("UPDATE user SET total_games = '%d' WHERE user_id = '%d';", newtotal, id));
	}
	
	public int getUserRank(int id) {
		return jdbcTemplate.queryForObject("SELECT rank FROM user WHERE user_id = ?;", Integer.class, id);
	}
	
	public void setUserRank(int id, int rank) {
		jdbcTemplate.update(String.format("UPDATE user SET rank = '%d' WHERE user_id = '%d';", rank, id));
	}
	
	
	public List<Map<String, Object>> getAllGames(){
		
		return jdbcTemplate.queryForList("SELECT game_id, p1_id, p2_id, round  FROM game_list;");
	}
	
	public void createNewChessboard(String p1, String p2, int[] board ) {
		jdbcTemplate.execute(String.format("insert into game_list (p1_id, p2_id, round, latestBoard) values (\"%s\", \"%s\", 0, \"%s\");", p1, p2, Arrays.toString(board)));
	}
	
	public int getIdAtUsername_gameBoard(String username) {
		return jdbcTemplate.queryForObject("SELECT game_id FROM game_list WHERE p1_id = ?;", Integer.class, username);
	}
	
	public void uploadTurn(int[] board, int id) {
		jdbcTemplate.update(String.format("UPDATE game_list SET latestBoard = \"%s\" WHERE game_id = '%d';", Arrays.toString(board), id));

	}
	
	public void uploadRound( int id) {
		int round = jdbcTemplate.queryForObject("SELECT round FROM game_list WHERE game_id = ?;", Integer.class, id);
		jdbcTemplate.update(String.format("UPDATE game_list SET round = '%d' WHERE game_id = '%d';", round+1, id));

	}
	
	public void deleteGameBoardAtId(int id) {
		jdbcTemplate.execute(String.format("DELETE FROM game_list WHERE game_id = '%d';", id));
	}
	
	public String getGameBoardAtId(int id) {
		return jdbcTemplate.queryForObject("Select latestBoard FROM game_list WHERE game_id = ?;", String.class, id);
	}
	
	
}

