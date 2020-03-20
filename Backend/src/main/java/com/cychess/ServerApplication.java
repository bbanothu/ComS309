package com.cychess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;


import com.cychess.model.UserDAO;
import com.cychess.sockets.CasualGame;
import com.cychess.sockets.RankedGame;
import com.cychess.sockets.Server;

@SpringBootApplication
public class ServerApplication {
	
	@Autowired
	static JdbcTemplate jdbcTemplate;
	
	public static Server server;
	public static CasualGame casGame;
	public static RankedGame rankGame;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ServerApplication.class, args);
		server = new Server();
		casGame = new CasualGame();
		rankGame = new RankedGame();
		
		server.start();
		casGame.start();
		rankGame.start();
	}
}
