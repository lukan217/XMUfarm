package com.javakaihua.farmgame.view.loading;

import com.badlogic.gdx.scenes.scene2d.ui.*;


public class LeadingWindow extends Window {
    private ImageButton next;
    private final String text;
    private Label label;

    public LeadingWindow(String title, Skin skin, String styleName, String text) {
        super(title, skin, styleName);
        this.text=text;
        label=new Label(text,skin);
        label.setPosition(0,0);
        next=new ImageButton(skin, "up");
        next.setPosition(250,20);

        this.setVisible(false);
        this.add(label);
        this.row();
        this.addActor(next);
        this.setMovable(true);
        this.setSize(300,270);
        this.setPosition(470,250);

    }
    public  ImageButton getnextButton(){
        return next;
    }
}
