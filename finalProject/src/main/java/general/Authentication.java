package general;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import connectionSQL.GetConnection;
import studentAccount.Student_UI;
import teacherAccount.Teacher_UI;

public class Authentication {
	
    public static boolean checkLogin(char[] password,String nameToCheck, String selection) throws HeadlessException, ClassNotFoundException {
        if (verifyAccount(nameToCheck, password, selection)) {
            SignIn_Window.checkUsername = true;
            return true;
        }
        return false;
    }

    public static boolean verifyAccount(String nameToCheck, char[] pass, String selection) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String password = new String(pass);
        String sql_student = "SELECT user_ID, pass_word FROM test.accounts WHERE role = 'Student' and user_Name = ?;";
        String sql_teacher = "SELECT user_ID, pass_word FROM test.accounts WHERE role = 'Teacher' and user_Name = ?;";
        String sql = null;

        SignIn_Window.id = null;

        if (selection.equals("Student"))
            sql = sql_student;
        else if (selection.equals("Teacher"))
            sql = sql_teacher;
        else
            JOptionPane.showMessageDialog(null, "Your selection is empty", "Warning!", JOptionPane.WARNING_MESSAGE);

        boolean checkAuth = false;
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nameToCheck);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("pass_word");

                if (storedPassword.equals(password)) {
                    checkAuth = true;
                    SignIn_Window.id = rs.getString("user_ID");
                    getTeacherOrStudentID( SignIn_Window.id, selection);
                }
            }

            if (!checkAuth)
                JOptionPane.showMessageDialog(null, "Wrong username or password", "Warning!", JOptionPane.WARNING_MESSAGE);

            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return checkAuth;
    }
    
    public static boolean checkExistedAccount(String nameToCheck, String selection) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String sql = "SELECT user_Name FROM test.accounts WHERE user_Name = ?;";

        boolean checkExist = true;
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nameToCheck);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                checkExist = false;

            ps.close();
            conn.close();
            return checkExist;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    
    public static void createUserAccount(String name, char[] pass, String role, String firstName, String lastName, String phoneNum) throws SQLException {
        String password = new String(pass);
        int ID = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = GetConnection.getConnection();

            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO accounts (user_Name, pass_word, role) VALUES (?,?,?)");

            PreparedStatement ps = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setString(3, role);
            ps.executeUpdate();

            ResultSet pK = ps.getGeneratedKeys();
            if (pK.next())
                ID = pK.getInt(1);
            pushTeacherOrStudentInfomation(ID, role, firstName, lastName, phoneNum);

            JOptionPane.showMessageDialog(null, "Successfully", "Warning!", JOptionPane.WARNING_MESSAGE);
            ps.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void pushTeacherOrStudentInfomation(int userID, String role, String firstName, String lastName, String phoneNum) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = GetConnection.getConnection();

            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO " + role + " (user_id, firstName, lastName, phoneNumber) VALUES (?,?,?,?)");

            PreparedStatement ps = connection.prepareStatement(query.toString());

            ps.setInt(1, userID);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, phoneNum);
            ps.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    

    public static void getTeacherOrStudentID(String userID, String role) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String sql_student = "SELECT id_student FROM test.Student WHERE user_id = ?;";
        String sql_teacher = "SELECT id_teacher FROM test.Teacher WHERE user_id = ?;";
        String sql = null;

        if (role.equals("Student"))
            sql = sql_student;
        else if (role.equals("Teacher"))
            sql = sql_teacher;
        else
            JOptionPane.showMessageDialog(null, "Your selection is empty", "Warning!", JOptionPane.WARNING_MESSAGE);

        try (Connection conn = GetConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (role.equals("Student"))
                    Student_UI.STUDENT_ID = rs.getString("id_student");
                else
                    Teacher_UI.teacherID = rs.getString("id_teacher");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
