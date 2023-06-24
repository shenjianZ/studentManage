package view;
import dao.StudentDao;
import model.Student;
import util.DbUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
class StudentWindow extends JFrame {
    private JTable studentTable;


    public StudentWindow() {
        this.setTitle("Student Window");
        this.setSize(700, 500);
       setResizable(false);
        // 将窗口居中显示
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) ((screenSize.getWidth() - getWidth()) / 2);
        int centerY = (int) ((screenSize.getHeight() - getHeight()) / 2);
        setLocation(centerX, centerY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu scoreMenu = new JMenu("Score");
        JMenuItem scoreaddItem = new JMenuItem("Add");
        scoreaddItem.setIcon(new ImageIcon(StudentWindow.class.getResource("/images/add.png")));
        JMenuItem scoredelete_modifyItem = new JMenuItem("Delete_Modify");
        scoredelete_modifyItem.setIcon(new ImageIcon(StudentWindow.class.getResource("/images/cancel.jpg")));
        scoreMenu.add(scoreaddItem);
        scoreMenu.add(scoredelete_modifyItem);
        scoreMenu.setPreferredSize(new Dimension(80, scoreMenu.getPreferredSize().height));
        scoreaddItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ScoreListAddWindow().setVisible(true);
            }
        });
        scoredelete_modifyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ScoreListModifyDeleteWindow().setVisible(true);
            }
        });
        //fileMenu.addSeparator(); // 添加分隔线
        JMenu studentMenu = new JMenu("Student Management");
        JMenuItem addItem = new JMenuItem("Add");
        addItem.setIcon(new ImageIcon(StudentWindow.class.getResource("/images/add.png")));
        JMenuItem delete_modifyItem = new JMenuItem("Delete_Modify");
        delete_modifyItem.setIcon(new ImageIcon(StudentWindow.class.getResource("/images/cancel.jpg")));
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setIcon(new ImageIcon(StudentWindow.class.getResource("/images/reset.png")));
        studentMenu.add(addItem);
        studentMenu.add(delete_modifyItem);
        studentMenu.add(exitItem);
        addItem.setPreferredSize(new Dimension(70, scoreMenu.getPreferredSize().height));
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentAddWindow().setVisible(true);
            }
        });
        delete_modifyItem.setPreferredSize(new Dimension(100, scoreMenu.getPreferredSize().height));
        exitItem.setPreferredSize(new Dimension(70, scoreMenu.getPreferredSize().height));
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentManagementWindow().setVisible(true);
                StudentWindow.this.setVisible(false);
            }
        });
        delete_modifyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentModifyDeleteWindow().setVisible(true);
            }
        });
        menuBar.add(scoreMenu);
        menuBar.add(studentMenu);
        setJMenuBar(menuBar);
        try {
            Connection con = DbUtil.getCon();
            StudentDao studentDao = new StudentDao();
            List<Student> students = studentDao.getAllStudents(con);
            String[] columnNames = {"序号", "姓名", "学号", "性别", "院系", "班级序号", "家庭地址"};
            Object[][] data = new Object[students.size()][7];
            for (int i = 0; i < students.size(); i++) {
                data[i][0] = students.get(i).getId();
                data[i][1] = students.get(i).getName();
                data[i][2] = students.get(i).getSn();
                data[i][3] = students.get(i).getSex();
                data[i][4] = students.get(i).getDept();
                data[i][5] = students.get(i).getClassId();
                data[i][6] = students.get(i).getAddress();
            }
            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
            studentTable = new JTable(tableModel);
            studentTable.setRowHeight(30);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            studentTable.setDefaultRenderer(Object.class, centerRenderer);
            studentTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(this, "Failed to retrieve students from the database.");
            this.dispose();
            new StudentManagementWindow().setVisible(true);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JScrollPane scrollPane = new JScrollPane(this.studentTable);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        JPanel southPanel = new JPanel();
        panel.add(southPanel, BorderLayout.SOUTH);
        this.getContentPane().add(panel);
    }

}
