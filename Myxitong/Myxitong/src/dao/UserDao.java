package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.User;

/**
 * 用户Dao类
 *
 */
public class UserDao {
    /**
     * 学生
     *
     * @param con
     * @param user
     * @return
     * @throws Exception
     */
    public int add(Connection con, User user) throws Exception {
        String sql = "insert into user values(null,?,?,null)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, user.getUserName());
        pstmt.setString(2, user.getPassword());
        return pstmt.executeUpdate();
    }

    /**
     * 登录验证
     * @param con
     * @param user
     * @return
     * @throws Exception
     */
    public static User login(Connection con, User user)throws Exception{
        User resultUser=null;
        String sql = "SELECT * FROM user WHERE userName = ? AND password  = ?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, user.getUserName());
        pstmt.setString(2, user.getPassword());
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()){
            resultUser=new User();
            resultUser.setId(rs.getInt("id"));
            resultUser.setUserName(rs.getString("userName"));
            resultUser.setPassword(rs.getString("password"));
        }
        return resultUser;
    }
    public List<User> getAllUsers(Connection con) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql="SELECT * FROM user;";
        PreparedStatement pstmt=con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String userName = rs.getString("username");
            String password = rs.getString("password");
            String email = rs.getString("email");
            User user = new User(id,userName, password, email);
            users.add(user);
        }
        return users;
    }
    public void update(Connection con, User user) throws Exception {
        String sql = "update user set userName=?,password=?,email=? where id=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, user.getUserName());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getEmail());
        pstmt.setInt(4, user.getId());
        pstmt.executeUpdate();
    }
    public void delete(Connection con, int id) throws Exception {
        String sql = "DELETE FRom user where id=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }
}

