package view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import dao.StudentDao;
import dao.SchoolClassDao;
import model.Student;
import model.SchoolClass;
import util.DbUtil;
import util.StringUtil;

public class StudentAddWindow extends JFrame {
    private JTextField studentTxt;
    private JTextField snTxt;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JTextField deptTxt;
    private JComboBox schoolClassJcb;
    private JTextArea addressTxt;
    private JRadioButton manJrb;
    private JRadioButton femaleJrb;

    private DbUtil dbUtil=new DbUtil();
    private SchoolClassDao schoolClassDao=new SchoolClassDao();
    private StudentDao studentDao=new StudentDao();



    /**
     * Create the frame.
     */
    public StudentAddWindow() {
        //setClosable(true);
        //setIconifiable(true);
        setTitle("添加学生信息");
        setBounds(100, 100, 550, 467);
        setResizable(false);
        // 将窗口居中显示
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) ((screenSize.getWidth() - getWidth()) / 2);
        int centerY = (int) ((screenSize.getHeight() - getHeight()) / 2);
        setLocation(centerX, centerY);
        JLabel label = new JLabel("学号：");
        studentTxt = new JTextField();
        JLabel label_1 = new JLabel("姓名：");
        snTxt = new JTextField();
        JLabel label_2 = new JLabel("性别：");
        manJrb = new JRadioButton("男");
        femaleJrb = new JRadioButton("女");
        JLabel label_3 = new JLabel("院系：");
        deptTxt = new JTextField();
        JLabel label_4 = new JLabel("地址：");
        addressTxt = new JTextArea();
        JLabel label_5 = new JLabel("班级：");
        schoolClassJcb = new JComboBox();
        JButton button = new JButton("确定");
        JButton button_1 = new JButton("重置");

        // 设置组件属性
        studentTxt.setColumns(10);
        snTxt.setColumns(10);
        deptTxt.setColumns(10);
        addressTxt.setColumns(10);
        addressTxt.setRows(5);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stuAddActionPerformed(e);
            }
        });
        button.setIcon(new ImageIcon(StudentAddWindow.class.getResource("/images/add.png")));

        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetValueActionPerformed(e);
            }
        });
        button_1.setIcon(new ImageIcon(StudentAddWindow.class.getResource("/images/reset.png")));
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(42)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(button)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(button_1)
                                                .addGap(232))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(label_4)
                                                        .addComponent(label_2)
                                                        .addComponent(label))
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(studentTxt, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(manJrb)
                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                .addComponent(femaleJrb))
                                                        .addComponent(schoolClassJcb, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(35)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(label_1)
                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                .addComponent(snTxt, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(label_3)
                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                .addComponent(deptTxt))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(label_5)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(addressTxt)))
                                .addContainerGap(44, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(42)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label)
                                        .addComponent(studentTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_1)
                                        .addComponent(snTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(29)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label_2)
                                        .addComponent(manJrb)
                                        .addComponent(femaleJrb)
                                        .addComponent(label_3)
                                        .addComponent(deptTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(33)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label_5)
                                        .addComponent(schoolClassJcb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(30)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(label_4)
                                        .addComponent(addressTxt, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(button)
                                        .addComponent(button_1))
                                .addGap(42))
        );

        // 设置文本域边框
        addressTxt.setBorder(new LineBorder(new java.awt.Color(127,157,185), 1, false));
        Connection con=null;
        try{
            con=dbUtil.getCon();
            ResultSet rs=schoolClassDao.list(con, new SchoolClass());
            while(rs.next()){
                SchoolClass studentClass=new SchoolClass();
                studentClass.setId(rs.getInt("id"));
                studentClass.setClassName(rs.getString("className"));
                this.schoolClassJcb.addItem(studentClass);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        }
    }

    /**
     * 重置事件处理
     * @param e
     */
    private void resetValueActionPerformed(ActionEvent e) {
        this.resetValue();
    }

    /**
     * 学生添加事件处理
     * @param
     */
    private void stuAddActionPerformed(ActionEvent evt) {
        String userName=this.studentTxt.getText();
        String sn=this.snTxt.getText();
        String dept=this.deptTxt.getText();
        String address=this.addressTxt.getText();

        if(StringUtil.isEmpty(userName)){
            JOptionPane.showMessageDialog(null, "学生姓名不能为空！");
            return;
        }

        if(StringUtil.isEmpty(sn)){
            JOptionPane.showMessageDialog(null, "学生学号不能为空！");
            return;
        }

        if(StringUtil.isEmpty(dept)){
            JOptionPane.showMessageDialog(null, "学生学院不能为空！");
            return;
        }

        String sex="";
        if(manJrb.isSelected()){
            sex="男";
        }else if(femaleJrb.isSelected()){
            sex="女";
        }

        SchoolClass stuClass=(SchoolClass) schoolClassJcb.getSelectedItem();
        String className=stuClass.getClassName();
        int classId=stuClass.getId();
        Student stu=new Student(sn,userName, sex, dept ,classId,className, address);

        Connection con=null;
        try{
            con=dbUtil.getCon();
            int addNum=studentDao.add(con, stu);
            if(addNum==1){
                JOptionPane.showMessageDialog(null, "学生添加成功！");
                resetValue();
            }else{
                JOptionPane.showMessageDialog(null, "学生添加失败！");
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "学生添加失败！");
        }finally{
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 重置表单
     */
    private void resetValue(){
        this.studentTxt.setText("");
        this.snTxt.setText("");
        this.deptTxt.setText("");
        this.manJrb.setSelected(true);
        this.addressTxt.setText("");
        if(this.schoolClassJcb.getItemCount()>0){
            this.schoolClassJcb.setSelectedIndex(0);
        }
    }
}
