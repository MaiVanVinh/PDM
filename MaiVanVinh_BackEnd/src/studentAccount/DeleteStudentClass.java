package studentAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import connectionSQL.MyConnection;

public class DeleteStudentClass {

	public static void deleteStudentClass(ArrayList<String> studentClass, String studentID) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		StringBuilder sql = new StringBuilder("delete from student_class where student_id = "+studentID+" and class_name in ('");

	     
         for(int i = 0; i < studentClass.size(); i++) {
       	 sql.append(studentClass.get(i));
       	 if(i < studentClass.size() - 1)
       		 sql.append("', '");
         }sql.append("')");
 
       System.out.println(sql);

       try (Connection conn = MyConnection.getConnection()) {	             
       	 
       	    try(PreparedStatement ps = conn.prepareStatement(sql.toString())) {
       	    ps.executeUpdate(); 
	        }

       }
       
	}
	
	
}
