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
import com.cychess.game.online.SocketClient;
import com.cychess.game.screens.CurrentGamesScreen;
import com.cychess.game.screens.LoginScreen;
import com.cychess.game.screens.SignupScreen;

/** Main page holds 4 buttons: Change username, change password, change email, delete user.
 * Each subpage holds the text boxes for their subject, a SUBMIT button and a BACK button to return here
 * Created by PJ on 4/24/2018.
 * Edited by Kienan 4/24/2018.
 */

public class Settings extends Table{

    private ChessGame game;


    private TextButton changeEmailBtn;

    private TextButton changePasswordBtn;

    private TextButton changeUsernameBtn;

    private TextButton deleteAccountBtn;

    /**
     * Used to change the username
     */
    private TextField changeUsername;
    /**
     * Used to change the username
     */
    private TextField changeUsername1;
    /**
     * Used to change the password
     */
    private TextField changePassword;
    /**
     * Used to change the password
     */
    private TextField changePassword1;
    /**
     * Used to change the password
     */
    private TextField changePassword2;
    /**
     * Used to change the email
     */
    private TextField email;
    /**
     * Used to change the email
     */
    private TextField email1;
    /**
     * Used to delete account
     */
    private TextField username;
    /**
     * Used to delete account
     */
    private TextField password;

    public Settings(ChessGame game) {
        setBounds(0, ChessGame.SCREEN_Y, ChessGame.VWIDTH, ChessGame.VWIDTH);
        this.game = game;
        setup();
        //setDebug(true);
    }

    private void setup() {
        //Creates the change email button
        changeEmailBtn = new TextButton("Change Email", Assets.skin);

        //Create the change password button
        changePasswordBtn = new TextButton("Change Password", Assets.skin);

        //Creates the change username button
        changeUsernameBtn = new TextButton("Change Username", Assets.skin);

        //Creates the delete account button
        deleteAccountBtn = new TextButton("Delete Account", Assets.skin);

        //Create the username textfield
        changeUsername = new TextField("", Assets.skin);
        changeUsername.setMessageText("new username");
        changeUsername.setOrigin(Align.left);

        //Create the username1 textfield
        changeUsername1 = new TextField("", Assets.skin);
        changeUsername1.setMessageText("retype new username");
        changeUsername1.setOrigin(Align.left);

        //Creates the password textfield
        changePassword = new TextField("", Assets.skin);
        changePassword.setMessageText("old password");
        changePassword.setOrigin(Align.right);

        //Creates the password1 textfield
        changePassword1 = new TextField("", Assets.skin);
        changePassword1.setMessageText("new password");
        changePassword1.setOrigin(Align.right);

        //Creates the password2 textfield
        changePassword2 = new TextField("", Assets.skin);
        changePassword2.setMessageText("retype new password");
        changePassword2.setOrigin(Align.right);

        //Creates the email textfield
        email = new TextField("", Assets.skin);
        email.setMessageText("new email");
        email.setOrigin(Align.left);

        //Creates the email1 textfield
        email1 = new TextField("", Assets.skin);
        email1.setMessageText("retype new email");
        email1.setOrigin(Align.left);

        //Creates the username textfield
        username = new TextField("", Assets.skin);
        username.setMessageText("username");
        username.setOrigin(Align.right);

        //Creates the password textfield
        password = new TextField("", Assets.skin);
        password.setMessageText("password");
        password.setOrigin(Align.right);

        add(changeUsername).expandX().width(ChessGame.VWIDTH/2);
        add(email).expandX().width(ChessGame.VWIDTH/2);
        row();
        add(changeUsername1).expandX().width(ChessGame.VWIDTH/2);
        add(email1).expandX().width(ChessGame.VWIDTH/2);
        row();
        add(changeUsernameBtn).width(ChessGame.VWIDTH/2).expandX();
        add(changeEmailBtn).expandX().width(ChessGame.VWIDTH/2);
        row();
        add(changePassword).expandX().width(ChessGame.VWIDTH/2);
        add(username).expandX().width(ChessGame.VWIDTH/2);
        row();
        add(changePassword1).expandX().width(ChessGame.VWIDTH/2);
        add(password).expandX().width(ChessGame.VWIDTH/2);
        row();
        add(changePassword2).expandX().width(ChessGame.VWIDTH/2);
        add(deleteAccountBtn).expandX().width(ChessGame.VWIDTH/2);
        row();
        add(changePasswordBtn).expandX().width(ChessGame.VWIDTH/2);
        add().expandX().width(ChessGame.VWIDTH/2);

        changeEmailBtnListener();
        changePasswordBtnListener();
        changeUsernameBtnListener();
        deleteAccountBtnListener();
    }

