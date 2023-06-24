package dao;

import model.Score;
import util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreDao {
    DbUtil dbUtil = new DbUtil();

    /**
     * 更新成绩信息
     *
     * @param id
     * @param studentId
     * @param name
     * @param chinese
     * @param math
     * @param english
     * @param total
     * @return
     */
    public boolean updateScore(Connection con, Score score) {
        try {
            String sql = "UPDATE score SET student_id=?, student_name=?, chinese=?, math=?, english=?, total=? WHERE id=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, score.getStudentId());
                ps.setString(2, score.getStudentName());
                ps.setFloat(3, score.getChineseScore());
                ps.setFloat(4, score.getMathScore());
                ps.setFloat(5, score.getEnglishScore());
                ps.setFloat(6, score.getTotal());
                ps.setInt(7, score.getId());
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public int add(Connection con, Score score) throws Exception {
        String sql = "insert into score values(null,?,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, score.getStudentId());
        pstmt.setString(2, score.getStudentName());
        pstmt.setFloat(3, score.getChineseScore());
        pstmt.setFloat(4, score.getMathScore());
        pstmt.setFloat(5, score.getEnglishScore());
        pstmt.setFloat(6, score.getTotal());
        return pstmt.executeUpdate();
    }

    public List<Score> getAllScores(Connection con) {
        List<Score> scoreList = new ArrayList<>();
        try {
            String sql = "SELECT id, student_name, student_id, chinese, math, english, total FROM score";
            try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id1 = rs.getInt("id");
                    String id2 = rs.getString("student_id");
                    String name = rs.getString("student_name");
                    float chinese = rs.getFloat("chinese");
                    float math = rs.getFloat("math");
                    float english = rs.getFloat("english");
                    float total = rs.getFloat("total");
                    Score score = new Score(id1, id2, name, chinese, math, english, total);
                    scoreList.add(score);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return scoreList;
    }

    public int delete(Connection con, int id) throws Exception {
        String sql = "delete from score where id=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, id);
        return pstmt.executeUpdate();
    }
}