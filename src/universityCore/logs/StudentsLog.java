package universityCore.logs;

import java.sql.Timestamp;
import universityCore.Students;
import universityCore.Users;

public class StudentsLog {
    private int idLog;
    private Users users;
    private Students students;
    private String action;
    private Timestamp action_date;

    public StudentsLog(int idLog, String action, Timestamp action_date, Students students, Users users) {
        this.idLog = idLog;
        this.action = action;
        this.action_date = action_date;
        this.students = students;
        this.users = users;
    }

    public String toString() {
        return "[log id=" + getIdLog() +
            ", user=" + getUsers().getId() +
            ", studentId=" + getStudents().getMatr() +
            ", action='" + getAction() + "'" +
            ", date=" + getAction_date() + "]";
    }

    public Students getStudents() {
        return students;
    }

    public void setStudents(Students students) {
        this.students = students;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Timestamp getAction_date() {
        return action_date;
    }

    public void setAction_date(Timestamp action_date) {
        this.action_date = action_date;
    }
}
