package uploadQaA;

import java.util.ArrayList;

public class MainQuestion {
    
	private String question;
	private ArrayList<MainAnswer> answer;
	private int questionID;
	
	public MainQuestion() {

	}
	
	public MainQuestion(String Title, ArrayList<MainAnswer> ans) {
		this.question = Title;
		this.answer = ans;
	}
	
	public MainQuestion(int questionID,String Title, ArrayList<MainAnswer> ans) {
		this.question = Title;
		this.answer = ans;
		this.questionID = questionID;
	}
	

	
	public void setQuestion(int questionID,String Title,  ArrayList<MainAnswer> ans) {
		this.question = Title;
		this.answer = ans;
		this.questionID = questionID;
	}
	
	public int getID() {
		return questionID;
	}
	
	public String getQuestion() {
		return question;
	}
	
	
	public ArrayList<MainAnswer> getAns(){
		return answer;
	}
	
	
	
}
