package com.Beartatos.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Level {
        Image w;
        int[][] level = new int[15][10];

        public Level() throws SlickException {
                w = new Image("w.png");
        }

        public int getTile(int x, int y) {
                return level[x][y];
        }

        public void draw(Graphics g) {
                for (int x = 0; x < 15; x++) {
                        for (int y = 0; y < 10; y++) {
                                if (getTile(x, y) != 0) {
                                        g.drawImage(w, x * 32, y * 32);
                                }
                        }
                }
        }

    public void load(File file) throws IOException {
           BufferedReader reader = new BufferedReader(new FileReader(file));
           for (int y = 0; y < 10; y++) {
               String line = reader.readLine();
               for (int x = 0; x < 15; x++) {
                      char c = line.charAt(x);
                      if (c == '#')  {
                          level[x][y] = 1;
                      }
               }
          }
          reader.close();
    }

}
