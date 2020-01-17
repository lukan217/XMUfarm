package com.javakaihua.farmgame.view.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.javakaihua.farmgame.view.PlayScreen;

public class Changewindow extends Window {
    //修改人物信息的界面

    private Label password;
    private Label verifypass;
    private Label username;
    private Label pic;
    private Label passpow;
    private TextField passwordfield;
    private TextField verifypassfield;
    private TextField usernamefield;
    private TextButton yes;
    private TextButton no;
    private ImageButton X;
    private Image img1,img2,img3,img4,img5,img6;
    private Image pass1,pass2,pass3,pass4;
    private int imageFlag=0;
    private PlayScreen playScreen;
    public Changewindow(String title, Skin skin, PlayScreen playScreen) {
        super(title, skin,"window1");
        this.playScreen=playScreen;
        this.setModal(true);
        this.setVisible(true);
        this.setSize(950,800);
        this.setPosition(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/4);
        username=new Label("昵称:",skin);
        pic=new Label("头像:",skin);
        password=new Label("新密码:",skin);
        verifypass=new Label("确认密码:",skin);
        passpow=new Label("密码强度:",skin);
        passwordfield=new TextField("",skin);
        passwordfield.setSize(250,50);
        passwordfield.setAlignment(Align.center);
        verifypassfield=new TextField("",skin);
        verifypassfield.setSize(250,50);
        verifypassfield.setAlignment(Align.center);
        usernamefield=new TextField("",skin);
        usernamefield.setSize(250,50);
        usernamefield.setAlignment(Align.center);
        yes=new TextButton("确定",skin);
        no=new TextButton("取消",skin);
        X=new ImageButton(skin,"win1");
        img1=new Image(new Texture(Gdx.files.internal( "assets/image/icon/1.png")));
        img1.setSize(80,80);
        img2=new Image(new Texture(Gdx.files.internal( "assets/image/icon/2.png")));
        img2.setSize(80,80);
        img3=new Image(new Texture(Gdx.files.internal( "assets/image/icon/3.png")));
        img3.setSize(80,80);
        img4=new Image(new Texture(Gdx.files.internal( "assets/image/icon/4.png")));
        img4.setSize(80,80);
        img5=new Image(new Texture(Gdx.files.internal( "assets/image/icon/5.png")));
        img5.setSize(80,80);
        img6=new Image(new Texture(Gdx.files.internal( "assets/image/icon/6.png")));
        img6.setSize(80,80);
        pass1=new Image(new Texture(Gdx.files.internal("assets/image/other/pass1.png")));
        pass2=new Image(new Texture(Gdx.files.internal("assets/image/other/pass2.png")));
        pass3=new Image(new Texture(Gdx.files.internal("assets/image/other/pass3.png")));
        pass4=new Image(new Texture(Gdx.files.internal("assets/image/other/pass4.png")));
        pass1.setVisible(true);
        pass2.setVisible(false);
        pass3.setVisible(false);
        pass4.setVisible(false);

        X.setPosition(850,700);
        username.setPosition(200,600);
        usernamefield.setPosition(400,600);
        pic.setPosition(200,500);
        img1.setPosition(400,500);
        img2.setPosition(500,500);
        img3.setPosition(600,500);
        img4.setPosition(400,400);
        img5.setPosition(500,400);
        img6.setPosition(600,400);
        password.setPosition(200,350);
        passwordfield.setPosition(400,350);
        passpow.setPosition(200,250);
        pass1.setPosition(400,250);
        pass2.setPosition(400,250);
        pass3.setPosition(400,250);
        pass4.setPosition(400,250);
        verifypass.setPosition(200,150);
        verifypassfield.setPosition(400,150);
        yes.setPosition(250,80);
        no.setPosition(550,80);
        usernamefield.setText(playScreen.getUser().getUserName());
        passwordfield.setPasswordMode(true);
        passwordfield.setPasswordCharacter('*');
        verifypassfield.setPasswordMode(true);
        verifypassfield.setPasswordCharacter('*');
        passwordfield.setText(playScreen.getUser().getPass());
        verifypassfield.setText(playScreen.getUser().getPass());
        
        img1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                img2.setVisible(false);
                img3.setVisible(false);
                img4.setVisible(false);
                img5.setVisible(false);
                img6.setVisible(false);
                img1.setSize(100, 100);
                img1.setPosition(500, 450);
                imageFlag=1;
              
                
            }
        });
        img2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                img1.setVisible(false);
                img3.setVisible(false);
                img4.setVisible(false);
                img5.setVisible(false);
                img6.setVisible(false);
                img2.setSize(100, 100);
                img2.setPosition(500, 450);
                imageFlag=2;
               
            }
        });
        img3.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                img1.setVisible(false);
                img2.setVisible(false);
                img4.setVisible(false);
                img5.setVisible(false);
                img6.setVisible(false);
                img3.setSize(100, 100);
                img3.setPosition(500, 450);
                imageFlag=3;
            }
        });
        img4.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                img1.setVisible(false);
                img2.setVisible(false);
                img3.setVisible(false);
                img5.setVisible(false);
                img6.setVisible(false);
                img4.setSize(100, 100);
                img4.setPosition(500, 450);
                imageFlag=4;
            }
        });
        img5.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                img1.setVisible(false);
                img2.setVisible(false);
                img3.setVisible(false);
                img4.setVisible(false);
                img6.setVisible(false);
                img5.setSize(100, 100);
                img5.setPosition(500, 450);
                imageFlag=5;
            }
        });
        img6.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                img1.setVisible(false);
                img2.setVisible(false);
                img3.setVisible(false);
                img4.setVisible(false);
                img5.setVisible(false);
                img6.setSize(100, 100);
                img6.setPosition(500, 450);
                imageFlag=6;
            }
        });
        
        this.addActor(X);
        this.addActor(username);
        this.addActor(usernamefield);
        this.addActor(pic);
        this.addActor(img1);
        this.addActor(img2);
        this.addActor(img3);
        this.addActor(img4);
        this.addActor(img5);
        this.addActor(img6);
        this.addActor(password);
        this.addActor(passwordfield);
        this.addActor(passpow);
        this.addActor(pass1);
        this.addActor(pass2);
        this.addActor(pass3);
        this.addActor(pass4);
        this.addActor(verifypass);
        this.addActor(verifypassfield);
        this.addActor(yes);
        this.addActor(no);
    }
    public void initial() {
    	
    	img1.setPosition(400,500);
        img2.setPosition(500,500);
        img3.setPosition(600,500);
        img4.setPosition(400,400);
        img5.setPosition(500,400);
        img6.setPosition(600,400);
        img1.setVisible(true);
        img2.setVisible(true);
        img3.setVisible(true);
        img4.setVisible(true);
        img5.setVisible(true);
        img6.setVisible(true);
        img1.setSize(80,80);
        img2.setSize(80,80);
        img3.setSize(80,80);
        img4.setSize(80,80);
        img5.setSize(80,80);
        img6.setSize(80,80);
        passwordfield.setText(playScreen.getUser().getPass());
        verifypassfield.setText(playScreen.getUser().getPass());
    }
    public TextButton Getyes(){
        return yes;
    }
    public TextButton Getno(){
        return no;
    }
    public ImageButton GetX(){
        return X;
    }
    public TextField Getpasswordfield(){
        return passwordfield;
    }
    public TextField Getverifypassfield(){
        return verifypassfield;
    }
    public TextField Getusernamefield(){
        return usernamefield;
    }
    public Image getPass1(){
        return pass1;
    }
    public Image getPass2(){
        return pass2;
    }
    public Image getPass3(){
        return pass3;
    }
    public Image getPass4(){
        return pass4;
    }
    public int getImageFlag() {
    	return imageFlag;
    }
}
