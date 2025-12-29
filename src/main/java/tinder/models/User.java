package tinder.models;

public class User {
    private int id;
    private String username;
    private String login;
    private String password;
    private String avatar_url;

    public User() {}

    public User(int id, String username, String login, String password, String avatar_url) {
        this.id = id;
        this.username = username;
        this.login = login;
        this.password = password;
        this.avatar_url = avatar_url;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
