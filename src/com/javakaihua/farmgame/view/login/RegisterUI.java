package com.javakaihua.farmgame.view.login;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

public class RegisterUI extends Group {

    //注册界面组成元素

    private Table buttonTable;

    //标签
    private Label passwordLabel;
    private Label verifyPassLabel;
    private Label userIDLabel;
    private Label passpow;

    //文本框
    private TextField userIDTextField;
    private TextField passwordTextField;// 文本框（密码）
    private TextField verifyPassTextField;//文本框（确认密码）

    //按钮
    private TextButton registerButton;
    private TextButton cancelButton;

    //密码强度图片
    private Image pass1;
    private Image pass2;
    private Image pass3;
    private Image pass4;




    public RegisterUI(Skin skin) {

        //初始化各个label
        userIDLabel = new Label("账号:", skin);
        passwordLabel = new Label("密码:", skin);
        passpow=new Label("密码强度:",skin);
        verifyPassLabel = new Label("确认密码:", skin);


        //注册界面四个文本框：账号+用户名+密码+确认密码
        userIDTextField = new TextField("", skin);
        userIDTextField.setAlignment(Align.center);
        userIDTextField.setSize(300,50);
        passwordTextField = new TextField("", skin);
        passwordTextField.setAlignment(Align.center);
        passwordTextField.setSize(300,50);
        verifyPassTextField = new TextField("", skin);
        verifyPassTextField.setAlignment(Align.center);
        verifyPassTextField.setSize(300,50);

        pass1=new Image(new Texture(Gdx.files.internal( "assets/image/other/pass1.png")));
        pass2=new Image(new Texture(Gdx.files.internal( "assets/image/other/pass2.png")));
        pass3=new Image(new Texture(Gdx.files.internal( "assets/image/other/pass3.png")));
        pass4=new Image(new Texture(Gdx.files.internal( "assets/image/other/pass4.png")));

        // 用于显示密码的文本框, 需要将文本框设置为密码模式
        passwordTextField.setPasswordMode(true);
        verifyPassTextField.setPasswordMode(true);
        // 显示密码时用 * 号代替密码字符
        passwordTextField.setPasswordCharacter('*');
        verifyPassTextField.setPasswordCharacter('*');
        userIDTextField.setMessageText("5-12位，包含字母或数字");
        passwordTextField.setMessageText("密码长度应大于5位");
        verifyPassTextField.setMessageText("密码长度应大于5位");

        registerButton = new TextButton("注册", skin);
        registerButton.setTransform(true);
        cancelButton = new TextButton("取消", skin);
        cancelButton.setTransform(true);

        buttonTable = new Table();

        buttonTable.add(registerButton);
        buttonTable.add(cancelButton).padLeft(100).padRight(80);
        buttonTable.setPosition(50, -350);

        userIDLabel.setPosition(-250,100);
        userIDTextField.setPosition(-100,100);
        passwordLabel.setPosition(-250,0);
        passwordTextField.setPosition(-100,0);
        passpow.setPosition(-250,-100);
        pass1.setPosition(-100,-100);
        pass2.setPosition(-100,-100);
        pass3.setPosition(-100,-100);
        pass4.setPosition(-100,-100);
        verifyPassLabel.setPosition(-250,-200);
        verifyPassTextField.setPosition(-100,-200);
        pass2.setVisible(false);
        pass3.setVisible(false);
        pass4.setVisible(false);

        this.addActor(userIDLabel);
        this.addActor(userIDTextField);
        this.addActor(passwordLabel);
        this.addActor(passwordTextField);
        this.addActor(passpow);
        this.addActor(pass1);
        this.addActor(pass2);
        this.addActor(pass3);
        this.addActor(pass4);
        this.addActor(verifyPassLabel);
        this.addActor(verifyPassTextField);
        this.addActor(buttonTable);
        this.setVisible(false);
        this.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

    }

    public TextButton getRegisterButton() {
        return registerButton;
    }

    public TextButton getCancelButton() {
        return cancelButton;
    }

    public TextField getUserIDTextField() {
        return userIDTextField;
    }

    public TextField getPasswordTextField() {
        return passwordTextField;
    }

    public TextField getVerifyPassTextField() {
        return verifyPassTextField;
    }
    public  Image getPass1(){
        return pass1;
    }
    public  Image getPass2(){
        return pass2;
    }
    public Image getPass3(){
        return pass3;
    }
    public Image getPass4(){
        return pass4;
    }
}
