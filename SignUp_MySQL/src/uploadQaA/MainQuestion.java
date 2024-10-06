package uploadQaA;

import java.util.ArrayList;

public class MainQuestion {
    
	private String question;
	private ArrayList<MainAnswer> answer;
	
	public MainQuestion(String Title, ArrayList<MainAnswer> ans) {
		this.question = Title;
		this.answer = ans;
	}
	
	public String getQuestion() {
		return question;
	}
	
	
	public ArrayList<MainAnswer> getAns(){
		return answer;
	}
	
	
	
}

