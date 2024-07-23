package universityCore;

public class Users {
    private int id;
    private String username;
    private String password;
    private String email;
    private String u_name;
    private boolean admin;

    public Users(){} // empty constructor to make generic objects

    public Users(int id, String username, String password, String email, String u_name, boolean admin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.u_name = u_name;
        this.admin = admin;
    }

    public String toString() {
        return "\n[id=" + getId() +
            ", username=" + getUsername() +
            ", email=" + getEmail() +
            ", u_name=" + getU_name() +
            ", password=" + getPassword() +
            ", admin=" + isAdmin() + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
