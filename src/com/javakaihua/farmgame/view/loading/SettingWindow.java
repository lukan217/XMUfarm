package com.javakaihua.farmgame.view.loading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class SettingWindow extends Window {

    private TextField textField;
    private Label username,pic;
    private TextButton keepButton;
    private Image img1,img2,img3,img4,img5,img6;
    private int imageFlag=0;


    public SettingWindow(String title, Skin skin){
        super(title, skin,"window1");

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

        username=new Label("你的昵称:",skin);
        pic=new Label("你的头像:",skin);
        keepButton=new TextButton("保存",skin);
        textField = new TextField("", skin);
        textField.setSize(300,50);
        textField.setMessageText("昵称");
        textField.setAlignment(Align.center);

        username.setPosition(100,300);
        textField.setPosition(250,300);
        pic.setPosition(100,200);
        img1.setPosition(250,200);
        img2.setPosition(350,200);
        img3.setPosition(450,200);
        img4.setPosition(250,100);
        img5.setPosition(350,100);
        img6.setPosition(450,100);
        keepButton.setPosition(300,0);

        img1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                img2.setVisible(false);
                img3.setVisible(false);
                img4.setVisible(false);
                img5.setVisible(false);
                img6.setVisible(false);
                img1.setSize(100, 100);
                img1.setPosition(350, 150);
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
                img2.setPosition(350, 150);
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
                img3.setPosition(350, 150);
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
                img4.setPosition(350, 150);
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
                img5.setPosition(350, 150);
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
                img6.setPosition(350, 150);
                imageFlag=6;
            }
        });

        this.setVisible(false);
        this.addActor(username);
        this.addActor(textField);
        this.addActor(pic);
        this.addActor(img1);
        this.addActor(img2);
        this.addActor(img3);
        this.addActor(img4);
        this.addActor(img5);
        this.addActor(img6);
        this.addActor(keepButton);

        this.setPosition(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/3);
        this.setSize(700,500);


    }
    public TextButton getKeepButton(){
        return keepButton;
    }

    public TextField getTextField(){
        return textField;
    }

    public int getImageFlag(){
        return imageFlag;
    }
}

