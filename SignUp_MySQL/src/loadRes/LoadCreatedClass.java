package loadRes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class LoadCreatedClass {

	private ArrayList<String> classInfo;
	private int numberOfComponents;
	public static String ID;
	   
	 public LoadCreatedClass(){
		 classInfo = new ArrayList<>();
	 }
	 
	 
	 
	 
	 public boolean getCreatedClass() throws ClassNotFoundException {

		    Class.forName("com.mysql.cj.jdbc.Driver"); 
	        String query = "SELECT class_code, class_name, pass_word FROM test.class WHERE teacher_id = "+ID;

	        try {
	            
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","maytinhcasio580");

	            PreparedStatement st = conn.prepareStatement(query);
	            
	            ResultSet rs = st.executeQuery();

	            while (rs.next()) {
	            	numberOfComponents += 3;
	                String tempContainer = rs.getString("class_code");
	                String tempContainer1 = rs.getString("class_name");
	                String tempContainer2 = rs.getString("pass_word");
	                
	                classInfo.add(tempContainer);
	                classInfo.add(tempContainer1);
	                classInfo.add(tempContainer2);
	                
	            }

	            rs.close();
	            st.close();
	            conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        if(numberOfComponents > 0)
	        	return true;
	        else 
	        	return false;
	        
	    }
	 
	 
	 
	 
	 public ArrayList<String> getInfoClass() {
		 return classInfo;
	 }
	 
	 
	 public int getNum() {
		 return numberOfComponents;
	 }
	 

	
}
