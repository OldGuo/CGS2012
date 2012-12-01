package com.Beartatos.test;

import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Platformer extends BasicGameState {
        boolean jumping;
        float velocityY;
        float gravity = 0.4f;
        int accTime;
        float px = 32;
        float py = 256;
        Image p, w;
        Input input;
        Level level;

        @Override
		public int getID() {
                return Main.GAME_STATE;
        }

        @Override
		public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
                p = new Image("data\\pr.png");
                w = new Image("data\\w.png");
                level = new Level();
                File map1 = new File("data\\map1.txt");
                //or your file name...

                try
                {
                	level.load(map1);
                }
                catch (IOException e)
                {
                	e.printStackTrace();
                }

                input = gc.getInput();
        }

        @Override
		public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
                p.draw(px, py);
                level.draw(g);
        }

        @Override
		public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
                accTime += delta;

                int startX = (int)(px / 32);
                int endX = (int)(px + 32) / 32;
                int startY = (int)(py / 32);
                int endY = (int)(py + 32) / 32;

                for (int x = startX; x <= endX; x++) {
                        for (int y = startY; y <= endY; y++) {

                        }
                }

                while (accTime > 0) {
                        accTime -= 20;
                        velocityY -= gravity;

                        if (input.isKeyDown(Input.KEY_RIGHT)) {
                                px += 4;
                        }

                        if (input.isKeyDown(Input.KEY_LEFT)) {
                                px -= 4;
                        }

                        if (input.isKeyDown(Input.KEY_SPACE) && !jumping) {
                                velocityY = 8.0f;
                                jumping = true;
                        }

                        if (jumping) {
                                py -= velocityY;
                        }

                        checkCollision();
                }
        }

        public void checkCollision() {
                if (py > 256)  {
                        velocityY = 0;
                        py = 256;
                        jumping = false;
                }
        }

        public void checkOverlap(int x1, int y1, int width1, int height1, int x2, int y2, int width2, int height2) {

        }
}