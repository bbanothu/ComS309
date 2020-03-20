package com.cychess.model;

import java.util.Set;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

/**
 * User Model
 * @author bbanothu
 *
 */

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int id;
	
	@Column(name = "email")
	//@Email(message = "*Please provide a valid Email")
	@Pattern(regexp="\\b[A-Za-z0-9._%+-]+@iastate.edu\\b")
	@NotEmpty(message = "*Please provide an email")
	private String email;
	
	@Column(name = "password")
	@Length(min = 5, message = "*Your password must have at least 5 characters")
	@NotEmpty(message = "*Please provide your password")
	@Transient
	private String password;
	
	@Column(name = "name")
	@NotEmpty(message = "*Please provide your name")
	private String name;
	
	@Column(name = "username")
	@NotEmpty(message = "*Please provide your last name")
	private String username;
	
	@Column(name = "active")
	private int active;
	
	@Column(name = "ONLINE_STATUS")
	private int ONLINE_STATUS;
	
	@Column(name = "GAMES_WON")
	private int GAMES_WON;
	
	@Column(name = "GAMES_LOST")
	private int GAMES_LOST;
	
	@Column(name = "TOTAL_GAMES")
	private int TOTAL_GAMES;
	
	@Column(name = "RANK")
	private int RANK;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	@Column(name = "TOKEN")
	private String token;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}
	
	public int getGameWon() {
		return GAMES_WON;
	}
	
	public void setGamesWon(int gamesWon) {
		this.GAMES_WON = gamesWon;
	}
	
	public int getGameLost() {
		return GAMES_WON;
	}
	
	public void setGamesLost(int gamesLost) {
		this.GAMES_LOST = gamesLost;
	}
	
	public int getTotalGames() {
		return TOTAL_GAMES;
	}
	
	public void setTotalGames(int totalGames) {
		this.TOTAL_GAMES = totalGames;
	}
	
	public int getRank() {
		return RANK;
	}
	
	public void setRank(int rank) {
		this.RANK = rank;
	}
	

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

}
