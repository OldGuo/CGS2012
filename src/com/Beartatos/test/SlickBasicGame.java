package com.Beartatos.test;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class SlickBasicGame extends BasicGame{

    Image plane = null;
    Image land = null;
    float x = 400;
    float y = 300;
    float scale = 1;

    public SlickBasicGame()
    {
        super("Slick2D Path2Glory - SlickBasicGame");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        plane = new Image("plane.png");
        land = new Image("land.jpg");
    }

    @Override
    public void update(GameContainer gc, int delta)
			throws SlickException
    {
        Input input = gc.getInput();

        if(input.isKeyDown(Input.KEY_A))
        {
            plane.rotate(-0.2f * delta);
        }

        if(input.isKeyDown(Input.KEY_D))
        {
            plane.rotate(0.2f * delta);
        }

        if(input.isKeyDown(Input.KEY_W))
        {
            float hip = 0.4f * delta;

            float rotation = plane.getRotation();

            x+= hip * Math.sin(Math.toRadians(rotation));
            y-= hip * Math.cos(Math.toRadians(rotation));
        }

        if(input.isKeyDown(Input.KEY_2))
        {
            scale += (scale >= 10.0f) ? 0 : 0.1f;
            plane.setCenterOfRotation(plane.getWidth()/2.0f*scale, plane.getHeight()/2.0f*scale);
        }
        if(input.isKeyDown(Input.KEY_1))
        {
            scale -= (scale <= 0.0f) ? 0 : 0.1f;
            plane.setCenterOfRotation(plane.getWidth()/2.0f*scale, plane.getHeight()/2.0f*scale);
        }
    }

    @Override
	public void render(GameContainer gc, Graphics g)
			throws SlickException
    {
        land.draw(0, 0);

        plane.draw(x, y, scale);

    }

    public static void main(String[] args)
			throws SlickException
    {
         AppGameContainer app =
			new AppGameContainer( new SlickBasicGame() );

         app.setDisplayMode(800, 600, false);
         app.start();
    }
}