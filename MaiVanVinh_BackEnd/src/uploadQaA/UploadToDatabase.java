package uploadQaA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;


public class UploadToDatabase {
	
	public void pushData(String classCode,String title, ArrayList<MainQuestion> questions) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		String insertQuiz = "INSERT INTO Quiz (title,class_code) VALUES (?,?);";
		String sqlQue = ("INSERT INTO Question (quiz_id, question_text) VALUES (?,?);");
		String sqlAns = ("INSERT INTO Answer (question_id, answer_text, is_correct) VALUES (?,?,?);");
		
		

		
        // Insert quiz
        PreparedStatement quizStmt = conn().prepareStatement(insertQuiz,Statement.RETURN_GENERATED_KEYS);
        quizStmt.setString(1, title);
        quizStmt.setString(2, classCode);
        quizStmt.executeUpdate();
        

        ResultSet quizKeys = quizStmt.getGeneratedKeys();
        if (quizKeys.next()) {
            int quizId = quizKeys.getInt(1);

 	     for(MainQuestion q : questions) {
 	    	PreparedStatement psQuestion = conn().prepareStatement(sqlQue,Statement.RETURN_GENERATED_KEYS);
	    	psQuestion.setInt(1, quizId);
	    	psQuestion.setString(2, q.getQuestion());
	    	psQuestion.executeUpdate();
	    	
            ResultSet questionKeys = psQuestion.getGeneratedKeys();
            questionKeys.next();
            int questionId = questionKeys.getInt(1);
	    	 
	    	 for(MainAnswer a : q.getAns()) {

	                PreparedStatement answerStmt = conn().prepareStatement(sqlAns);
	                answerStmt.setInt(1, questionId);
	                answerStmt.setString(2, a.getOption());
	                answerStmt.setString(3, a.isCorrect());
	                answerStmt.executeUpdate();
	    		 
	    	 }
	    	 
	    	 
	     }
 	     
        }
		
		  
		  
	}
	
	private Connection conn() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","maytinhcasio580");
		return conn;
	}
	
	



}
