package org.mvfbla.cgs2012;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class QuestionReader {
	static ArrayList<String> questions=new ArrayList<String>();
	static ArrayList<String> correctAns=new ArrayList<String>();
	static ArrayList<String> wrongAns1=new ArrayList<String>();
	static ArrayList<String> wrongAns2=new ArrayList<String>();
	static ArrayList<String> wrongAns3=new ArrayList<String>();
	private final File textFile;
	public QuestionReader(String fileName){
		textFile=new File(fileName);
		processFile();
	}
	/*public static void main(String[]args) throws FileNotFoundException{
		QuestionReader parser=new QuestionReader("data\\questions.txt");
		parser.processFile();
	}*/
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
	public ArrayList<String>getQuestions(){
		return questions;
	}
	public ArrayList<String>getCorrectAns(){
		return correctAns;
	}
	public ArrayList<String>getWrongAns1(){
		return wrongAns1;
	}
	public ArrayList<String>getWrongAns2(){
		return wrongAns2;
	}
	public ArrayList<String>getWrongAns3(){
		return wrongAns3;
	}
}
