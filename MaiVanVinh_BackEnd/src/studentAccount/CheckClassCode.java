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
	     String sql = "SELECT id_class, class_name, pass_word FROM test.class WHERE class_code = '"+classCode+"'";
         String temp1 = null;

	     
	        try (Connection conn = MyConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	             ResultSet rs = ps.executeQuery();
                 if(rs.next()) { 
                	temp1 = rs.getString("pass_word");
                	classID = rs.getInt("id_class");
                	className = rs.getString("class_name");
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
	    String sql = "SELECT class_name, student_id, class_id FROM test.student_class WHERE student_id = "+studentID+" and class_id = "+classID;
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
	    String insertQuery = "INSERT INTO Student_Class (student_id, class_id, class_name) VALUES ("+studentID+","+classID+",'"+className+"')";
        System.out.println(studentID);
	    try (Connection conn = MyConnection.getConnection();
	        PreparedStatement ps = conn.prepareStatement(insertQuery)) {
	        int rowsInserted = ps.executeUpdate();
	        if (rowsInserted > 0) {
	            System.out.println("Student ID " + studentID + " successfully added to Class ID " + classID + " Class Name: "+className);
	            return true;
	        }

	    } catch (SQLException e) {
	        
	        System.err.println("Error inserting Student ID " + studentID + " into Class ID " + classID+ " Class Name: "+className);
	        e.printStackTrace();
	    }

	    return false; 
	}


}
