package universityGUI.professorsGUI;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import universityCore.Courses;
import universityCore.Professors;
import universityDAO.CoursesDAO;

public class ProfessorsTableModel extends AbstractTableModel {
    private final static int PR_MATR_COL = 0;
    private final static int PR_NAME_COL = 1;
    private final static int PR_ADDRESS_COL = 2;
    private final static int PR_PHONE_COL = 3;
    private final static int PR_SSN_COL = 4;
    private final static int SALARY_COL = 5;
    private final static int COURSE_COL = 6;
    public final static int OBJECT_COL = -1; // will be used to return the default object

    private String[] columnNames = { "ID", "Name", "Address", "Phone", "SSN", "Salary", "Course"};
    private List<Professors> professors;

    public ProfessorsTableModel(List<Professors> theProfessors) {
        professors = theProfessors;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return professors.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Professors tempProfessors = professors.get(row);
        int i = 0;

        switch (col) {
            case PR_MATR_COL:
                return tempProfessors.getMatr();
            case PR_NAME_COL:
                return tempProfessors.getName();
            case PR_ADDRESS_COL:
                return tempProfessors.getAddress();
            case PR_PHONE_COL:
                return tempProfessors.getPhone();
            case PR_SSN_COL:
                return tempProfessors.getSsn();
            case SALARY_COL:
                return tempProfessors.getSalary();
            case COURSE_COL:
                try {
                    int idCourse = tempProfessors.getCourse().getIdCourse();
                    CoursesDAO coursesDAO = new CoursesDAO();
                    List<Courses> courses = coursesDAO.searchCoursesId(idCourse);
                    Courses theCourse = courses.get(i);
                    String courseName = theCourse.getC_name();
                    return courseName;
                } catch (Exception exc) {
                    exc.printStackTrace();
                    return tempProfessors.getMatr();
                }
            default:
                return tempProfessors;
        }
    }
}
