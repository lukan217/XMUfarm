package com.javakaihua.farmgame.view.login;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.javakaihua.farmgame.model.User;
import com.javakaihua.farmgame.utils.Check;
import com.javakaihua.farmgame.utils.DataManage;

public class LoginUI extends Group {
    //登录界面

    private Skin skin;

    private Table loginTable;
    private Table buttonTable;

    public TextField getUserIDTextField() {
        return userIDTextField;
    }

    public TextField getPasswordTextField() {
        return passwordTextField;
    }

    private Label userIDLabel;
    private Label passwordLabel;
    private TextField userIDTextField;
    private TextField passwordTextField;
    private CheckBox remember;
    private User user;


    private TextButton loginButton;
    private TextButton registerButton;

    public LoginUI(Skin skin){
        this.skin=skin;

        userIDLabel=new Label("账号:", skin);
        userIDLabel.setAlignment(Align.left);
        passwordLabel=new Label("密码:",skin);
        passwordLabel.setAlignment(Align.left);

        userIDTextField=new TextField("",skin);
        userIDTextField.setAlignment(Align.center);

        passwordTextField=new TextField("",skin);
        passwordTextField.setAlignment(Align.center);
        passwordTextField.setWidth(400);
        passwordTextField.setPasswordMode(true);
        passwordTextField.setPasswordCharacter('*');

        remember=new CheckBox("记住密码",skin);

        if(Check.checkLocalUserExist())
        {
            remember.setChecked(true);
            user=DataManage.readUserMsg();
            userIDTextField.setText(user.getUserID());
            passwordTextField.setText(user.getPass());
        }

        loginButton=new TextButton("登录", skin);
        registerButton=new TextButton("注册", skin);


        loginTable=new Table();
        buttonTable=new Table();

        loginTable.add(userIDLabel).padBottom(50).padRight(50).padTop(100);
        loginTable.add(userIDTextField).width(250).padBottom(50).padTop(100);
        loginTable.row();
        loginTable.add(passwordLabel).padBottom(50).padRight(50);
        loginTable.add(passwordTextField).width(250).padBottom(50);
        loginTable.row();
        loginTable.add(remember).colspan(2).padLeft(100).padBottom(80);

        buttonTable.add(loginButton);
        buttonTable.add(registerButton).padLeft(100);

        this.addActor(loginTable);
        loginTable.setPosition(0,0);
        this.addActor(buttonTable);
        buttonTable.setPosition(0,-200);
        this.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);


    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextButton getRegisterButton() {
        return registerButton;
    }

    public CheckBox getRemember(){
        return remember;
    }



}
