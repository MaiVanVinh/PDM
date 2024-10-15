package uploadQaA;

public class MainAnswer {
	
	private String op;
	private String isCorrect;

	
	public MainAnswer(String option, String isCorrect) {
		this.op = option;
		this.isCorrect = isCorrect;
	}
	
	public String getOption() {
		return op;
	}
	
	public String isCorrect() {
		return isCorrect;
	}
	


}
