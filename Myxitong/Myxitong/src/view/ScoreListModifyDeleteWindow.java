package view;

import dao.ScoreDao;
import model.Score;
import util.DbUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ScoreListModifyDeleteWindow extends JFrame {
    private JTextField idField, studentIdField, nameField, chineseField, mathField, englishField, totalField;
    private JTable table;
    private ScoreDao scoreDao;
    public ScoreListModifyDeleteWindow() {
        setSize(600, 300);
        table=new JTable();
        scoreDao = new ScoreDao();
        // 将窗口居中显示
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) ((screenSize.getWidth() - getWidth()) / 2);
        int centerY = (int) ((screenSize.getHeight() - getHeight()) / 2);
        setLocation(centerX, centerY);
        setResizable(false);
        // 添加顶部面板，并在面板中添加文本框和按钮
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel idLabel = new JLabel("ID：");
        idField = new JTextField(5);
        JLabel studentIdLabel = new JLabel("学号：");
        studentIdField = new JTextField(5);
        JLabel nameLabel = new JLabel("姓名：");
        nameField = new JTextField(5);
        JLabel chineseLabel = new JLabel("语文：");
        chineseField = new JTextField(5);
        JLabel mathLabel = new JLabel("数学：");
        mathField = new JTextField(5);
        JLabel englishLabel = new JLabel("英语：");
        englishField = new JTextField(5);
        JLabel totalLabel = new JLabel("总分：");
        totalField = new JTextField(5);
        JButton updateButton = new JButton("修改");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int id = Integer.parseInt(idField.getText());
                    String studentId = studentIdField.getText();
                    String name = nameField.getText();
                    float chinese = Float.parseFloat(chineseField.getText());
                    float math = Float.parseFloat(mathField.getText());
                    float english = Float.parseFloat(englishField.getText());
                    float total = Float.parseFloat(totalField.getText());
                    Connection con= null;
                    try {
                        con = DbUtil.getCon();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    Score score=new Score(id,studentId,name,chinese,math,english,total);
                    boolean result = scoreDao.updateScore(con,score);
                    if (result) {
                        JOptionPane.showMessageDialog(null, "修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        refreshTableData();
                    } else {
                        JOptionPane.showMessageDialog(null, "修改失败！", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        JButton deleteButton=new JButton("删除");

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int id = Integer.parseInt(idField.getText());
                    DbUtil dbUtil = new DbUtil();
                    Connection con = null;
                    try {
                        con = dbUtil.getCon();
                        int result = scoreDao.delete(con, id);
                        if (result!=-1) {
                            JOptionPane.showMessageDialog(null, "删除成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            refreshTableData();
                        } else {
                            JOptionPane.showMessageDialog(null, "删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });

        topPanel.add(idLabel);
        topPanel.add(idField);
        topPanel.add(studentIdLabel);
        topPanel.add(studentIdField);
        topPanel.add(nameLabel);
        topPanel.add(nameField);
        topPanel.add(chineseLabel);
        topPanel.add(chineseField);
        topPanel.add(mathLabel);
        topPanel.add(mathField);
        topPanel.add(englishLabel);
        topPanel.add(englishField);
        topPanel.add(totalLabel);
        topPanel.add(totalField);
        topPanel.add(updateButton);
        topPanel.add(deleteButton);

        add(topPanel, BorderLayout.NORTH);

        // 创建表格并添加到窗口中
        JTable table = createScoreTable();
        JScrollPane jScrollPane = new JScrollPane(table);
        add(jScrollPane, BorderLayout.CENTER);

        setTitle("学生成绩列表");
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * 刷新表格数据
     */
    private void refreshTableData() {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0); // 清空表格
        Connection con= null;
        try {
            con = DbUtil.getCon();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<Score> scoreList = scoreDao.getAllScores(con);
        for (Score score : scoreList) {
            Object[] rowData = {score.getId(), score.getStudentId(), score.getStudentName(),
                    score.getChineseScore(), score.getMathScore(), score.getEnglishScore(), score.getTotal()};
            tableModel.addRow(rowData);
        }
        tableModel.fireTableDataChanged(); // 通知表格模型数据发生变化
    }
    /**
     * 获取所有成绩信息
     *
     * @return
     */


    /**
     * 创建成绩表格，并设置选择模式为单选
     *
     * @return
     */
    private JTable createScoreTable() {
        Object[] columnNames = {"id", "ID", "姓名", "语文", "数学", "英语", "总分"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        table.setRowHeight(30); // 设置表格行高
        ScoreDao scoreDao=new ScoreDao();
        List<Score> scoreList;
        try (Connection con = DbUtil.getCon()) {
            scoreList = scoreDao.getAllScores(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (Score score : scoreList) {
            Object[] rowData = {score.getId(), score.getStudentId(), score.getStudentName(),
                    score.getChineseScore(), score.getMathScore(), score.getEnglishScore(), score.getTotal()};
            tableModel.addRow(rowData);
        }
        table.setModel(tableModel); // 将成绩表格绑定到成员变量table上
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 单选模式
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) { // 根据选中的行来更新文本框内容
                idField.setText(String.valueOf(table.getValueAt(row, 0)));
                studentIdField.setText((String) table.getValueAt(row, 1));
                nameField.setText((String) table.getValueAt(row, 2));
                chineseField.setText(String.valueOf(table.getValueAt(row, 3)));
                mathField.setText(String.valueOf(table.getValueAt(row, 4)));
                englishField.setText(String.valueOf(table.getValueAt(row, 5)));
                totalField.setText(String.valueOf(table.getValueAt(row, 6)));
            }
        });
        return table;
    }
}
