package studentAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connectionSQL.MyConnection;

public class CheckClassCode {
	private int classID;
	private String className;
	
	public int getClassID() {
		return classID;
	}
	
	public String getName() {
		return className;
	}
	

	
	public boolean checkDatabase(String classCode, String pass) throws ClassNotFoundException {
		 Class.forName("com.mysql.cj.jdbc.Driver"); 
	     String sql = "SELECT id_course, course_name, pass_word FROM test.course WHERE course_code = '"+classCode+"'";
         String temp1 = null;

	     
	        try (Connection conn = MyConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	             ResultSet rs = ps.executeQuery();
                 if(rs.next()) { 
                	temp1 = rs.getString("pass_word");
                	classID = rs.getInt("id_course");
                	className = rs.getString("course_name");
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
	
	public boolean checkAvailability(String studentID, int classID) throws ClassNotFoundException {

		Class.forName("com.mysql.cj.jdbc.Driver"); 
	    String sql = "SELECT course_name, student_id, course_id FROM test.student_course WHERE student_id = "+studentID+" and course_id = "+classID;
        boolean check = false;
	     
	        try (Connection conn = MyConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	             ResultSet rs = ps.executeQuery();
                if(rs.next()) 
                   check = true;
                

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        

           return check;
		
	}
	
	public boolean addStudentToClass(String studentID, int classID,String className) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver"); 
	    String insertQuery = "INSERT INTO student_course (student_id, course_id, course_name) VALUES ("+studentID+","+classID+",'"+className+"')";
        System.out.println(studentID);
	    try (Connection conn = MyConnection.getConnection();
	        PreparedStatement ps = conn.prepareStatement(insertQuery)) {
	        int rowsInserted = ps.executeUpdate();
	        if (rowsInserted > 0) {
	            System.out.println("Student ID " + studentID + " successfully added to Course ID " + classID + " Course Name: "+className);
	            return true;
	        }

	    } catch (SQLException e) {
	        
	        System.err.println("Error inserting Student ID " + studentID + " into Course ID " + classID+ " Course Name: "+className);
	        e.printStackTrace();
	    }

	    return false; 
	}


}
