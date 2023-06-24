package util;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 * 数据库工具类
 * @author Administrator
 *
 */
public class DbUtil {

    private static String dbUrl="jdbc:mysql://localhost:3306/db_student?characterEncoding=utf8"; // 数据库连接地址
    private static String dbUserName="root"; // 用户名,填写你自己的数据库用户名
    private static String dbPassword="123456"; // 密码，你自己的用户名密码，这个就是我的，所以我不修改了
    private static String jdbcName="com.mysql.jdbc.Driver"; // 驱动名称
    //dbUrl表示数据库的连接地址，其中localhost表示本机，3306表示MySQL数据库的默认端口号，db_student_swing为数据库名称；
    //dbUserName和dbPassword分别为数据库的用户名和密码，你需要根据自己的数据库情况进行修改。
    /**
     * 获取数据库连接
     * @return
     * @throws Exception
     */
    public static Connection getCon()throws Exception{
        Class.forName(jdbcName);// 加载MySQL数据库驱动
        Connection con=DriverManager.getConnection(dbUrl, dbUserName, dbPassword);// 建立数据库连接，获取Connection对象 Connection
        return con;// 返回连接对象  con
    }

    /**
     * 关闭数据库连接
     * @param con
     * @throws Exception
     */
    public static void closeCon(Connection con)throws Exception{
        if(con!=null){
            con.close();
        }
    }

    public static void main(String[] args) {
        DbUtil dbUtil=new DbUtil();
        try {
            dbUtil.getCon();
            System.out.println("数据库连接成功！");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }
    }
}

