package teacherAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import connectionSQL.MyConnection;


public class CheckDuplicatedQuiz {
	
	public static boolean checkDuplicated(String courseCode, String quizName) throws ClassNotFoundException {
	    Class.forName("com.mysql.cj.jdbc.Driver"); 
        String sql = "Select title from test.exam where course_code ='"+courseCode+"'";
        ArrayList<String> tempList = new ArrayList<>();
        
        boolean checkDuplicate = false;
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {           
             ResultSet rs = ps.executeQuery();
             while(rs.next())
            	 tempList.add(rs.getString("title"));
		     ps.close();
		     conn.close();
		     
		     
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        for(String s : tempList) {
        	if(quizName.equals(s))
        		checkDuplicate = true;
        }
  
        return checkDuplicate;
	}
}