    private void changeEmailBtnListener() {
        changeEmailBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                ChessGame.client.output.println("SETTINGS-EMAIL," + email.getText() + "," + email1.getText() + "," + ChessGame.email);
            }
        });
    }

    private void changePasswordBtnListener() {
        changePasswordBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                String url = ChessGame.url + "/change_password";

                HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
                Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST).content("old=" + changePassword.getText() + "&new=" + changePassword1.getText() + "&new1=" + changePassword2.getText() + "&email=" + ChessGame.email).url(url).build();
                Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
                    @Override
                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        String response = httpResponse.getResultAsString();
                        Gdx.app.log("Response", response);

                        if( response.substring(0, 7).equals("SUCCESS") ){
                            Gdx.app.exit();
                        }
                    }

                    @Override
                    public void failed(Throwable t) {
                        Gdx.app.log("FAILED", "MSG: " + t.getMessage() + " CAUSE: " + t.getCause());
                    }

                    @Override
                    public void cancelled() {

                    }
                });
            }
        });
    }
    
   /* private void changePasswordBtnListener() {
        changePasswordBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                String url = ChessGame.url + "/change_password";

                HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
                Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST).content("old=" + changePassword.getText() + "&new=" + changePassword1.getText() + "&new1=" + changePassword2.getText() + "&email=" + ChessGame.email).url(url).build();
                Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
                    @Override
                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        String response = httpResponse.getResultAsString();
                        Gdx.app.log("Response", response);

                        if( response.substring(0, 7).equals("SUCCESS") ){

                        }
                    }

                    @Override
                    public void failed(Throwable t) {
                        Gdx.app.log("FAILED", "MSG: " + t.getMessage() + " CAUSE: " + t.getCause());
                    }

                    @Override
                    public void cancelled() {

                    }
                });
            }
        });
    }*/

    private void changeUsernameBtnListener() {
        changeUsernameBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                ChessGame.client.output.println("SETTINGS-USER," + changeUsername.getText() + "," + changeUsername1.getText() + "," + ChessGame.email);
            }
        });
    }

    private void deleteAccountBtnListener() {
        deleteAccountBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                String url = ChessGame.url + "/delete_user";

                HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
                Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST).content("new=" + username.getText() + "&new1=" + password.getText() + "&email=" + ChessGame.email).url(url).build();
                Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
                    @Override
                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        String response = httpResponse.getResultAsString();
                        Gdx.app.log("Response", response);

                        if( response.substring(0, 7).equals("SUCCESS") ){

                        }
                    }

                    @Override
                    public void failed(Throwable t) {
                        Gdx.app.log("FAILED", "MSG: " + t.getMessage() + " CAUSE: " + t.getCause());
                    }

                    @Override
                    public void cancelled() {

                    }
                });
            }
        });
    }
   /* private void deleteAccountBtnListener() {
        deleteAccountBtn.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                String url = ChessGame.url + "/delete_user";

                HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
                Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST).content("new=" + username.getText() + "&new1=" + password.getText() + "&email=" + ChessGame.email).url(url).build();
                Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
                    @Override
                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        String response = httpResponse.getResultAsString();
                        Gdx.app.log("Response", response);

                        if( response.substring(0, 7).equals("SUCCESS") ){

                        }
                    }

                    @Override
                    public void failed(Throwable t) {
                        Gdx.app.log("FAILED", "MSG: " + t.getMessage() + " CAUSE: " + t.getCause());
                    }

                    @Override
                    public void cancelled() {

                    }
                });
            }
        });
    }*/
}
