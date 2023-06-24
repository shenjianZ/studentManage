package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.SchoolClass;
import util.StringUtil;


/**
 * 班级Dao类
 * @author Administrator
 *
 */
public class SchoolClassDao {

    /**
     * 班级添加
     *
     * @param con
     * @param schoolClass
     * @return int
     * @throws Exception
     */
    public int add(Connection con, SchoolClass schoolClass) throws Exception {
        String sql = "insert into schoolclass values(null,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, schoolClass.getClassName());
        //System.out.println(schoolClass.getClassName());
        pstmt.setString(2, schoolClass.getCalssDesc());
        return pstmt.executeUpdate();
    }

    /**
     * 查询班级集合
     *
     * @param con
     * @param schoolClass
     * @return ResultSet
     */
    public ResultSet list(Connection con, SchoolClass schoolClass) throws Exception {
        StringBuilder sb = new StringBuilder("SELECT * FROM schoolclass");
        List<Object> params = new ArrayList<>();
        if (StringUtil.isNotEmpty(schoolClass.getClassName())) {
            sb.append(" WHERE className LIKE ?");
            params.add("%" + schoolClass.getClassName() + "%");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString());
        // 设置每个占位符的参数值
        for (int i = 0; i < params.size(); i++) {
            pstmt.setObject(i + 1, params.get(i));
        }
        return pstmt.executeQuery();
    }

}