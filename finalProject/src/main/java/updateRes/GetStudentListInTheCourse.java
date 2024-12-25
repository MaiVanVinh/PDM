package updateRes;

import connectionSQL.GetConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class GetStudentListInTheCourse {

	int rowCounter;
	private String[] columns;
	private Object[] row;
	private Object[][] multipleStudentRows;
	
	public void getListWithExam(String courseName) throws ClassNotFoundException {

		Class.forName("com.mysql.cj.jdbc.Driver");
		String sql ="select student.lastName, c.student_id, student_exam.grade, student_exam.exam_name\r\n"
				+ "from test.student_course c\r\n"
				+ "join student_exam on c.student_id = student_exam.student_id\r\n"
				+ "join student on c.student_id = student.id_student\r\n"
				+ "where course_name = '"+courseName+"';";
				
		
		try (Connection conn = GetConnection.getConnection();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {

		          ResultSetMetaData metaData = rs.getMetaData();
		          int columnCount = metaData.getColumnCount();
		          
		          columns = new String[columnCount];
		          for (int i = 1; i <= columnCount; i++) {
		        	  columns[i - 1] = metaData.getColumnName(i);
	              }

		          
		          ArrayList<Object[]> temp = new ArrayList<>();
		          
		          while (rs.next()) {
	                  row = new Object[columnCount];
	                  for (int i = 1; i <= columnCount; i++) {
	                	  row[i - 1] = rs.getObject(i);
	                  }
	                  temp.add(row);
	              }
		          rowCounter = temp.size();
		          multipleStudentRows = new Object[temp.size()][];
		          for(int j = 0; j < temp.size(); j++) {
		        	  multipleStudentRows[j] = temp.get(j);
		          }

		      }catch (SQLException e) {
		         e.printStackTrace();
		      }

	}
	
	public void getStudentInformation(String courseName) throws ClassNotFoundException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sql ="select  c.student_id,student.lastName, c.course_name\r\n"
				+ "from test.student_course c\r\n"
				+ "join student on c.student_id = student.id_student\r\n"
				+ "where course_name = '"+courseName+"'\r\n"
				+ "order by student.lastName ASC;";
				
		
		try (Connection conn = GetConnection.getConnection();
		          Statement stmt = conn.createStatement();
		          ResultSet rs = stmt.executeQuery(sql)) {

		          ResultSetMetaData metaData = rs.getMetaData();
		          int columnCount = metaData.getColumnCount();
		          
		          columns = new String[columnCount];
		          for (int i = 1; i <= columnCount; i++) {
		        	  columns[i - 1] = metaData.getColumnName(i);
	              }

		          
		          ArrayList<Object[]> temp = new ArrayList<>();
		          
		          while (rs.next()) {
	                  row = new Object[columnCount];
	                  for (int i = 1; i <= columnCount; i++) {
	                	  row[i - 1] = rs.getObject(i);
	                  }
	                  temp.add(row);
	              }
		          rowCounter = temp.size();
		          multipleStudentRows = new Object[temp.size()][];
		          for(int j = 0; j < temp.size(); j++) {
		        	  multipleStudentRows[j] = temp.get(j);
		          }

		      }catch (SQLException e) {
		         e.printStackTrace();
		      }

	}
	
	
	public String[] getColumns() {
		return columns;
	}
	
	public Object[][] getRows() {
		return multipleStudentRows;
	}
	
	public int getCount() {
		return rowCounter;
	}
}
