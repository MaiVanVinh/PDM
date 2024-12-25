package updateRes;

import connectionSQL.GetConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class LoadCreatedClass {

	private ArrayList<String> classInfo;
	private ArrayList<String> courseNames;
	private HashMap<String,String> classes = new HashMap<>();
	private int numberOfComponents;
	public static String ID;
	   
	 public LoadCreatedClass(){
		 classInfo = new ArrayList<>();
		 courseNames = new ArrayList<>(); 
		 classes = new HashMap<>();
	 }
	 
	 public void getCourseName() throws ClassNotFoundException {
		    Class.forName("com.mysql.cj.jdbc.Driver"); 
	        String query = "SELECT course_name FROM test.course WHERE teacher_id = 1";

	        try {
	            
	            Connection conn = GetConnection.getConnection();

	            PreparedStatement st = conn.prepareStatement(query);
	            
	            ResultSet rs = st.executeQuery();

	            while (rs.next()) {
	            	courseNames.add(rs.getString("course_name"));
 
	            }
	            

	            rs.close();
	            st.close();
	            conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	 }
 
	 public void getCreatedClass() throws ClassNotFoundException {

		    Class.forName("com.mysql.cj.jdbc.Driver"); 
	        String query = "SELECT course_code, course_name, pass_word FROM test.course WHERE teacher_id = "+ID;

	        try {
	            
	            Connection conn = GetConnection.getConnection();
	            PreparedStatement st = conn.prepareStatement(query);	            
	            ResultSet rs = st.executeQuery();

	            while (rs.next()) {
	            	numberOfComponents += 3;
	                String tempContainer = rs.getString("course_code");
	                String tempContainer1 = rs.getString("course_name");
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
	      String query = "SELECT course_id, course_name FROM test.student_course WHERE student_id = "+studentID;
	      ArrayList<String> studentClass = new ArrayList<>();
	      String classCode = "";
          classes.clear();

	        try {
	            
	            Connection conn = GetConnection.getConnection();

	            PreparedStatement st = conn.prepareStatement(query);
	            
	            ResultSet rs = st.executeQuery();

	            while (rs.next()) {
	            	studentClass.add(rs.getString("course_name"));
	            	classCode = getClassCode(rs.getInt("course_id"),rs.getString("course_name"));
	            	classes.put(rs.getString("course_name"),classCode);
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
	     String query = "SELECT course_code FROM test.course WHERE id_course = "+classID+" and course_name = '"+className+"'";
         String code = "";
	        try {
	            
	            Connection conn = GetConnection.getConnection();

	            PreparedStatement st = conn.prepareStatement(query);
	            
	            ResultSet rs = st.executeQuery();

	            if(rs.next()) {
	            	code = rs.getString("course_code");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
 
	        return code;
	 }
	 
	 public ArrayList<String> getInfoClass() {
		 return classInfo;
	 }
	 
	 public ArrayList<String> getCourseNames() {
		 return courseNames;
	 }
	 
	 
	 public HashMap<String,String> getHashMap(){
		 return classes;
	 }
	 
	 
	 public int getNum() {
		 return numberOfComponents;
	 }
	 

	
}

