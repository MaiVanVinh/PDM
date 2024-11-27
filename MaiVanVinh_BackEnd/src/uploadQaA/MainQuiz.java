package uploadQaA;

import java.util.ArrayList;

public class MainQuiz {

    private String name;
    private int quizID;
    private ArrayList<MainQuestion> questions;

    public MainQuiz(int quizID, String name, ArrayList<MainQuestion> questions) {
    	this.quizID = quizID;
        this.name = name;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public int getID() {
    	return quizID;
    }
    
    public ArrayList<MainQuestion> getQuestions() {
        return questions;
    }
}
