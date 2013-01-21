package org.mvfbla.cgs2012;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/** @author davedes */
public class TypeWriterTest{

	private int renderRow, renderCol,TYPE_DELAY,time;
	private final int width;
	private boolean finished;
	private Font font;
	private List<String>lines;
	private final String text;
	private final Color box;

    public TypeWriterTest() {
        width = 720;
        List<String> lines;
        text = "Language is one of the keys to being human. It allows use to communicate with other human beings and to leave a legacy of our thoughts and actions for future generations. The dominant temporal lobe helps to process sounds and written words into meaninful information.";

        //create a list of lines based on the above text
        box = new Color(200,200,200,0.45f);

        renderRow = 0;
        renderCol = 0;

        TYPE_DELAY = 25;
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
	public void render(GameContainer container, Graphics g,int xPos,int yPos) throws SlickException {
        font = container.getDefaultFont();
        lines = wrap(text, font, width);
        if(!finished){
			int x = 40 + xPos;
	        int y = 55 + yPos;
	        int pad = 25;
	        g.setColor(box);
	        g.fillRect(x-pad, y-pad, width+pad*2, 200+pad*2);

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

    //update the game logic and typewriting effect
	public void update(int delta) {
        time -= delta;
        if (time<=0 && !finished) { // a character is printed every TYPE_DELAY milliseconds
            time = TYPE_DELAY;
            String line = lines.get(renderRow);

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

    //shows ALL text
    public void showAll() {
        if (lines.isEmpty())
            renderRow = renderCol = 0;
        else {
            renderRow = lines.size()-1;
            renderCol = lines.get(renderRow).length();
        }
        finished = true;
    }

    //restarts typewriting effect
    public void restart() {
        renderCol = 0;
        renderRow = 0;
        time = TYPE_DELAY;
        finished = false;
    }

    //Wraps the given string into a list of split lines based on the width
    private List<String> wrap(String text, Font font, int width) {
        //A less accurate but more efficient wrap would be to specify the max
        //number of columns (e.g. using the width of the 'M' character or something).
        //The below method will look nicer in the end, though.

        List<String> list = new ArrayList<String>();
        String str = text;
        String line = "";

        //we will go through adding characters, once we hit the max width
        //we will either split the line at the last space OR split the line
        //at the given char if no last space exists

        //while we still have text to check
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
}