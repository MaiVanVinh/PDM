package uploadQaA;

import java.util.ArrayList;

public class MainQuiz {

    private String name;
    private int quizID;
    private String duration;
    private ArrayList<MainQuestion> questions;

    public MainQuiz(int quizID, String name, ArrayList<MainQuestion> questions,String duration) {
    	this.quizID = quizID;
        this.name = name;
        this.questions = questions;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
    	return duration;
    }
    
    public int getID() {
    	return quizID;
    }
    
    public ArrayList<MainQuestion> getQuestions() {
        return questions;
    }
}
