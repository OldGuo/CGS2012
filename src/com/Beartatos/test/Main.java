package com.Beartatos.test;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {
        public static final int GAME_STATE = 1;

        public Main() {
                super("Platformer");
        }

        @Override
		public void initStatesList(GameContainer container) throws SlickException {
                addState(new Platformer());
        }

        public static void main(String[] args) throws SlickException {
                AppGameContainer app = new AppGameContainer(new Main());
                app.setDisplayMode(480, 320, false);
                app.start();
        }
}