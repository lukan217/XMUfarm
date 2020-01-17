package com.javakaihua.farmgame.view.loading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class IntroUI extends Window {

    private Table table;
    private TextButton quitButton,confirmButton; //定义文本按钮，用于关闭窗口
    private Label label;           //定义标签，用于添加文字


    public IntroUI(String title, Skin skin){
        super(title,skin,"window1");

        table=new Table();
        table.setSize(500,500);
        table.setPosition( Gdx.graphics.getWidth()/2-225, Gdx.graphics.getHeight()/2-225);

        quitButton = new TextButton("关闭", skin);
        quitButton.setTransform(true);
        quitButton.setScale(0.8f);
        quitButton.setPosition(80,5);

        table.setSize(500,500);
        table.setPosition( Gdx.graphics.getWidth()/2-225, Gdx.graphics.getHeight()/2-225);

        label=new Label("玩家可以进行昵称、头像、音乐的\n设置。\n通过键盘的上下左右键进行移动\n人物，人物通过睡觉或补充食物\n来恢复体力。" +
                "\n人物在地图上移动，可以与NPC\n进行对话。\n作物需要浇水，否则会枯萎。\n",skin);

        quitButton = new TextButton("关闭", skin);
        quitButton.setTransform(true);
        quitButton.setScale(0.8f);
        quitButton.setPosition(80,5);

        table.add(label).padLeft(10).padTop(20);
        table.row();
        table.add(quitButton);
        this.add(table).padLeft(10).padTop(20);
        this.setPosition(Gdx.graphics.getWidth()/2-225, Gdx.graphics.getHeight()/2-225);
        this.setSize(500, 500);
        this.setVisible(false);
        this.setMovable(true);
    }
    public TextButton getQuitButton(){
        return quitButton;
    }
}
