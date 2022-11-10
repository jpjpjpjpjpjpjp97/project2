package Presentation.Model;

public class AbstractUser {
    private int id;
    private String username;

    public AbstractUser() {
        this.id = 0;
        this.username = "";
    }

    public AbstractUser(int id, String username) {
        this.id = id;
        this.username = username;
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

    @Override
    public String toString() {
        return "AbstractUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
