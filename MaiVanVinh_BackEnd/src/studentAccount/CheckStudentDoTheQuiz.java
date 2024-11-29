package studentAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connectionSQL.MyConnection;

public class CheckStudentDoTheQuiz {
	
	
	public static int checkStudentQuiz(String studentID,int classID,String quizName) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sql = "select grade from test.student_quiz "
		+ "where student_id = "+studentID+" and class_id = "+classID+" and quiz_name = '"+quizName+"'";
	    int score = -1;

		try {	
			     Class.forName("com.mysql.cj.jdbc.Driver");
			     Connection connection = MyConnection.getConnection();
			     PreparedStatement ps = connection.prepareStatement(sql);
			     ResultSet rs = ps.executeQuery();
			     if(rs.next())
			        score = rs.getInt("grade");			  
			     ps.close();
			     connection.close();
				
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	    return score;
	
	}
	

}
