package com.cychess.game.online;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.cychess.game.ChessGame;
import com.cychess.game.controller.BoardController;
import com.cychess.game.logic.ChessBoard;
import com.cychess.game.logic.Observer;
import com.cychess.game.logic.Piece;
import com.cychess.game.model.Announcements;
import com.cychess.game.model.CurrentGames;
import com.cychess.game.model.FriendsList;
import com.cychess.game.model.Leaderboards;
import com.cychess.game.screens.ChessBoardScreen;
import com.cychess.game.screens.CurrentGamesScreen;
import com.cychess.game.screens.ObserverScreen;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Peter on 4/16/2018.
 */

public class SocketClient extends Thread {

    private ChessGame game;

    /**
     * The socket used by the client to send and recieve information from the server
     */
    public Socket socket;

    /**
     * Input from the socket connection
     */
    public BufferedReader input;

    /**
     * Output for the socket connection
     */
    public PrintWriter output;

    /**
     * The x coordinate of the piece or tile you are sending information about
     */
    public int targetX;

    /**
     * The y coordinate of the piece or tile you are sending information about
     */
    public int targetY;

    /**
     * Determines if the user is playing a game or not
     */
    public boolean ingame;

    /**
     * Determines if the user is observiing a game or not
     */
    public boolean observe;

    /**
     * Holds the board string sent by the server to see if anything has changed
     */
    public String board = "DEFAULT";

    /**
     * Determines if the board is updated
     */
    public static boolean boardUpdated;

