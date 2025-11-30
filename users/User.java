package users;

public abstract class User {
    protected String id;
    public User(String id) { this.id = id; }
    public abstract void login();
}
