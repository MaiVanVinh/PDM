package uploadQaA;

public class MainAnswer {
	
	private String op;
	private String isCorrect;
	private int answerID;

	
	public MainAnswer(String option, String isCorrect) {
		this.op = option;
		this.isCorrect = isCorrect;
	}
	
	public MainAnswer(int answerID,String option, String isCorrect) {
		this.op = option;
		this.isCorrect = isCorrect;
		this.answerID = answerID;
	}
	
	public String getOption() {
		return op;
	}
	
	public String isCorrect() {
		return isCorrect;
	}
	
    public int getID() {
    	return answerID;
    }

}
