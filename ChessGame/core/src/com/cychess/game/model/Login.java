package com.cychess.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.cychess.game.Assets;
import com.cychess.game.ChessGame;
import com.cychess.game.online.Client;
import com.cychess.game.online.SocketClient;
import com.cychess.game.screens.CurrentGamesScreen;
import com.cychess.game.screens.LoginScreen;
import com.cychess.game.screens.SignupScreen;

import java.io.IOException;

/**
 * Creates the login table. Handles all the interacts a user can do on the login screen.
 * Created by PJ on 3/24/2018.
 */
public class Login extends Table {

    private ChessGame game;

    /**
     * Email textfield
     */
    public TextField email;

    /**
     * Password textfield
     */
    public TextField password;

    /**
     * @return The text entered in the email textfield
     */
    public String getEmail(){
        return email.getText();
    }

    /**
     * @return The text entered in the password textfield
     */
    public String getPassword(){
        return password.getText();
    }

    /**
     * Called by the LoginScreen. Sets the bounds for the table,
     * which is what is visually shown on screen.
     * @param game
     */
    public Login(ChessGame game) {
        setBounds(0, 0, ChessGame.VWIDTH, ChessGame.VHEIGHT);
        this.game = game;
        //setDebug(true);
    }

    /**
     * Sets up the login screen
     */
    public void setup() {
        addEmailEntry();
        this.row();
        addPasswordEntry();
        this.row();
        addLoginBtn();
        this.row();
        addSignupBtn();

        //TODO temporary, remove later
        email.setText("pjd@iastate.edu");
        password.setText("12345");
    }

    /**
     * The button listener for the loginBtn that is called when the
     * loginBtn is clicked. Using a POST request it connects to the
     * server by sending your email and password.
     * @param loginBtn
     */
    public void loginBtnListener(TextButton loginBtn) {
        loginBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                //Check to make sure the fields are filled in
                if( !getEmail().isEmpty() && !getPassword().isEmpty() ){
                    //Get the token that was saved
                    Preferences prefs = Gdx.app.getPreferences(getEmail());
                    ChessGame.token = prefs.getString("token");
                    Gdx.app.log("token", ChessGame.token);

                    String url = ChessGame.url + "/signin";
                    Gdx.app.log("URL", url);
                    Gdx.app.log("EMAIL", getEmail());
                    Gdx.app.log("PASSWORD", getPassword());
                    HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
                    Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST).content(/*"token=" + ChessGame.token +*/ "email=" + getEmail() + "&password=" + getPassword()).url(url).build();
                    Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
                        @Override
                        public void handleHttpResponse(Net.HttpResponse httpResponse) {
                            String response = httpResponse.getResultAsString();
                            Gdx.app.log("Response", response);

                            if( response.substring(0, 7).equals("SUCCESS") ){
                                ChessGame.token = response.substring(8, 28);
                                Preferences prefs = Gdx.app.getPreferences(getEmail());
                                prefs.putString("token", ChessGame.token);
                                prefs.flush();
                                ChessGame.username = response.substring(29, response.length() - 2);
                                ChessGame.role = Integer.parseInt(response.substring(response.length()-1));
                                ChessGame.email = getEmail();
                                //TODO REMOVE SYSOUT
                                System.out.println("TOKEN: " + ChessGame.token);
                                System.out.println("USERNAME: " + ChessGame.username);
                                System.out.println("EMAIL: " + ChessGame.email);
                                System.out.println("ROLE: " + ChessGame.role);

                                try {
                                    ChessGame.client = new SocketClient(game);
                                } catch (Exception e) {
                                    Gdx.app.log("ERROR", "Client failed to connect to socket!");
                                }

                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        game.setScreen(new CurrentGamesScreen(game));
                                    }
                                });
                                ChessGame.client.sendToken();
                                ChessGame.client.run();
                            }else if( response.equals("EMAIL FAILED") || response.equals("PASSWORD FAILED") ){
                                LoginScreen.errorCode = 2;
                            }else if( response.equals("FAILED") ){
                                LoginScreen.errorCode = 3;
                            }
                        }

                        @Override
                        public void failed(Throwable t) {
                            Gdx.app.log("SIGNIN", "was NOT successful! " + t.getMessage() + " Cause: " + t.getCause());
                        }

                        @Override
                        public void cancelled() {
                            Gdx.app.log("SIGNIN", "was cancelled!");
                        }
                    });
                }else{
                    LoginScreen.errorCode = 1;
                }
            }
        });
    }

    /**
     * Creates and adds the email textfield to the screen
     */
    private void addEmailEntry() {
        //Adds the email textfield to the screen
        email = new TextField("", Assets.skin);
        email.setMessageText("email");
        email.setOrigin(Align.center);
        this.add(email).width(ChessGame.VWIDTH/2);
    }

    /**
     * Creates and adds the password textfield to the screen
     */
    private void addPasswordEntry() {
        //Adds the password textfield to the screen
        password = new TextField("", Assets.skin);
        password.setMessageText("password");
        password.setOrigin(Align.center);
        password.setPasswordMode(true);
        password.setPasswordCharacter('*');
        this.add(password).width(ChessGame.VWIDTH/2);
    }

    /**
     * Creates and adds the login button to the screen
     */
    private void addLoginBtn() {
        //Adds the login button to the screen
        TextButton loginBtn = new TextButton("Login", Assets.skin);
        loginBtnListener(loginBtn);
        this.add(loginBtn).width(ChessGame.VWIDTH/2);
    }

    /**
     * Creates and adds the signup button to the screen
     */
    private void addSignupBtn() {
        //Adds the signup button to the screen
        TextButton signupBtn = new TextButton("Signup", Assets.skin);
        signupBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new SignupScreen(game));
            }
        });
        this.add(signupBtn).width(ChessGame.VWIDTH/2);
    }
}
