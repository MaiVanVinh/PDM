package updateRes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import connectionSQL.MyConnection;



public class LoadCreatedClass {

	private ArrayList<String> classInfo;
	private HashMap<String,String> classes = new HashMap<>();
	private int numberOfComponents;
	public static String ID;
	   
	 public LoadCreatedClass(){
		 classInfo = new ArrayList<>();
		 classes = new HashMap<>();
	 }
	 
	 
	 
	 
	 public void getCreatedClass() throws ClassNotFoundException {

		    Class.forName("com.mysql.cj.jdbc.Driver"); 
	        String query = "SELECT class_code, class_name, pass_word FROM test.class WHERE teacher_id = "+ID;

	        try {
	            
	            Connection conn = MyConnection.getConnection();

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
	        

	        
	 }
	 
	 
	 public ArrayList<String> loadStudentClass(String studentID) throws ClassNotFoundException {
		  Class.forName("com.mysql.cj.jdbc.Driver"); 
	      String query = "SELECT class_id, class_name FROM test.student_class WHERE student_id = "+studentID;
	      ArrayList<String> studentClass = new ArrayList<>();
	      String classCode = "";
          classes.clear();

	        try {
	            
	            Connection conn = MyConnection.getConnection();

	            PreparedStatement st = conn.prepareStatement(query);
	            
	            ResultSet rs = st.executeQuery();

	            while (rs.next()) {
	            	studentClass.add(rs.getString("class_name"));
	            	classCode = getClassCode(rs.getInt("class_id"),rs.getString("class_name"));
	            	classes.put(rs.getString("class_name"),classCode);
	            }
	           
	            rs.close();
	            st.close();
	            conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
  
        return studentClass;
		 
	 }
	 
	 private String getClassCode(int classID, String className) throws ClassNotFoundException {
		 Class.forName("com.mysql.cj.jdbc.Driver"); 
	     String query = "SELECT class_code FROM test.class WHERE id_class = "+classID+" and class_name = '"+className+"'";
         String code = "";
	        try {
	            
	            Connection conn = MyConnection.getConnection();

	            PreparedStatement st = conn.prepareStatement(query);
	            
	            ResultSet rs = st.executeQuery();

	            if(rs.next()) {
	            	code = rs.getString("class_code");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
 
	        return code;
	 }
	 
	 public ArrayList<String> getInfoClass() {
		 return classInfo;
	 }
	 
	 public HashMap<String,String> getHashMap(){
		 return classes;
	 }
	 
	 
	 public int getNum() {
		 return numberOfComponents;
	 }
	 

	
}

