package general;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.FlatLightLaf;

import accountFactory.AccountFactory;
import accountFactory.StudentInterface;
import studentAccount.Student_UI;
import teacherAccount.Teacher_UI;
import updateRes.LoadCreatedClass;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;


public class SignIn_Window extends JFrame {

    private static final long serialVersionUID = 1L;
    public static boolean checkUsername = false;
    public static String id;
    
    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> comboBox;
    private JCheckBox showPass;

    private Teacher_UI teacher_UI;
    private LoadCreatedClass getCreatedClass;
//    private Student_UI student_UI;
    private MainMenu main;
    private Image backgroundImage; 

    public SignIn_Window(MainMenu mainmenu) {
    	setBackground(new Color(255, 255, 255));
    	setType(Type.UTILITY);
    	setTitle("EduExam");
    	setForeground(new Color(0, 0, 0));
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        URL imageURL = MainMenu.class.getResource("/bg.jpg");
        if(imageURL != null) {
            backgroundImage = new ImageIcon(imageURL).getImage();
        } else {
            System.out.println("imageURL is null");
        }

        this.main = mainmenu;
        getCreatedClass = new LoadCreatedClass();
        teacher_UI = new Teacher_UI(this,mainmenu);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 880, 550);
        contentPane = new JPanel() {
            private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        comboBox = new JComboBox<>();
        comboBox.setForeground(new Color(0, 0, 0));
        comboBox.setFont(new Font("Dubai Medium", Font.PLAIN, 14));
        comboBox.setBackground(new Color(255, 153, 255));
        comboBox.setModel(new DefaultComboBoxModel<>(new String[]{"", "Teacher", "Student"}));
        comboBox.setBounds(359, 120, 172, 22);
        contentPane.add(comboBox);

        JLabel label = new JLabel("You are a ");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial Rounded MT Bold", Font.BOLD | Font.ITALIC, 30));
        label.setBounds(359, 77, 172, 33);
        contentPane.add(label);

        JLabel userLabel = new JLabel("User name");
        userLabel.setForeground(new Color(0, 0, 0));
        userLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 24));
        userLabel.setBounds(306, 206, 112, 20);
        contentPane.add(userLabel);

        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(new Color(0, 0, 0));
        passLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 24));
        passLabel.setBounds(306, 269, 115, 20);
        contentPane.add(passLabel);

        usernameField = new JTextField();
        usernameField.setForeground(new Color(0, 0, 0));
        usernameField.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
        usernameField.setBackground(new Color(255, 153, 255));
        usernameField.setBounds(431, 206, 157, 20);
        contentPane.add(usernameField);
        usernameField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setForeground(new Color(0, 0, 0));
        passwordField.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
        passwordField.setBackground(new Color(255, 153, 255));
        passwordField.setBounds(431, 269, 157, 20);
        contentPane.add(passwordField);

        showPass = new JCheckBox("Show password");
        showPass.setBackground(new Color(255, 255, 255));
        showPass.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
        showPass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showPass.isSelected())
                    passwordField.setEchoChar((char) 0);
                else
                    passwordField.setEchoChar('*');
            }
        });
        showPass.setBounds(431, 295, 120, 23);
        contentPane.add(showPass);

        JButton summit = new JButton("Sign In");
        summit.setForeground(new Color(0, 0, 0));
        summit.setBackground(new Color(255, 255, 51));
        summit.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
        summit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                summitAction();
            }
        });
        summit.setBounds(312, 375, 89, 33);
        contentPane.add(summit);

        JButton back = new JButton("Back");
        back.setBackground(new Color(255, 255, 51));
        back.setForeground(new Color(0, 0, 0));
        back.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                passwordField.setText("");
                comboBox.setSelectedIndex(0);
                showPass.setSelected(false);
                setVisible(false);
                mainmenu.setVisible(true);
            }
        });
        back.setBounds(499, 375, 89, 33);
        contentPane.add(back);
        setVisible(true);
    }


    private void summitAction() {
        try {
            String selection = (String) comboBox.getSelectedItem();
            if (Authentication.checkLogin(passwordField.getPassword(),usernameField.getText(), (String) comboBox.getSelectedItem())) {
                usernameField.setText("");
                passwordField.setText("");
                comboBox.setSelectedIndex(0);
                showPass.setSelected(false);
                if (selection.equals("Student"))
                    initializeStudentAccount();
                else
                    loadClassToTeacherAccount();

                JOptionPane.showMessageDialog(null, "Login successfully !!", "Warning!", JOptionPane.WARNING_MESSAGE);
            } else
                System.out.println("Invalid account");

        } catch (HeadlessException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }
    

    private void loadClassToTeacherAccount() throws ClassNotFoundException {
        if (!id.equals(null)) {
            LoadCreatedClass.ID = id;
            getCreatedClass.getCreatedClass();
            Teacher_UI.teacherOwnClass = getCreatedClass.getInfoClass();
            Teacher_UI.componentClass = getCreatedClass.getNum();
            openTeacherUI();
        }
    }

    private void openTeacherUI() {
    	this.dispose();
        teacher_UI.initializeClass();
        Teacher_UI.frame.setVisible(true);
        Teacher_UI.frame.setLocationRelativeTo(null);
    }

    private void initializeStudentAccount() {
    	StudentInterface student = AccountFactory.getStudentAccount("Student", main);
    	student.nothing();
//        student_UI = new Student_UI(main);
//        student_UI.nothing();
        Student_UI.Studentframe.setVisible(true);
        this.dispose();
    }
    
//
//    private boolean checkLogin(char[] password, String selection) throws HeadlessException, ClassNotFoundException {
//        if (checkExistedAccount(usernameField.getText(), password, selection)) {
//            checkUsername = true;
//            return true;
//        }
//        return false;
//    }
//
//    private boolean checkExistedAccount(String nameToCheck, char[] pass, String selection) throws ClassNotFoundException {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        String password = new String(pass);
//        String sql_student = "SELECT user_ID, pass_word FROM test.accounts WHERE role = 'Student' and user_Name = ?;";
//        String sql_teacher = "SELECT user_ID, pass_word FROM test.accounts WHERE role = 'Teacher' and user_Name = ?;";
//        String sql = null;
//
//        id = null;
//
//        if (selection.equals("Student"))
//            sql = sql_student;
//        else if (selection.equals("Teacher"))
//            sql = sql_teacher;
//        else
//            JOptionPane.showMessageDialog(null, "Your selection is empty", "Warning!", JOptionPane.WARNING_MESSAGE);
//
//        boolean checkAuth = false;
//        try (Connection conn = MyConnection.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setString(1, nameToCheck);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                String storedPassword = rs.getString("pass_word");
//
//                if (storedPassword.equals(password)) {
//                    checkAuth = true;
//                    id = rs.getString("user_ID");
//                    getTeacherOrStudentID(id, selection);
//                }
//            }
//
//            if (!checkAuth)
//                JOptionPane.showMessageDialog(null, "Wrong username or password", "Warning!", JOptionPane.WARNING_MESSAGE);
//
//            ps.close();
//            conn.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return checkAuth;
//    }
//
//    private void getTeacherOrStudentID(String userID, String role) throws ClassNotFoundException {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        String sql_student = "SELECT id_student FROM test.Student WHERE user_id = ?;";
//        String sql_teacher = "SELECT id_teacher FROM test.Teacher WHERE user_id = ?;";
//        String sql = null;
//
//        if (role.equals("Student"))
//            sql = sql_student;
//        else if (role.equals("Teacher"))
//            sql = sql_teacher;
//        else
//            JOptionPane.showMessageDialog(null, "Your selection is empty", "Warning!", JOptionPane.WARNING_MESSAGE);
//
//        try (Connection conn = MyConnection.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setString(1, userID);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                if (role.equals("Student"))
//                    Student_UI.STUDENT_ID = rs.getString("id_student");
//                else
//                    Teacher_UI.teacherID = rs.getString("id_teacher");
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}