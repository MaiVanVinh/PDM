package studentAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connectionSQL.GetConnection;



public class CheckClassCode {
	private int courseID;
	private String courseName;
	
	public int getClassID() {
		return courseID;
	}
	
	public String getName() {
		return courseName;
	}
	

	
	public boolean checkDatabase(String courseCode, String pass) throws ClassNotFoundException {
		 Class.forName("com.mysql.cj.jdbc.Driver"); 
	     String sql = "SELECT id_course, course_name, pass_word FROM test.course WHERE course_code = '"+courseCode+"'";
         String temp1 = null;

	     
	        try (Connection conn = GetConnection.getConnection();
				 PreparedStatement ps = conn.prepareStatement(sql)) {
	             ResultSet rs = ps.executeQuery();
                 if(rs.next()) { 
                	temp1 = rs.getString("pass_word");
                	courseID = rs.getInt("id_course");
                	courseName = rs.getString("course_name");
                 }else 
                	return false; 

			     ps.close();
			     conn.close();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
		if(temp1.equals(pass))
			return true;
		else
			return false;
					
	}
	
	public boolean checkAvailability(String studentID, int courseID) throws ClassNotFoundException {

		Class.forName("com.mysql.cj.jdbc.Driver"); 
	    String sql = "SELECT course_name, student_id, course_id FROM test.student_course WHERE student_id = "+studentID+" and course_id = "+courseID;
        boolean check = false;
	     
	        try (Connection conn = GetConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	             ResultSet rs = ps.executeQuery();
                if(rs.next()) 
                   check = true;
                

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        

           return check;
		
	}
	
	public boolean addStudentToClass(String studentID, int courseID,String courseName) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver"); 
	    String insertQuery = "INSERT INTO student_course (student_id, course_id, course_name) VALUES ("+studentID+","+courseID+",'"+courseName+"')";
        System.out.println(studentID);
	    try (Connection conn = GetConnection.getConnection();
	        PreparedStatement ps = conn.prepareStatement(insertQuery)) {
	        int rowsInserted = ps.executeUpdate();
	        if (rowsInserted > 0) {
	            System.out.println("Student ID " + studentID + " successfully added to Course ID " + courseID + " Course Name: "+courseName);
	            return true;
	        }

	    } catch (SQLException e) {
	        
	        System.err.println("Error inserting Student ID " + studentID + " into Course ID " + courseID+ " Course Name: "+courseName);
	        e.printStackTrace();
	    }

	    return false; 
	}


}
