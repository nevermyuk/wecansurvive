package com.ict1009.wecansurvive.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ict1009.wecansurvive.WeCanSurvive;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "We can Survive!";
		config.width = 1360;
		config.height = 624;
		new LwjglApplication(new WeCanSurvive(), config);
	}
}
