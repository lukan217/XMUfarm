package com.javakaihua.farmgame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.javakaihua.farmgame.utils.Check;
import com.javakaihua.farmgame.utils.DataManage;
import com.javakaihua.farmgame.model.User;
import com.javakaihua.farmgame.main.FarmGame;
import com.javakaihua.farmgame.view.login.*;


import java.sql.SQLException;

public class LoginScreen implements Screen {
	//登录界面

	private User user;
	private FarmGame game;
	private Skin skin;

	private Image background;//背景
	private Stage loginStage;

	private RegisterUI registerUI;
	private LoginUI loginUI;

	public LoginScreen(FarmGame game) {
		//注册界面

		this.game = game;
        game.gamemusic.setMusicStop();//注册界面不播放音乐
		skin=new Skin(Gdx.files.internal("assets/skin/skin.json"), new TextureAtlas(Gdx.files.internal("assets/skin/skin.atlas")));
		loginStage = new Stage();

		//初始化各个控件UI
		background = new Image(new Texture("assets/image/background/login.png"));
		background.setSize(1600, 900);
		registerUI = new RegisterUI(skin);
		loginUI = new LoginUI(skin);

		//将各个UI加到舞台
		loginStage.addActor(background);
		loginStage.addActor(loginUI);
		loginStage.addActor(registerUI);
		Gdx.input.setInputProcessor(loginStage);

		//为按钮添加监听器并实现逻辑
		loginUI.getRegisterButton().addListener(new ClickListener() {
			public void clicked(InputEvent event,
								float x,
								float y) {
				loginUI.setVisible(false);
				registerUI.setVisible(true);
			}
		});
		loginUI.getLoginButton().addListener(new ClickListener(){
            public void clicked(InputEvent event,
                                float x,
                                float y) {
				int status= 0;
				try {
					status = Check.checkLogin(loginUI.getUserIDTextField().getText(),loginUI.getPasswordTextField().getText());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(status==0){
					new Dialog("",skin,"dialog") {
						protected void result(Object object) {
							if (object.equals(true)) {
								loginUI.getPasswordTextField().setText("");
							}
						}
					}.text("用户账号不存在，请先注册！").button("确认", true).show(loginStage);
				}

                else if(status==1)
				{
					new Dialog("",skin,"dialog") {
					protected void result(Object object) {
						if (object.equals(true)) {
							loginUI.getPasswordTextField().setText("");
						}
					}
				}.text("密码错误，请重新输入！").button("确认", true).show(loginStage);
				}

                else {
					try {
						user= DataManage.readUserDataFromSQL(loginUI.getUserIDTextField().getText());
					} catch (SQLException e) {
						e.printStackTrace();
					}
					new Dialog("",skin,"dialog") {
						protected void result(Object object) {
							if (object.equals(true)) {
								if(loginUI.getRemember().isChecked())
									DataManage.saveUserMsg(user);
								else
									Gdx.files.local("user/user.json").delete();
								game.setScreen(game.menuScreen=new MenuScreen(game, user));

							}
						}
					}.text("登录成功！").button("确认", true).show(loginStage);
				}
            }
        }
        );

		registerUI.getCancelButton().addListener(new ClickListener() {
			public void clicked(InputEvent event,
								float x,
								float y) {
				loginUI.setVisible(true);
				registerUI.setVisible(false);
				registerUI.getUserIDTextField().setText("");
				registerUI.getVerifyPassTextField().setText("");
			}
		});
		registerUI.getRegisterButton().addListener(new ClickListener(){
            public void clicked(InputEvent event,
                                float x,
                                float y) {
                if (!Check.checkID(registerUI.getUserIDTextField().getText()))
				{
					new Dialog("",skin,"dialog") {
						protected void result(Object object) {
							if (object.equals(true)) {
							}
						}
					}.text("用户长度应为5-12位，只能包含字母和数字").button("确认", true).show(loginStage);
				}
				else {
					try {
						if (Check.checkIDExistFromSQL(registerUI.getUserIDTextField().getText()))
						{//用户账号已被注册
							new Dialog("",skin,"dialog") {
								protected void result(Object object) {
									if (object.equals(true)) {
									}
								}
							}.text("用户账号已被注册，请重新输入！").button("确认", true).show(loginStage);
						}

						else if (!Check.checkVerifyPass(registerUI.getPasswordTextField().getText(),
								registerUI.getVerifyPassTextField().getText())
						) {//两次输入密码不一致
							new Dialog("",skin,"dialog") {
								protected void result(Object object) {
									if (object.equals(true)) {
										registerUI.getPasswordTextField().setText("");
										registerUI.getVerifyPassTextField().setText("");
									}
								}
							}.text("前后密码不一致，请重新输入！").button("确认", true).show(loginStage);
						}
						else if(!Check.checkPassworst(registerUI.getPasswordTextField().getText()))
						{
							new Dialog("",skin,"dialog") {
								protected void result(Object object) {
									if (object.equals(true)) {
										registerUI.getPasswordTextField().setText("");
										registerUI.getVerifyPassTextField().setText("");
									}
								}
							}.text("密码强度过低，请重新输入！").button("确认", true).show(loginStage);
						}
						else{//注册信息均正确，开始注册
							user=new User(registerUI.getUserIDTextField().getText(),
									registerUI.getPasswordTextField().getText());
							DataManage.saveUserDataToSQL(user);//保存用户信息
							new Dialog("",skin,"dialog") {
								protected void result(Object object) {
									if (object.equals(true)) {
										registerUI.getUserIDTextField().setText("");
										registerUI.getPasswordTextField().setText("");
										registerUI.getVerifyPassTextField().setText("");
										registerUI.setVisible(false);
										loginUI.setVisible(true);
									}
								}
							}.text("恭喜你，注册成功了，快去登录吧！").button("确认", true).show(loginStage);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
            }
        });
		registerUI.getPasswordTextField().setTextFieldListener(new TextField.TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char c) {
				boolean isnull;
				boolean middle;
				boolean best;
				isnull=Check.checkPass(registerUI.getPasswordTextField().getText());
				middle=Check.checkPassmiddle(registerUI.getPasswordTextField().getText());
				best=Check.checkPassbest(registerUI.getPasswordTextField().getText());
				if(isnull==true){

					registerUI.getPass2().setVisible(false);
					registerUI.getPass3().setVisible(false);
					registerUI.getPass4().setVisible(false);
					registerUI.getPass1().setVisible(true);
				}

				else if(best==true){//强度为高
					registerUI.getPass3().setVisible(false);
					registerUI.getPass2().setVisible(false);
					registerUI.getPass1().setVisible(false);
					registerUI.getPass4().setVisible(true);
				}
				else if(middle==true){//强度为中
					registerUI.getPass4().setVisible(false);
					registerUI.getPass2().setVisible(false);
					registerUI.getPass1().setVisible(false);
					registerUI.getPass3().setVisible(true);
				}
				else{//强度为低
					registerUI.getPass3().setVisible(false);
					registerUI.getPass2().setVisible(false);
					registerUI.getPass1().setVisible(false);
					registerUI.getPass2().setVisible(true);
				}
			}
		});


	}


	@Override
	public void show() {

	}

	public void render(float delta) {
		// 黑色清屏

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.end();
		// 更新舞台逻辑
		loginStage.act();
		// 绘制舞台
		loginStage.draw();
	}

	@Override
	public void resize(int i, int i1) {

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

	public void dispose() {
		// 场景被销毁时释放资源



	}

	public Skin getSkin() {
		return skin;
	}
}

