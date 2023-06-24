package view;

import dao.ScoreDao;
import dao.StudentDao;
import model.Score;
import model.Student;
import util.DbUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StudentModifyDeleteWindow extends JFrame {
    private JTextField idField, nameField, snField, sexField, deptField, classIdField,addressField;
    private JTable table;
    private StudentDao studentDao;
    private DbUtil dbUtil;
    public StudentModifyDeleteWindow() {
        setSize(700, 300);
        table=new JTable();
        studentDao = new StudentDao();
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
        JLabel nameLabel = new JLabel("姓名：");
        nameField = new JTextField(5);
        JLabel snLabel = new JLabel("学号：");
        snField = new JTextField(5);
        JLabel sexLabel = new JLabel("性别：");
        sexField= new JTextField(5);
        JLabel deptLabel = new JLabel("院系：");
        deptField = new JTextField(5);
        JLabel classIdLabel = new JLabel("班级序号：");
        classIdField = new JTextField(5);
        JLabel addressLabel = new JLabel("家庭地址：");
        addressField = new JTextField(5);
        JButton updateButton = new JButton("修改");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int id = Integer.parseInt(idField.getText());
                    String name   = nameField.getText();
                    String sn = snField.getText();
                    String sex = sexField.getText();
                    String dept= deptField.getText();
                    Integer classId=Integer.parseInt(classIdField.getText());
                    String address= addressField.getText();
                    Student student=new Student(id,name,sn,sex,dept,classId,address);
                    dbUtil=new DbUtil();
                    Connection con= null;
                    try {
                        con = dbUtil.getCon();
                        boolean result = StudentDao.update(con,student);
                        if (result) {
                            JOptionPane.showMessageDialog(null, "修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            refreshTableData();
                        } else {
                            JOptionPane.showMessageDialog(null, "修改失败！", "错误", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
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
                        int result = StudentDao.delete(con, id);
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
        topPanel.add(nameLabel);
        topPanel.add(nameField);
        topPanel.add(snLabel);
        topPanel.add(snField);
        topPanel.add(sexLabel);
        topPanel.add(sexField);
        topPanel.add(deptLabel);
        topPanel.add(deptField);
        topPanel.add(classIdLabel);
        topPanel.add(classIdField);
        topPanel.add(addressLabel);
        topPanel.add(addressField);
        topPanel.add(updateButton);
        topPanel.add(deleteButton);

        add(topPanel, BorderLayout.NORTH);

        // 创建表格并添加到窗口中
        JTable table = createStudentTable();
        JScrollPane jScrollPane = new JScrollPane(table);
        add(jScrollPane, BorderLayout.CENTER);

        setTitle("学生列表");
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * 刷新表格数据
     */
    private void refreshTableData() {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0); // 清空表格
        dbUtil=new DbUtil();
        List<Student> StudentList;
        try (Connection con = dbUtil.getCon()) {
            StudentList = StudentDao.getAllStudents(con);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (Student student : StudentList) {
            Object[] rowData = {student.getId(), student.getName(), student.getSn(),
                    student.getSex(), student.getDept(), student.getClassId(), student.getAddress()};
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
    private JTable createStudentTable() {
        Object[] columnNames = {"序号", "姓名", "学号", "性别", "院系", "班级序号", "家庭住址"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        table.setRowHeight(30); // 设置行高为30像素
        StudentDao studentDao=new StudentDao();
        dbUtil=new DbUtil();
        try {
            Connection con= dbUtil.getCon();
            List<Student> studentList =StudentDao.getAllStudents(con);
            for (Student student : studentList) {
                Object[] rowData = {student.getId(), student.getName(), student.getSn(),
                        student.getSex(), student.getDept(), student.getClassId(), student.getAddress()};
                tableModel.addRow(rowData);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        table.setModel(tableModel); // 将成绩表格绑定到成员变量table上
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 单选模式
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) { // 根据选中的行来更新文本框内容
                idField.setText(String.valueOf(table.getValueAt(row, 0)));
                nameField.setText((String) table.getValueAt(row, 1));
                snField.setText((String) table.getValueAt(row, 2));
                sexField.setText(String.valueOf(table.getValueAt(row, 3)));
                deptField.setText(String.valueOf(table.getValueAt(row, 4)));
                classIdField.setText(String.valueOf(table.getValueAt(row, 5)));
                addressField.setText(String.valueOf(table.getValueAt(row, 6)));
            }
        });
        return table;
    }


}
