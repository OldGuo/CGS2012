package org.mvfbla.cgs2012.interactable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Young
 * Reads Questions in from .txt file and loads into questions
 */
public class QuestionReader {
	static ArrayList<String> questions=new ArrayList<String>(); //ArrayLists for questions
	static ArrayList<String> correctAns=new ArrayList<String>();
	static ArrayList<String> wrongAns1=new ArrayList<String>();
	static ArrayList<String> wrongAns2=new ArrayList<String>();
	static ArrayList<String> wrongAns3=new ArrayList<String>();
	private final File textFile;
	/**
	 * @param fileName - File name to retrieve questions from
	 */
	public QuestionReader(String fileName){
		textFile=new File(fileName);
		processFile();
	}
	/**
	 * @return returns the arraylist with correct answers
	 */
	public ArrayList<String>getCorrectAns(){
		return correctAns;
	}
	/**
	 * @return - returns the arraylist of questions
	 */
	public ArrayList<String>getQuestions(){
		return questions;
	}
	/**
	 * @return - first array list of wrong answers
	 */
	public ArrayList<String>getWrongAns1(){
		return wrongAns1;
	}
	/**
	 * @return - second array of wrong answers
	 */
	public ArrayList<String>getWrongAns2(){
		return wrongAns2;
	}
	/**
	 * @return - third array of wrong answers
	 */
	public ArrayList<String>getWrongAns3(){
		return wrongAns3;
	}
	/*public static void main(String[]args) throws FileNotFoundException{
		QuestionReader parser=new QuestionReader("data"+GameConstants.separatorChar+"questions.txt");
		parser.processFile();
	}*/
	/**
	 * Reads in from the .txt file using Scanner
	 */
	public final void processFile(){
		Scanner scanner = null;
		try{
			scanner=new Scanner(new FileReader(textFile));
			while(scanner.hasNextLine()){
				processLine(scanner.nextLine());
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}finally{
			scanner.close();
		}
	}
	/**
	 * @param aLine - string to be passed in
	 */
	protected void processLine(String aLine){
		Scanner scanner=new Scanner(aLine);
		scanner.useDelimiter("\t");
		if(scanner.hasNext()){
			questions.add(scanner.next());
			scanner.next();
			correctAns.add(scanner.next());
			wrongAns1.add(scanner.next());
			wrongAns2.add(scanner.next());
			wrongAns3.add(scanner.next());
		}
	}
}
