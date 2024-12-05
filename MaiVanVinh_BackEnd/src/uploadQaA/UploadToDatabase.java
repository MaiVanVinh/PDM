package uploadQaA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import connectionSQL.MyConnection;
import studentAccount.DoTheQuizPane;
import studentAccount.Student_UI;

import java.sql.Statement;


public class UploadToDatabase {
	
	public void pushData(String courseCode,String title, ArrayList<MainQuestion> questions,String duration) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		String insertQuiz = "INSERT INTO Exam (title,course_code,duration) VALUES (?,?,?);";
		String sqlQue = ("INSERT INTO Question (exam_id, question_text) VALUES (?,?);");
		String sqlAns = ("INSERT INTO Answer (question_id, answer_text, is_correct) VALUES (?,?,?);");
		boolean isCorrect = false;
		

		
        // Insert quiz
        PreparedStatement quizStmt = MyConnection.getConnection().prepareStatement(insertQuiz,Statement.RETURN_GENERATED_KEYS);
        quizStmt.setString(1, title);
        quizStmt.setString(2, courseCode);
        quizStmt.setString(3, duration);
        quizStmt.executeUpdate();
        

        ResultSet quizKeys = quizStmt.getGeneratedKeys();
        if (quizKeys.next()) {
            int quizId = quizKeys.getInt(1);

 	     for(MainQuestion q : questions) {
 	    	PreparedStatement psQuestion = MyConnection.getConnection().prepareStatement(sqlQue,Statement.RETURN_GENERATED_KEYS);
	    	psQuestion.setInt(1, quizId);
	    	psQuestion.setString(2, q.getQuestion());
	    	psQuestion.executeUpdate();
	    	
            ResultSet questionKeys = psQuestion.getGeneratedKeys();
            questionKeys.next();
            int questionId = questionKeys.getInt(1);
	    	 
	    	 for(MainAnswer a : q.getAns()) {

	                PreparedStatement answerStmt = MyConnection.getConnection().prepareStatement(sqlAns);
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
	    	PreparedStatement psQuestion = MyConnection.getConnection().prepareStatement(sqlQue,Statement.RETURN_GENERATED_KEYS);
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
	                PreparedStatement answerStmt = MyConnection.getConnection().prepareStatement(sqlAns);
	                answerStmt.setInt(1, questionId);
	                answerStmt.setString(2, a.getOption());
	                answerStmt.setBoolean(3, isCorrect);
	                answerStmt.executeUpdate();
	    		 
	    	 }
	    	 
	    	 
	     }
	}
	
	public static void uploadStudentQuizScore(int score,String quizName) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sql = "Insert into student_exam(student_id,exam_id,grade,exam_name) Values (?,?,?,?)";
		String studentID = Student_UI.STUDENT_ID;
//		int classID = ShowStudentQuiz.CLASS_ID;
		int quizID = DoTheQuizPane.QUIZ_ID;
		
		 Connection connection = MyConnection.getConnection();
		 PreparedStatement ps = connection.prepareStatement(sql);

		 ps.setString(1, studentID);
		 ps.setInt(2, quizID);
		 ps.setInt(3, score);
		 ps.setString(4, quizName);
		 
		 ps.executeUpdate(); 
		 JOptionPane.showMessageDialog(null, "Successfully", "Warning!", JOptionPane.WARNING_MESSAGE);
		 ps.close();
		 connection.close();
	}
	


}
