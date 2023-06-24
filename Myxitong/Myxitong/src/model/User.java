package model;
public class User {
    private int id; // 编号
    private String userName; // 用户名
    private String password; // 密码
    private String email;// 邮箱
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }
    public User(String userName,String password) {
        super();
        this.userName=userName;
        this.password=password;
    }
    public User(int id,String userName,String password){
        super();
        this.id=id;
        this.userName=userName;
        this.password=password;
    }
    public User(int id, String userName, String password, String email) {
        super();
        this.id =id;
        this.userName = userName;
        this.password = password;
        this.email=email;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
}
