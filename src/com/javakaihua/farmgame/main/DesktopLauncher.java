package com.javakaihua.farmgame.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.height = 900;
        config.width = 1600;
		config.vSyncEnabled = true;
        config.resizable = false;
        config.title="厦大农场";
        config.addIcon("assets/image/logo1.png", Files.FileType.Internal);
		new LwjglApplication(new FarmGame(), config);
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
	}
}
