package com.javakaihua.farmgame.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


import com.javakaihua.farmgame.model.User;
import com.javakaihua.farmgame.utils.DataManage;
import com.javakaihua.farmgame.utils.GameMusic;
import com.javakaihua.farmgame.utils.SQLConnector;
import com.javakaihua.farmgame.view.LoadingScreen;
import com.javakaihua.farmgame.view.LoginScreen;
import com.javakaihua.farmgame.view.MenuScreen;
import com.javakaihua.farmgame.view.PlayScreen;

import java.sql.SQLException;

public class FarmGame extends Game {
    public AssetManager manager;

    public SpriteBatch batch;
    public PlayScreen playScreen;
    public LoginScreen loginScreen;
    public MenuScreen menuScreen;
    public LoadingScreen loadingScreen;
    public GameMusic gamemusic;

    @Override
    public void create () {
        manager=new AssetManager();
        gamemusic=new GameMusic();
        manager.load("assets/skin/skin.atlas", TextureAtlas.class);
        manager.load("assets/skin/skin.json", Skin.class, new SkinLoader.SkinParameter("assets/skin/skin.atlas"));
        manager.finishLoading();
        batch = new SpriteBatch();
        setScreen(loginScreen=new LoginScreen(this));
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose () {

        if(playScreen!=null)
            playScreen.dispose();
        if(loginScreen!=null)
            loginScreen.dispose();
        if(menuScreen!=null)
            menuScreen.dispose();
        if(loginScreen!=null)
            loginScreen.dispose();
        if(batch!=null)
            batch.dispose();
        if(manager!=null)
            manager.dispose();
        if(SQLConnector.getConn()!=null) {
            try {
                SQLConnector.getStmt().close();
                SQLConnector.getConn().close();
                System.out.println("数据库连接已关闭！");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}