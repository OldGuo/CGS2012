package org.mvfbla.cgs2012.utils;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * @author Young
 * TyperWriter for plot text
 */
public class TypeWriter{

	//Wraps the given string into a list of split lines based on the width
    public static List<String> wrap(String text, Font font, int width) {
        List<String> list = new ArrayList<String>();
        String str = text;
        String line = "";

        //go through adding characters, once we hit the max width
        //either split the line at the last space OR split the line
        //at the given char if no last space exists
        //while still have text to check
        int i = 0;
        int lastSpace = -1;
        while (i<str.length()) {
            char c = str.charAt(i);
            if (Character.isWhitespace(c))
                lastSpace = i;

            //time to wrap
            if (c=='\n' || font.getWidth(line + c) > width) {
                //if we've hit a space recently, use that
                int split = lastSpace!=-1 ? lastSpace : i;
                int splitTrimmed = split;

                //if we are splitting by space, trim it off for the start of the
                //next line
                if (lastSpace!=-1 && split<str.length()-1) {
                   splitTrimmed++;
                }

                line = str.substring(0, split);
                str = str.substring(splitTrimmed);

                //add the line and reset our values
                list.add(line);
                line = "";
                i = 0;
                lastSpace = -1;
            }
            //not time to wrap, just keep moving along
            else {
                line += c;
                i++;
            }
        }
        if (str.length()!=0)
            list.add(str);
        return list;
    }
	private int renderRow;
	private int renderCol;
	private int TYPE_DELAY;
	private int time;
	private int width;
	private int height;
	private boolean finished;
	private Font font;
	private List<String>lines;
	private String text;
	private final Color box;

    private int x,y;

	public TypeWriter() {
        width = 720;
        height = 80;
        text = " ";
        box = new Color(255,255,255,0.4f);

        renderRow = 0;
        renderCol = 0;

        TYPE_DELAY = 20;
        time = TYPE_DELAY;
        finished = false;
    }
    public void draw(Graphics g,int xPos,int yPos,int w,int h) throws SlickException {
        if(!finished){
			x = 40 + xPos;
	        y = 45 + yPos;
	        width = w;
	        height = h;
	        int pad = 10;
	        g.setColor(Color.black);
	        g.fillRect(x-pad-10, y-pad-10, width+pad*2+20, height+pad*2+20);
	        g.setColor(box);
	        g.fillRect(x-pad, y-pad, width+pad*2, height+pad*2);

	        g.setColor(Color.white);
	        int lineHeight = font.getLineHeight();

	        //only render the rows we have typed out so far (renderRow = current row)
	        for (int i=0; i<renderRow+1; i++) {
	            String line = lines.get(i);
	            //render whole line if it's a previous one, otherwise render the col
	            int len = i<renderRow ? line.length() : renderCol;
	            String t = line.substring(0, len);
	            if (t.length()!=0) {
	                g.drawString(t, x, y);
	            }
	            y += lineHeight;
	        }
        }
    }
    public boolean isFinished(){
    	return finished;
    }
	//restarts typewriting effect
    public void restart() {
        renderCol = 0;
        renderRow = 0;
        time = TYPE_DELAY;
        finished = false;
    }

    /**
     * Entry point to our test
     *
     * @param argv The arguments passed to the test
     */
    public void setDelay(int d){
    	TYPE_DELAY = d;
    }
    public void setText(String s){
    	text = s;
    }

    //update the game logic and typewriting effect
	public void update(GameContainer container,int delta) {
        time -= delta;
        font = container.getDefaultFont();
        lines = wrap(text, font, width);
        if (time<=0 && !finished) { // a character is printed every TYPE_DELAY milliseconds
            time = TYPE_DELAY;

            //if we are moving down to the next line
            if (renderCol > lines.get(renderRow).length()-1) {
                //we've rendered all characters
                if (renderRow >= lines.size()-1) {
                    finished = true;
                }
                //move to next line
                else {
                    renderRow++;
                    renderCol = 0;
                }
            } else {
                //move to next character
                renderCol++;
            }
        }
    }
}