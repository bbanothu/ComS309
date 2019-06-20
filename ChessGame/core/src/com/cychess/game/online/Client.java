package com.cychess.game.online;

import com.badlogic.gdx.Gdx;
import com.cychess.game.ChessGame;
import com.cychess.game.controller.BoardController;
import com.cychess.game.logic.ChessBoard;
import com.cychess.game.logic.Piece;
import com.cychess.game.model.FriendsList;
import com.cychess.game.screens.ChessBoardScreen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * A runnable class that loops through on its own waiting for commands to be sent
 * or received from the socket connection.
 * Created by PJ on 3/24/2018.
 */
public class Client implements Runnable {
    private ChessGame game;

    /**
     * The socket used by the client to send and recieve information from the server
     */
    public Socket clientSocket;

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
     * Creates the socket connection and setups the input and output streams
     * @param game the game object, used to interact with or set screens
     * @throws IOException Exception could be caused by client not finding URL/PORT
     */
    public Client(ChessGame game) throws IOException {
        this.game = game;
        clientSocket = new Socket(ChessGame.socketURL, ChessGame.port);
        input = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
        output = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    /**
     * Loops through for checking for commands regarding game logic.
     */
    public void run() {
        String response;
        try {
            response = input.readLine();
            Gdx.app.log("Client", response );
            //Gdx.app.log("Client", response.substring(response.length()-1) );

            if( response.contains("WELCOME")){
                if( response.substring(response.length()-1).equals("W") ){
                    ChessGame.isWhite = true;
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            game.setScreen(new ChessBoardScreen(game));
                        }
                    });
                }else{
                    ChessGame.isWhite = false;
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            game.setScreen(new ChessBoardScreen(game));
                        }
                    });
                }
                Gdx.app.log("Color", "" + ChessGame.isWhite);
                while (true) {
                    response = input.readLine();
                    Gdx.app.log("Client", response );

                    if (response.contains("VALID_MOVE")) {
                        BoardController.movePiece(ChessBoard.selectedPiece, targetX, targetY);
                    } else if (response.contains("OPPONENT_MOVED")) {
                        int newY = response.charAt(15) - '0';
                        int newX = response.charAt(16) - '0';
                        int oldY = response.charAt(17) - '0';
                        int oldX = response.charAt(18) - '0';
                        Gdx.app.log("Location", "OLDX: " + oldX + " OLDY: " + oldY + " NEWY: " + newY + " NEWX: " + newX);
                        if( ChessGame.isWhite ){
                            Piece piece = ChessBoard.getPieceAt(oldX, Math.abs(oldY-7));
                            Gdx.app.log("Piece", piece.name + " Y: " + piece.y + " X: " + piece.x);
                            BoardController.movePiece(piece, newX, Math.abs(newY-7));
                        }else{
                            Piece piece = ChessBoard.getPieceAt(oldX, oldY);
                            Gdx.app.log("Piece", piece.name + " Y: " + piece.y + " X: " + piece.x);
                            BoardController.movePiece(piece, newX, newY);
                        }
                    } else if (response.contains("VICTORY")) {
//                        messageLabel.setText("You win");
                        break;
                    } else if (response.contains("DEFEAT")) {
//                        messageLabel.setText("You lose");
                        break;
                    } else if (response.contains("TIE")) {
//                        messageLabel.setText("You tied");
                        break;
                    } else if (response.contains("MESSAGE")) {
//                        messageLabel.setText(response.substring(8));
                    }
                }
                output.println("QUIT");
            }else{
                if( response.contains("FRIENDLIST") ){
                    FriendsList.parseFriendsList(response.substring(15));
                }
            }
        } catch (Exception e) {

        } finally {
            try{

                clientSocket.close();
            } catch (IOException e) {

            }
        }
    }

    private void parseValidMoves(String moves) {
        moves = moves.substring(moves.length()-71, moves.length());
        Scanner s = new Scanner(moves);

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(s.hasNextInt()) {
                    BoardController.validMoves[i][j] = s.nextInt();
                }
            }
        }
        s.close();
    }

//    public void sendMove(String name, int x, int y){
//        Gdx.app.log("Client MOVE", name + ": Y: " + y + " X: " + x);
//        output.println("MOVE " + x + y);
//    }

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
}
