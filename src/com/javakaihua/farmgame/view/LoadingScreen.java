package com.javakaihua.farmgame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.javakaihua.farmgame.model.User;
import com.javakaihua.farmgame.utils.Check;
import com.javakaihua.farmgame.utils.DataManage;
import com.javakaihua.farmgame.main.FarmGame;
import com.javakaihua.farmgame.view.loading.*;


public class LoadingScreen implements Screen {
    //游戏预界面

    public FarmGame game;

    private User user;
    private Stage stage;
    private TextButton returnButton;
    private Skin skin;
    private Texture texture,texture1;
    private LeadingWindow window1;
    private SettingWindow settingWindow;
    private Image img1;

    public  LoadingScreen(FarmGame game,User user) {
        this.game = game;
        this.user=user;
        skin=new Skin(Gdx.files.internal("assets/skin/skin.json"), new TextureAtlas(Gdx.files.internal("assets/skin/skin.atlas")));
        stage = new Stage();

        texture = new Texture(Gdx.files.internal( "assets/image/other/leading.png" ));
        texture1=new Texture(Gdx.files.internal("assets/image/background/setbackground.png"));

        img1 = new Image();
        img1.setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
        img1.setSize(180,180);
        img1.setPosition(300,100);

        window1 = new LeadingWindow("", skin, "dialog", "欢迎来到厦大农场,请\n完善您的信息\n");
        settingWindow = new SettingWindow("完善信息", skin);
        settingWindow.setPosition(Gdx.graphics.getWidth()/2-225, Gdx.graphics.getHeight()/2-225);
        returnButton = new TextButton("主菜单", skin);
        returnButton.setPosition(80,100);
        window1.setVisible(true);

        stage.addActor(img1);
        stage.addActor(window1);
        stage.addActor(settingWindow);
        stage.addActor(returnButton);

        Gdx.input.setInputProcessor(stage);

        window1.getnextButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                window1.setVisible(false);
                settingWindow.setVisible(true);
                img1.setVisible(true);
            }
        });

        settingWindow.getKeepButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Check.checkusername(settingWindow.getTextField().getText()) || settingWindow.getImageFlag()==0 ){
                    new Dialog("", skin, "dialog") {
                        protected void result(Object object) {
                            if (object.equals(true)) {
                            }
                        }
                    }.text("请输入昵称,并且选择头像").button("确认", true).show(stage);
                }
                else {
                    user.setUserName(settingWindow.getTextField().getText());
                    user.setPic("assets/image/icon/"+settingWindow.getImageFlag()+".png");
                    DataManage.initialUser(user.getUserID());
                    settingWindow.getTextField().setText("");
                    settingWindow.setVisible(false);
                    game.setScreen(game.playScreen = new PlayScreen(game, user));
                }
            }
        });
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.menuScreen=new MenuScreen(game,user);
                game.setScreen(game.menuScreen);

            }
        });

    }

    @Override
    public void show() {


    }
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(texture1,0,0,1600,900);
        game.batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
    @Override
    public void hide() {
    }
    @Override
    public void dispose() {
    }
}
