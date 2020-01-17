package com.javakaihua.farmgame.view.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.javakaihua.farmgame.view.PlayScreen;


public class Userwindow extends Window {
    //用户信息窗口

    private Label username;//昵称
    private Image icon;//头像
    private Label userID;//账号
    private Label level;//等级
    private Label power;//体力值
    private Label exp;//经验值
    private Label money;//金币
    public TextButton change;//修改信息
    public TextButton exit;//注销账号
    private ImageButton win1;//叉叉键
    private Table tableicon;
    private Table tablemoney;
    private ImageButton X;
    private PlayScreen playScreen;

    public Userwindow(String title, Skin skin, PlayScreen playScreen) {
        super(title, skin,"window1");
        this.playScreen=playScreen;
        this.setModal(true);
        this.setVisible(true);
        this.setSize(900,700);
        this.setPosition(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/5);

        userID=new Label("账号:"+playScreen.getUser().getUserID(),skin);
        username=new Label("昵称:"+playScreen.getUser().getUserName(),skin);
        level=new Label("等级:"+playScreen.getUser().getLevel(),skin);
        power=new Label("体力值:"+playScreen.getUser().getHp(),skin);
        exp=new Label("经验值:"+playScreen.getUser().getExp(),skin);
        money=new Label("金币:"+playScreen.getUser().getMoney(),skin);
        change=new TextButton("修改信息",skin);
        exit=new TextButton("切换账号",skin);
        win1=new ImageButton(skin,"win1");
        X=new ImageButton(skin,"win1");
        this.addActor(X);
        X.setPosition(800,600);
        icon=new Image(new Texture(Gdx.files.internal(playScreen.getUser().getPic())));
        icon.setSize(70,70);
        icon.setPosition(0,0);
        tableicon=new Table();
        tablemoney=new Table();
        
        
        this.add(tableicon);
        this.add(tablemoney);
        
        tableicon.setVisible(true);
        tableicon.setPosition(600,200);
        tableicon.setSize(400,500);
        tableicon.add(icon).padRight(150).padBottom(40);
        tableicon.row();
        tableicon.add(userID).padRight(150).padBottom(50);
        tableicon.row();
        tableicon.add(username).padRight(150).padBottom(50);
        tableicon.row();
        tableicon.add(change).padRight(150);
   
        tablemoney.setVisible(true);
        tablemoney.setPosition(100,200);
        tablemoney.setSize(400,500);
        tablemoney.add(level).padBottom(50).padTop(10);
        tablemoney.row();
        tablemoney.add(money).padBottom(50);
        tablemoney.row();
        tablemoney.add(exp).padBottom(50);
        tablemoney.row();
        tablemoney.add(power).padBottom(50);
        tablemoney.row();
        tablemoney.add(exit);
    }
    public TextButton Getexit(){
        return exit;
    }
    public TextButton Getchange(){
        return change;
    }
    public ImageButton GetX(){
        return X;
    }
    public void update() {
   	 icon=new Image(new Texture(Gdx.files.internal(playScreen.getUser().getPic())));
   	 icon.setSize(70, 70);
   	 icon.setPosition(600, 200);
   	 tableicon.clearChildren();
   	 tableicon.add(icon).padRight(150).padBottom(40);
        tableicon.row();
        tableicon.add(userID).padRight(150).padBottom(50);
        tableicon.row();
        tableicon.add(username).padRight(150).padBottom(50);
        tableicon.row();
        tableicon.add(change).padRight(150);
   	
   	 
   }
   @Override
   public void act(float delta) {
   	super.act(delta);
   	power.setText("体力值:"+playScreen.getUser().getHp());
   	money.setText("金币:"+playScreen.getUser().getMoney());
   	level.setText("等级:"+playScreen.getUser().getLevel());
   	exp.setText("经验:"+playScreen.getUser().getExp());
   	username.setText("昵称:"+playScreen.getUser().getUserName());
   		
	}


}
