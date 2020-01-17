package com.javakaihua.farmgame.view.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.javakaihua.farmgame.utils.GameMusic;



public class SettingUI extends Window {

    public CheckBox checkBox;// 复选框
    public CheckBox soundCheckBox;

    private Label switchLabel;
    private Label SoundLabel;
    private Label soundLabel;

    public Slider slider;
    private TextButton keepButton,cancelButton;
    private ImageButton no;

    public  SettingUI(String title, Skin skin,GameMusic gameMusic) {
        super(title, skin,"window1");
        SettingUI thisSettingUI=this;
        checkBox=new CheckBox("",skin);
        checkBox.setChecked(true);

        soundCheckBox=new CheckBox("",skin);
        soundCheckBox.setChecked(true);

        //定义调节音量大小的滑条
        slider=new Slider(0,1,0.05f,false,skin);

        keepButton = new TextButton("确认",skin);
        keepButton.setTransform(true);keepButton.setScale(1f);
        cancelButton = new TextButton("取消", skin);
        cancelButton.setTransform(true);cancelButton.setScale(1f);
        no=new ImageButton(skin,"win1");
        no.setScale(0.5f,0.5f);
        no.setPosition(380,380);
        switchLabel=new Label("音乐开关:", skin);
        SoundLabel=new Label("音效开关:",skin);
        soundLabel=new Label("音量调节:", skin);
        
        
    	//gameMusic=new GameMusic();
    	slider.setValue(0.5f);
    	//gameMusic.setMusicVolume(settingUI.getSlider().getValue());
        keepButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	thisSettingUI.setVisible(false);;
          
            }
        });


        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //撤销设置
            	thisSettingUI.setVisible(false);
       


            }
        });

      
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(thisSettingUI.getCheckBox().isChecked()==true)
                {
                    gameMusic.setMusicPlaying();
                }
                else 
                	gameMusic.setMusicStop();
            }
        });
        soundCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(thisSettingUI.getSoundCheckBox().isChecked()==true)
                {
                    gameMusic.setIsSoundPlaying(true);
                }
                else 
                	gameMusic.setIsSoundPlaying(false);
            }
        });
        
        slider.addListener(
                new ChangeListener()
                {
                    public void changed(ChangeEvent event, Actor actor)
                    {
                        gameMusic.setMusicVolume(thisSettingUI.getSlider().getValue());
                    }
                });
        no.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                thisSettingUI.setVisible(false);
            }
        });

        this.addActor(no);
        this.add(switchLabel).padLeft(1);
        this.add(checkBox).padLeft(10);
        this.row();
        this.add(SoundLabel).padLeft(1);
        this.add(soundCheckBox).padLeft(10);
        this.row();
        this.add(soundLabel).padLeft(1);
        this.add(slider).padLeft(10).width(150);
        this.row();
        this.add(keepButton).padTop(50);
        this.add(cancelButton).padTop(50);
        this.row();

        this.setVisible(false);
        this.setPosition(Gdx.graphics.getWidth()/2-225, Gdx.graphics.getHeight()/2-225);
        this.setSize(450, 450);
        this.setKeepWithinStage(false);
        this.setMovable(true);

    }
    public TextButton getKeepButton(){
        return keepButton;
    }
    public TextButton getCancelButton(){
        return cancelButton;
    }
    public CheckBox getCheckBox(){
        return checkBox;
    }
    public Slider getSlider(){
        return slider;
    }
    public CheckBox getSoundCheckBox() {
    	return soundCheckBox;
    }
}

