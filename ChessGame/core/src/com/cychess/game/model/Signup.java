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
import com.cychess.game.screens.LoginScreen;
import com.cychess.game.screens.SignupScreen;

/**
 * Creates the signup table. Handles all the interacts a user can do on the signup screen.
 * Created by PJ on 3/24/2018.
 */
public class Signup extends Table {

    private ChessGame game;

    /**
     * Textfield to enter your email
     */
    public TextField email;
    /**
     * Textfield to enter your password
     */
    public TextField password;
    /**
     * Textfield to reenter your password
     */
    public TextField password1;
    /**
     * Textfield to enter your username
     */
    public TextField username;
    /**
     * Textfield to enter your full anme
     */
    public TextField name;

    /**
     * @return The text entered in the email textfield
     */
    public String getEmail() {
        return email.getText();
    }

    /**
     * @return The text entered in the password textfield
     */
    public String getPassword() {
        return password.getText();
    }

    /**
     * @return The text entered in the password1 textfield
     */
    public String getPassword1() {
        return password1.getText();
    }

    /**
     * @return The text entered in the username textfield
     */
    public String getUsername() {
        return username.getText();
    }

    /**
     * @return The text entered in the fullname textfield
     */
    public String getFullName() {
        return name.getText();
    }

    /**
     * Called by the SignupScreen. Sets the bounds for the table,
     * which is what is visually shown on screen.
     * @param game
     */
    public Signup(ChessGame game) {
        setBounds(0, 0, ChessGame.VWIDTH, ChessGame.VHEIGHT);
        this.game = game;
        //setDebug(true);
    }

    /**
     * Sets up the signup screen
     */
    public void setup() {
        addNameEntry();
        this.row();
        addUsernameEntry();
        this.row();
        addEmailEntry();
        this.row();
        addPasswordEntry();
        this.row();
        addPassword1Entry();
        this.row();
        addSignupBtn();
        this.row();
        addBackBtn();
    }

    /**
     * The button listener for the signupBtn that is called when the
     * loginBtn is clicked. Using a POST request it connects to the
     * server by sending your email, username, name, and password.
     * @param signupBtn
     */
    public void signupBtnListener(TextButton signupBtn) {
        signupBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                //Check to see if the fields are filled in
                if( !getFullName().isEmpty() && !getUsername().isEmpty() && !getEmail().isEmpty() && !getPassword().isEmpty()){
                    if( getEmail().substring(getEmail().length()-11, getEmail().length()).equals("iastate.edu") ){
                        //Make sure password is 5 characters or longer
                        if( getPassword().length() < 5){
                            SignupScreen.errorCode = 5;

                            return;
                        }

                        //Make sure passwords match
                        if( !getPassword().equals(getPassword1()) ){
                            SignupScreen.errorCode = 6;
                            Gdx.app.log("Error Code", "6");
                            return;
                        }

                        String url = ChessGame.url + "/signup";
                        Gdx.app.log("URL", url);
                        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
                        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST).content("email=" + getEmail() + "&name=" + getFullName() + "&username=" + getUsername() + "&password=" + getPassword()).url(url).build();
                        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
                            @Override
                            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                                String response = httpResponse.getResultAsString();
                                Gdx.app.log("Response", response);

                                if( response.substring(0, 7).equals("SUCCESS") ){
                                    ChessGame.token = response.substring(8, response.length());
                                    SignupScreen.errorCode = 8;
                                    //Saves the token so it can be used next time the player logs in
                                    Preferences prefs = Gdx.app.getPreferences(getEmail());
                                    prefs.putString("token", ChessGame.token);
                                    prefs.flush();
                                    Gdx.app.log("token", ChessGame.token);
                                }else if( response.equals("EMAIL EXISTS") ){
                                    SignupScreen.errorCode = 2;
                                    Gdx.app.log("Error Code", "2");
                                }else if( response.equals("USERNAME EXISTS") ){
                                    SignupScreen.errorCode = 3;
                                    Gdx.app.log("Error Code", "3");
                                }else if( response.equals("FAILED") ){
                                    SignupScreen.errorCode = 7;
                                    Gdx.app.log("Error Code", "4");
                                }
                            }

                            @Override
                            public void failed(Throwable t) {
                                Gdx.app.log("SIGNUP", "was NOT successful! " + t.getMessage());
                                SignupScreen.errorCode = 7;
                            }

                            @Override
                            public void cancelled() {
                                Gdx.app.log("SIGNUP", "was cancelled!");
                            }
                        });
                    }else{
                        SignupScreen.errorCode = 4;
                    }
                }else{
                    SignupScreen.errorCode = 1;
                }
            }
        });
    }

    /**
     * Creates and adds the name textfield to the screen
     */
    private void addNameEntry() {
        //Adds the name textfield to the screen
        name = new TextField("", Assets.skin);
        name.setMessageText("full name");
        name.setOrigin(Align.center);
        this.add(name).width(ChessGame.VWIDTH/2);
    }

    /**
     * Creates and adds the usrename textfield to the screen
     */
    private void addUsernameEntry() {
        //Adds the username textfield to the screen
        username = new TextField("", Assets.skin);
        username.setMessageText("username");
        username.setOrigin(Align.center);
        this.add(username).width(ChessGame.VWIDTH/2);
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
     * Creates and adds the password textfield to the screen
     */
    private void addPassword1Entry() {
        //Adds the password textfield to the screen
        password1 = new TextField("", Assets.skin);
        password1.setMessageText("password");
        password1.setOrigin(Align.center);
        password1.setPasswordMode(true);
        password1.setPasswordCharacter('*');
        this.add(password1).width(ChessGame.VWIDTH/2);
    }

    /**
     * Creates and adds the signup button to the screen
     */
    private void addSignupBtn() {
        //Adds the signup button to the screen
        TextButton signupBtn = new TextButton("Signup", Assets.skin);
        signupBtnListener(signupBtn);
        this.add(signupBtn).width(ChessGame.VWIDTH/2);
    }

    /**
     * Creates and adds the back button to the screen
     */
    private void addBackBtn() {
        //Adds the back button to the screen
        TextButton backBtn = new TextButton("Back", Assets.skin);
        backBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new LoginScreen(game));
            }
        });
        this.add(backBtn).width(ChessGame.VWIDTH/2);
    }
}
