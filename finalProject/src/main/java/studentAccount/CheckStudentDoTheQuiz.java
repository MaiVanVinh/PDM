package studentAccount;

import connectionSQL.GetConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CheckStudentDoTheQuiz {
	
	
	public static int checkStudentQuiz(String studentID,int classID,String examName,String courseCode) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
	    int score = -1;
        String query = "select grade \n"
        		+ "from test.student_exam\n"
        		+ "where exam_id in (select exam_id\n"
        		+ "                  from test.exam\n"
        		+ "                  join course on exam.course_code = course.course_code\n"
        		+ "                  where exam.course_code = '"+courseCode+"')\n"
        		+ "and exam_name = '"+examName+"'  and student_id = "+studentID;
        
        System.out.println(query);
		try {	
			     Class.forName("com.mysql.cj.jdbc.Driver");
			     Connection connection = GetConnection.getConnection();
			     PreparedStatement ps = connection.prepareStatement(query);
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
