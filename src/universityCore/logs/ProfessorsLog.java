package universityCore.logs;

import java.sql.Timestamp;

import universityCore.Professors;
import universityCore.Users;

public class ProfessorsLog {
    private int idLog;
    private Users users;
    private Professors professors;
    private String action;
    private Timestamp action_date;

    public ProfessorsLog(int idLog, Users users, Professors professors, String action, Timestamp action_date) {
        this.idLog = idLog;
        this.users = users;
        this.professors = professors;
        this.action = action;
        this.action_date = action_date;
    }

    public String toString() {
        return "[log id=" + getIdLog() +
            ", user=" + getUsers().getId() +
            ", studentId=" + getProfessors().getMatr() +
            ", action='" + getAction() + "'" +
            ", date=" + getAction_date() + "]";
    }

    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Professors getProfessors() {
        return professors;
    }

    public void setProfessors(Professors professors) {
        this.professors = professors;
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
