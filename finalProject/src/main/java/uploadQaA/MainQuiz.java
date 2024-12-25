package uploadQaA;

import java.util.ArrayList;

public class MainQuiz {

    private String name;
    private int quizID;
    private int day;
    private int month;
    private int year;
    private String duration;
    private ArrayList<MainQuestion> questions;

    public MainQuiz(int quizID, String name, ArrayList<MainQuestion> questions,String duration,int day,int month,int year) {
    	this.quizID = quizID;
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
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
    
    public int getDay() {
    	return day;
    }
    
    public int getMonth() {
    	return month;
    }
    public int getYear() {
    	return year;
    }
    public ArrayList<MainQuestion> getQuestions() {
        return questions;
    }
}
