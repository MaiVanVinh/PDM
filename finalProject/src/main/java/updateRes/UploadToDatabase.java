package updateRes;

import connectionSQL.GetConnection;
import studentAccount.DoTheQuizPane;
import studentAccount.Student_UI;
import uploadQaA.MainAnswer;
import uploadQaA.MainQuestion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;


import java.sql.Statement;


public class UploadToDatabase {
	
	public void pushData(String courseCode,String title, ArrayList<MainQuestion> questions,String duration,String year,String month,String day) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		int dayInt = Integer.parseInt(day);
		int monthInt = Integer.parseInt(month);
		int yearInt = Integer.parseInt(year);
		
		String insertQuiz = "INSERT INTO Exam (title,course_code,duration,deadline) VALUES (?,?,?,?);";
		String sqlQue = ("INSERT INTO Question (exam_id, question_text) VALUES (?,?);");
		String sqlAns = ("INSERT INTO Answer (question_id, answer_text, is_correct) VALUES (?,?,?);");
		boolean isCorrect = false;
		
		Calendar calendar = Calendar.getInstance();
        calendar.set(yearInt, monthInt - 1, dayInt); // month is 0-based in Calendar
        Date eventDate = new Date(calendar.getTimeInMillis());

		
        // Insert quiz
        PreparedStatement quizStmt = GetConnection.getConnection().prepareStatement(insertQuiz,Statement.RETURN_GENERATED_KEYS);
        quizStmt.setString(1, title);
        quizStmt.setString(2, courseCode);
        quizStmt.setString(3, duration);
        quizStmt.setDate(4, eventDate);
        quizStmt.executeUpdate();
        

        ResultSet quizKeys = quizStmt.getGeneratedKeys();
        if (quizKeys.next()) {
            int quizId = quizKeys.getInt(1);

 	     for(MainQuestion q : questions) {
 	    	PreparedStatement psQuestion = GetConnection.getConnection().prepareStatement(sqlQue,Statement.RETURN_GENERATED_KEYS);
	    	psQuestion.setInt(1, quizId);
	    	psQuestion.setString(2, q.getQuestion());
	    	psQuestion.executeUpdate();
	    	
            ResultSet questionKeys = psQuestion.getGeneratedKeys();
            questionKeys.next();
            int questionId = questionKeys.getInt(1);
	    	 
	    	 for(MainAnswer a : q.getAns()) {

	                PreparedStatement answerStmt = GetConnection.getConnection().prepareStatement(sqlAns);
	                if(a.isCorrect().equals("TRUE"))
	                	isCorrect = true;
	                else 
	                	isCorrect = false;
	                
	                answerStmt.setInt(1, questionId);
	                answerStmt.setString(2, a.getOption());
	                answerStmt.setBoolean(3, isCorrect);
	                answerStmt.executeUpdate();
	    		 
	    	 }
	    	 
	    	 
	     }
 	     
        }
		
		  
		  
	}
	
	public void uploadQuestion(int quizID,String classCode,String title, ArrayList<MainQuestion> questions) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		String sqlQue = ("INSERT INTO Question (exam_id, question_text) VALUES (?,?);");
		String sqlAns = ("INSERT INTO Answer (question_id, answer_text, is_correct) VALUES (?,?,?);");
		boolean isCorrect = false;
		
	     for(MainQuestion q : questions) {
	    	PreparedStatement psQuestion = GetConnection.getConnection().prepareStatement(sqlQue,Statement.RETURN_GENERATED_KEYS);
	    	psQuestion.setInt(1, quizID);
	    	psQuestion.setString(2, q.getQuestion());
	    	psQuestion.executeUpdate();
	    	
           ResultSet questionKeys = psQuestion.getGeneratedKeys();
           questionKeys.next();
           int questionId = questionKeys.getInt(1);
	    	 
	    	 for(MainAnswer a : q.getAns()) {
	                if(a.isCorrect().equals("TRUE"))
	                	isCorrect = true;
	                else 
	                	isCorrect = false;
	                PreparedStatement answerStmt = GetConnection.getConnection().prepareStatement(sqlAns);
	                answerStmt.setInt(1, questionId);
	                answerStmt.setString(2, a.getOption());
	                answerStmt.setBoolean(3, isCorrect);
	                answerStmt.executeUpdate();
	    		 
	    	 }
	    	 
	    	 
	     }
	}
	
	public static void uploadStudentQuizScore(int score,String quizName,String courseCode,int i) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sql = "Insert into student_exam(student_id,exam_id,grade,exam_name) Values (?,?,?,?)";
		String studentID = Student_UI.STUDENT_ID;
		int quizID = DoTheQuizPane.QUIZ_ID;
		if(i == 1) 
		   quizID = getExamID(courseCode);
			
		
		
		 Connection connection = GetConnection.getConnection();
		 PreparedStatement ps = connection.prepareStatement(sql);

		 ps.setString(1, studentID);
		 ps.setInt(2, quizID);
		 ps.setInt(3, score);
		 ps.setString(4, quizName);		 
		 ps.executeUpdate(); 
		 ps.close();
		 connection.close();
	}
	
	private static int getExamID(String courseCode) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sql = "Select exam_id from test.exam where course_code = '"+courseCode+"'";
		int id = 0;
		Connection connection = GetConnection.getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);
		try (ResultSet rs = ps.executeQuery()) {
             if(rs.next()) 
               id = rs.getInt("exam_id");    
         }
		
		return id;
	}


}
