package accountFactory;

import general.MainMenu;
import general.SignIn_Window;
import studentAccount.Student_UI;
import teacherAccount.Teacher_UI;

public class AccountFactory {
	
    public static TeacherInterface getTeacherAccount(String type,SignIn_Window signin, MainMenu mainmenu) {
        if("Teacher".equalsIgnoreCase(type))
           return new Teacher_UI(signin, mainmenu);
		return null;
    }
    
    public static StudentInterface getStudentAccount(String type, MainMenu mainmenu) {
        if("Student".equalsIgnoreCase(type))
           return new Student_UI(mainmenu);
		return null;
    }
}