    public SocketClient(ChessGame game) throws Exception{
        this.game = game;
        socket = new Socket(ChessGame.socketURL, ChessGame.port);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * Main thread of the client that will listen for messages from
     * the server. When WELCOME is received a game has been started.
     * @throws
     */
    @Override
    public void run() {
        String response;
        try {
//            response = input.readLine();
//            System.out.println("(MAIN LOOP) SOCKET INPUT: " + response);
            boolean loop = true;
            ingame = false;
            observe = false;
            while(loop) {
                //TODO Implement ability to quit out of loop by sending server a message
                while(ingame && !observe) {
                    response = input.readLine();
                    System.out.println("(TRUE LOOP) SOCKET INPUT: " + response);

                    if (response.contains("VALID_MOVE")) {
                        Gdx.app.log("VALID_MOVE", "X: " + targetX + " " + "Y: " + targetY);
                        BoardController.movePiece(ChessBoard.selectedPiece, targetX, targetY);
                    } else if (response.contains("OPPONENT_MOVED")) {
                        //Castling moves
                        if( response.length() > 20){
                            int oldKingY = response.charAt(15) - '0';
                            int oldKingX = response.charAt(16) - '0';
                            int newKingY = response.charAt(17) - '0';
                            int newKingX = response.charAt(18) - '0';
                            int oldRookX = response.charAt(19) - '0';
                            int oldRookY = response.charAt(20) - '0';
                            int newRookY = response.charAt(21) - '0';
                            int newRookX = response.charAt(22) - '0';

                            Gdx.app.log("King Old Location", "X: " + oldKingX + " Y: " + oldKingY);
                            Gdx.app.log("King New Location", "X: " + newKingX + " Y: " + newKingY);
                            Gdx.app.log("Rook Old Location", "X: " + oldRookX + " Y: " + oldRookY);
                            Gdx.app.log("Rook New Location", "X: " + newRookX + " Y: " + newRookY);

                            if( ChessGame.isWhite ){
                                Piece king = ChessBoard.getPieceAt(oldKingY, Math.abs(oldKingX - 7));
                                Gdx.app.log("King selected", king.name + " oldX: " + king.x + " oldY: " + king.y);
                                Piece rook = ChessBoard.getPieceAt(oldRookY, Math.abs(oldRookX - 7));
                                Gdx.app.log("Rook selected", rook.name + " oldX: " + rook.x + " oldY: " + rook.y);
                                BoardController.movePiece(king, newKingY, Math.abs(newKingX - 7));
                                BoardController.movePiece(rook, newRookY, Math.abs(newRookX - 7));
                            }else{
                                Piece king = ChessBoard.getPieceAt(Math.abs(oldKingY - 7), oldKingX);
                                Gdx.app.log("King selected", king.name + " oldX: " + king.x + " oldY: " + king.y);
                                Piece rook = ChessBoard.getPieceAt(Math.abs(oldRookY - 7), oldRookX);
                                Gdx.app.log("Rook selected", rook.name + " oldX: " + rook.x + " oldY: " + rook.y);
                                BoardController.movePiece(king, Math.abs(newKingY - 7), newKingX);
                                BoardController.movePiece(rook, Math.abs(newRookX - 7), newRookY);
                            }
                        //None castling moves
                        }else{
                            int newX = response.charAt(15) - '0';
                            int newY = response.charAt(16) - '0';
                            int oldY = response.charAt(17) - '0';
                            int oldX = response.charAt(18) - '0';
                            Gdx.app.log("Location", "OLDX: " + oldX + " OLDY: " + oldY + " NEWY: " + newY + " NEWX: " + newX);
                            if( ChessGame.isWhite ){
                                Piece piece = ChessBoard.getPieceAt(oldY, Math.abs(oldX-7));
                                Gdx.app.log("Piece", piece.name + " oldY: " + piece.y + " oldX: " + piece.x);
                                BoardController.movePiece(piece, newY, Math.abs(newX-7));
                            }else{
                                Piece piece = ChessBoard.getPieceAt(Math.abs(oldY-7), oldX);
                                Gdx.app.log("Piece", piece.name + " oldY: " + piece.y + " oldX: " + piece.x);
                                BoardController.movePiece(piece, Math.abs(newY-7), newX);
                            }
                        }
                    } else if (response.contains("VICTORY")) {
                        //break;
                    } else if (response.contains("DEFEAT")) {
                        //break;
                    } else if (response.contains("TIE")) {
                        //break;
                    } else if (response.contains("MESSAGE")) {
                    } else if(response.contains("BACK")) {
                        ingame = false;
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                game.setScreen(new CurrentGamesScreen(game));
                            }
                        });
                    }
                }

                //TODO Implement ability to quit out of loop by sending server a message
                while(!ingame && !observe) {
                    response = input.readLine();
                    System.out.println("(FALSE LOOP) SOCKET INPUT: " + response);
                    String color = response.substring(response.length()-1);
                    if( response.contains("WELCOME") ){
                        response = input.readLine();
                        System.out.println("(FALSE LOOP) SOCKET INPUT: " + response);
                        if( color.equals("W") ){
                            ChessGame.isWhite = true;
                            ingame = true;
                            System.out.println("Is White: " + ChessGame.isWhite);
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    game.setScreen(new ChessBoardScreen(game));
                                }
                            });
                        }else{
                            ChessGame.isWhite = false;
                            ingame = true;
                            System.out.println("Is White: " + ChessGame.isWhite);
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    game.setScreen(new ChessBoardScreen(game));
                                }
                            });
                        }
                    }else if( response.contains("FRIENDLIST") ){
                        FriendsList.parseFriendsList(response.substring(15));
                    }else if( response.contains("ANNOUNCEMENTS") ){
                        Announcements.parseAnnouncements(response);
                    }else if( response.contains("LEADERBOARD") ){
                        Leaderboards.parseLeaderboard(response.substring(12));
                    }else if( response.contains("CURRGAMES-OBSERVER") ){
                        observe = true;
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                game.setScreen(new ObserverScreen(game));
                            }
                        });
                    }else if( response.contains("CURRGAMES") ){
                        CurrentGames.parseGames(response);
                    }else if(response.contains("SETTINGS-USER-SUCCESS")) {

                    }else if(response.contains("SETTINGS-USER-FAIL")) {

                    }else if(response.contains("SETTINGS-EMAIL-SUCCESS")) {
                        int charLoc = response.indexOf(',');
                        ChessGame.email = response.substring(charLoc+1);
                        System.out.println(ChessGame.email);
                    }else if(response.contains("SETTINGS-EMAIL-FAIL")) {

                    }
                }

                //True once you start observing a game
                while(observe) {
                    response = input.readLine();

                    if( response.contains("OBSERVE-BACK") ){
                        observe = false;
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                game.setScreen(new CurrentGamesScreen(game));
                            }
                        });
                    }else if( response.contains("CURRGAMES-OBSERVER") ){
                        int index = response.indexOf('[');
                        if( board.equals("DEFAULT") || !board.equals(response.substring(index+1, response.length()-1)) ){
                            System.out.println("(OBSERVE LOOP) SOCKET INPUT: " + response);
                            System.out.println("Board changed!");
                            Observer.parseBoard(response);
                            index = response.indexOf('[');
                            board = response.substring(index+1, response.length()-1);
                            boardUpdated = true;
                            Timer.schedule(new Timer.Task(){
                                @Override
                                public void run() {
                                    //Wait to receive current games list from server
                                    boardUpdated = false;
                                }
                            }, .25f);
                        }
                    }
                }
            }
            //Closes the client socket connection on the server
            //output.println("QUIT");
        }catch (Exception e){
            Gdx.app.log("Client Exception", e.getMessage());
        }finally{
            //Send message to server to pause communication between game
            try {
                socket.close();
            }catch (Exception e) {
                Gdx.app.log("Exception Closing Socket", e.getMessage());
            }
        }
    }

    /**
     * Send the FORFEIT tag to the server
     */
    public void sendForfeit(){
        output.println("DRAW ");
    }

    /**
     * Sends your token to the server
     */
    public void sendToken(){
        output.println("TOKEN " + ChessGame.token);
    }

    /**
     * Sends START to the server to find a casual game
     */
    public void findCasualGame() {
        output.println("CASSTART");
    }

    /**
     * Sends START to the server to find a ranked game
     */
    public void findRankedGame() {
        output.println("RANKSTART");
    }

    /**
     * Sends MOVE piece/tile x and piece y to the server
     */
    public void sendMove(int x, int y) {
        output.println("MOVE " + x + y);
    }

    /**
     * Sends FRIENDSLIST to the server to retrieve the friendslist
     */
    public void loadFriends() {
        output.println("FRIENDLIST");
    }

    /**
     * Sends LEADERBOARD to the server to retrieve the leaderboard data
     */
    public void loadLeaderboard() {
        output.println("LEADERBOARD");
    }

    /**
     * Sends ANNOUNCEMENTS to the server to retrieve the announcements data
     */
    public void loadAnnouncements() {
        output.println("ANNOUNCEMENTS");
    }

    /**
     * Sends QUIT to the server the exit out of a game
     */
    public void quitGame() {
        output.println("QUIT");
    }

    /**
     * Sends CURRGAMES to the server, if you are a MOD or ADMIN
     * to retrieve current games being played
     */
    public void loadGames() {
        output.println("CURRGAMES");
    }

    /**
     * Sends CURRGAMES-OBSERVER-[CHESSBOARDID] to the server in order
     * to watch a game
     * @param chessboardID The chessboard ID of the game you want ot observe
     */
    public void observeGame(int chessboardID) {
        output.println("CURRGAMES-OBSERVER-" + chessboardID);
    }

}
