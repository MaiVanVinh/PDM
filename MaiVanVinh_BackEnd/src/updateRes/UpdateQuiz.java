package updateRes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectionSQL.MyConnection;
import uploadQaA.MainAnswer;
import uploadQaA.MainQuestion;

public class UpdateQuiz {

	private ArrayList<MainQuestion> questions;
	private ArrayList<Integer> previousAnswerID;
	private ArrayList<Integer> deleteList;
	private ArrayList<Integer> questionIDList;
	private ArrayList<String> SQL;
	private boolean needDelete;
	private boolean needAdd;	
	private boolean deleteAll;
	private int questionID;
	

	
	public UpdateQuiz() {
		SQL = new ArrayList<>();
		questions = new ArrayList<>();
		previousAnswerID = new ArrayList<>();
		questionIDList = new ArrayList<>();
		deleteList = new ArrayList<>();
	}
	
	public void updateData() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		
		StringBuilder sqlInsert = new StringBuilder();
		sqlInsert.append("Insert into Answer (question_id, answer_text, is_correct) VALUES (?,?,?);");
		boolean check = false;
		MainQuestion q = questions.get(0);
		
		getPreviousAnswerIDData();
		System.out.println("num: "+questions.get(0).getAns().size());
		if(questions.get(0).getAns().size() > previousAnswerID.size()) 
			generateInsertSQL();
		else	
		    generateSQL();
		
       
	
		try (Connection conn = MyConnection.getConnection()) {	             
       	     
			 if(!needAdd && !deleteAll) {
        	   try(PreparedStatement ps = conn.prepareStatement(SQL.get(0))) {
        	   ps.executeUpdate(); 
        	   System.out.println("ok");
	           }
			 }
			 
        	 if(needAdd) {
        		 try (PreparedStatement ps = conn.prepareStatement(SQL.get(0))) {
        			    System.out.println(SQL.get(0));
        			    ps.executeUpdate();
        			    System.out.println("ok");
        			} catch (SQLException e) {
        			    e.printStackTrace(); 
        			}

        			for (MainAnswer a : q.getAns()) {
        			    try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert.toString())) {
        			        if(a.isCorrect().equals("TRUE"))
        			        	check = true;
        			        else
        			        	check = false;
        			    	psInsert.setInt(1, this.questionID);
        			        psInsert.setString(2, a.getOption());
        			        psInsert.setBoolean(3, check); 
        			        psInsert.executeUpdate();
        			    } catch (SQLException e) {
        			        e.printStackTrace(); 
        			    }
        			}

        			// Resetting flags (if needed)
        			needAdd = false;
        			needDelete = false;

        	 }
        	 
             if(needDelete) {              
               try(PreparedStatement ps = conn.prepareStatement(SQL.get(1))) {
               ps.executeUpdate(); 
               System.out.println("ok");
      	       }
             }
             
             conn.close();
        }
	}
	
 
	
	public void getPreviousAnswerIDData() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		String sql = "Select answer_id from test.answer where question_id = "+this.questionID;

		try (Connection conn = MyConnection.getConnection();
		        PreparedStatement stmt = conn.prepareStatement(sql)) {
	            try (ResultSet rs = stmt.executeQuery()) {
	             while (rs.next()) {
	            	 previousAnswerID.add(rs.getInt("answer_id"));
	             }
	    
	            }
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }

       
	}
	
	private void generateInsertSQL() {
		
		StringBuilder sqlDelete = new StringBuilder();
		sqlDelete.append("Delete from test.answer where question_id = "+this.questionID+" and answer_id in ( ");
        
		   if(questions.get(0).getAns().size() > previousAnswerID.size()) {
			   needAdd = true;
			   needDelete = false;
				for(int j = 0; j < previousAnswerID.size(); j++) {
					int size = previousAnswerID.size();
					sqlDelete.append(previousAnswerID.get(j));
	            	   if(j < size - 1)
	            		   sqlDelete.append(",");
				}sqlDelete.append(")");
				SQL.add(sqlDelete.toString());
			}
	}
	
    private void generateSQL() {
		
		StringBuilder sqlUpdate = new StringBuilder();
		sqlUpdate.append("Update test.answer Set answer_text = Case WHEN answer_id = ");
		
		StringBuilder sqlDelete = new StringBuilder();
		sqlDelete.append("Delete from test.answer where question_id = "+this.questionID+" and answer_id in ( ");
        
		if(questions.get(0).getAns().size() == previousAnswerID.size()) {
            for(int index = 0; index < previousAnswerID.size(); index++) {
         	   int size = previousAnswerID.size();
         	   sqlDelete.append(previousAnswerID.get(index));
         	   if(index < size - 1)
         		   sqlDelete.append(",");
            }sqlDelete.append(")");
            SQL.add(sqlDelete.toString());
            needAdd = true;
            return;
		}
		
		   int i = 0;
		   for(MainQuestion q : questions) {
			  for(MainAnswer a : q.getAns()) {
				  int size = q.getAns().size();	
				  sqlUpdate.append(previousAnswerID.get(i)+" Then"+" '" +a.getOption()+"' ");
				  if(i < size - 1) {
				    sqlUpdate.append("WHEN answer_id = ");
				  }i++;
				
			  }
		   }sqlUpdate.append("Else answer_text End WHERE answer_id IN (");
		   
		   for(int j = 0; j < previousAnswerID.size(); j++) {
			   int size = previousAnswerID.size();
			   sqlUpdate.append(previousAnswerID.get(j));
			   if(j < size - 1)
				   sqlUpdate.append(",");
		   }sqlUpdate.append(")");
		   SQL.add(sqlUpdate.toString());
		      
			

		   if(questions.get(0).getAns().size() < previousAnswerID.size()) {
			   int loop = previousAnswerID.size() - questions.get(0).getAns().size();
			   int n = (previousAnswerID.size() - loop);
			   if(loop == previousAnswerID.size())
				   deleteAll = true;
			   else
				   deleteAll = false;
			   
			   for(int m = 0; m < loop; m++) {
				   if(n >= 0 && !deleteAll)
				      deleteList.add(previousAnswerID.get(n--));
				   if(deleteAll)
					   deleteList.add(previousAnswerID.get(m));
			   }
		   }
		   
		   if(deleteList.size() > 0) {
               for(int index = 0; index < deleteList.size(); index++) {
            	   int size = deleteList.size();
            	   sqlDelete.append(deleteList.get(index));
            	   if(index < size - 1)
            		   sqlDelete.append(",");
               }sqlDelete.append(")");
               SQL.add(sqlDelete.toString());
               needDelete = true;
		   }
	
		   
    }
	
	public void getReady(MainQuestion question,int id) {
		questions.add(question);
        this.questionID = id;

	}
	
	public ArrayList<Integer> getQuestionID(int quizID) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		String sql = "Select question_id from test.question where quiz_id =" + quizID;
		

		try (Connection conn = MyConnection.getConnection();
		        PreparedStatement stmt = conn.prepareStatement(sql)) {
	            try (ResultSet rs = stmt.executeQuery()) {
	             while (rs.next()) {	            
	            	 questionIDList.add(rs.getInt("question_id"));
	             }
	    
	            }
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }

		return questionIDList;
	}
	
	public void clear() {
		questionIDList.clear();
		questions.clear();
		previousAnswerID.clear();
		deleteList.clear();
		SQL.clear();
		needDelete = false;
		needAdd = false;
	}
	
	public ArrayList<MainQuestion> getQuestion(){
		return questions;
	}
}
