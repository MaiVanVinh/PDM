package accountFactory;

public interface TeacherInterface {
		public void addClass();
		public void initializeClass();
		public boolean generateUniqueCode() throws ClassNotFoundException;
	    public boolean checkDuplicateClass(String n);
	    public int autoCalculateAcademicYear();
}
