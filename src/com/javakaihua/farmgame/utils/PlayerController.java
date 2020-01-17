package com.javakaihua.farmgame.utils;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.javakaihua.farmgame.model.User;
import com.javakaihua.farmgame.view.PlayScreen;

import java.awt.*;

public class PlayerController implements InputProcessor {
	//用来控制人物移动，点击睡觉，点击npc睡觉

	private Entity player;
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	private PlayScreen screen;
	private Vector3 tp;
	NPCDialog npcDialog = new NPCDialog();

	public PlayerController(PlayScreen screen, Entity player) {
		this.player = player;
		this.screen = screen;
		tp = new Vector3();
		left = false;
		right = false;
		up = false;
		down = false;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A)
			this.left = true;
		if (keycode == Input.Keys.UP || keycode == Input.Keys.W)
			this.up = true;
		if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S)
			this.down = true;
		if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D)
			this.right = true;
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A)
			this.left = false;
		if (keycode == Input.Keys.UP || keycode == Input.Keys.W)
			this.up = false;
		if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S)
			this.down = false;
		if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D)
			this.right = false;
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		Vector3 coords = screen.getCam().unproject(tp.set(screenX, screenY, 0));
		if (Math.abs(player.getPlayerX() - coords.x) < 150 && Math.abs(player.getPlayerY() - coords.y) < 150) {
			MapLayer objectLayer = screen.getMap().getLayers().get("NPC");
			if(objectLayer!=null)
			{
				for (MapObject object : objectLayer.getObjects()) {
					Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
					if (rectangle.contains(coords.x, coords.y)) {
						switch (object.getName()) {
							case "visitor": {
								if (screen.isFirstTalk == false) {
									npcDialog.setDialog(screen);
								}
								if (screen.isFirstTalk == true) {
									npcDialog.setNPCDialog("visitor", 0, screen);
									npcDialog.getDialogOfVisitor().show(screen.getStage());
									screen.isFirstTalk=false;
								}
								break;

							}
							case "teacher": {
								if (screen.isFirstTalk == false) {
									npcDialog.setDialog(screen);
								}
								if (screen.isFirstTalk == true) {
									npcDialog.setNPCDialog("teacher", 0, screen);
									npcDialog.getDialogOfTeacher().show(screen.getStage());
									screen.isFirstTalk = false;
								}
								break;

							}
							case "security": {
								npcDialog.setNPCDialog("security", 0, screen);
								npcDialog.getDialogOfSecurityGuard().show(screen.getStage());
								break;
							}
							case "bed":{
								if(screen.getWorldTime().getElapsedInHours()<21&&screen.getWorldTime().getElapsedInHours()>2)
									new Dialog("",screen.getSkin(),"dialog").text("还没到睡觉时间哦！").button("确认", true).show(screen.getStage());
								else {
									new Dialog("",screen.getSkin(),"dialog") {
										protected void result(Object object) {
											if (object.equals(true)) {
												screen.getUser().setSleep(true);
												screen.goTOBed();
												screen.getWorldTime().setRealToTimerRatio(3600);
												screen.setShowSleep(true);
											}
											else {
												screen.getWorldTime().setPause(false);
											}

										}
									}.text("是否立即睡觉？").button("是", true).button("否",false).show(screen.getStage());
								}
								break;
							}
			}


					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	public void update(float delta) {
		processInput(delta);
	}

	private void processInput(float delta) {
		if(!screen.getUser().isFaint()&&!screen.getUser().isSleep())
		{
			if (up) {
				player.move(Entity.Direction.UP, delta);
				player.setState(Entity.State.WALKING);
				player.setDirection(Entity.Direction.UP, delta);
			} else if (down) {

				player.move(Entity.Direction.DOWN, delta);
				player.setState(Entity.State.WALKING);
				player.setDirection(Entity.Direction.DOWN, delta);
			} else if (right) {

				player.move(Entity.Direction.RIGHT, delta);
				player.setState(Entity.State.WALKING);
				player.setDirection(Entity.Direction.RIGHT, delta);
			} else if (left) {
				player.move(Entity.Direction.LEFT, delta);
				player.setState(Entity.State.WALKING);
				player.setDirection(Entity.Direction.LEFT, delta);
			} else {
				player.setState(Entity.State.IDLE);
				player.setDirection(player.getDirection(), delta);
			}
		}

	}
}