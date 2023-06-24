package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Student;
import util.StringUtil;

/**
 * 学生Dao类
 * @author Administrator
 *
 */
public class StudentDao {

    /**
     * 学生
     *
     * @param con
     * @param student
     * @return
     * @throws Exception
     */
    public int add(Connection con, Student student) throws Exception {
        String sql = "insert into student values(null,?,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, student.getName());
        pstmt.setString(2, student.getSn());
        pstmt.setString(3, student.getSex());
        pstmt.setString(4, student.getDept());
        pstmt.setInt(5, student.getClassId());
        pstmt.setString(6, student.getAddress());
        return pstmt.executeUpdate();
    }

    public static List<Student> getAllStudents(Connection con) throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            try {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setSn(rs.getString("sn"));
                student.setSex(rs.getString("sex"));
                student.setDept(rs.getString("dept"));
                student.setClassId(rs.getInt("classId"));
                student.setAddress(rs.getString("address"));
                students.add(student);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return students;
    }

    /**
     * 学生信息查询
     *
     * @param con
     * @param student
     * @return
     * @throws Exception
     */
    public ResultSet list(Connection con, Student student) throws Exception {
        StringBuilder sb = new StringBuilder("SELECT * FROM student b WHERE true");
        List<Object> params = new ArrayList<>();

        if (StringUtil.isNotEmpty(student.getName())) {
            sb.append(" AND b.name LIKE ?");
            params.add("%" + student.getName() + "%");
        }
        if (StringUtil.isNotEmpty(student.getSn())) {
            sb.append(" AND b.sn LIKE ?");
            params.add("%" + student.getSn() + "%");
        }
        if (student.getClassId() != null && student.getClassId() != -1) {
            sb.append(" AND b.classId = ?");
            params.add(student.getClassId());
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString());
        for (int i = 0; i < params.size(); i++) {
            pstmt.setObject(i + 1, params.get(i));
        }
        return pstmt.executeQuery();
    }

    /**
     * 学生信息删除
     *
     * @param con
     * @param id
     * @return
     * @throws Exception
     */
    public static int delete(Connection con, int id) throws Exception {
        String sql = "delete from student where id=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, id);
        return pstmt.executeUpdate();
    }

    /**
     * 学生信息修改
     *
     * @param con
     * @param student
     * @return
     * @throws Exception
     */
    public static boolean update(Connection con, Student student) throws Exception {
        String sql = "update student set name=?,sn=?,sex=?,dept=?,address=?,classId=? where id=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, student.getName());
        pstmt.setString(2, student.getSn());
        pstmt.setString(3, student.getSex());
        pstmt.setString(4, student.getDept());
        pstmt.setString(5, student.getAddress());
        pstmt.setInt(6, student.getClassId());
        pstmt.setInt(7, student.getId());
        int result= pstmt.executeUpdate();
        return result > 0;
    }

}
