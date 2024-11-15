package uploadQaA;

import java.util.ArrayList;

public class MainQuiz {

    private String name;
    private ArrayList<MainQuestion> questions;

    public MainQuiz(String name, ArrayList<MainQuestion> questions) {
        this.name = name;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public ArrayList<MainQuestion> getQuestions() {
        return questions;
    }
}
