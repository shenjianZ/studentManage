package view;
import model.User;
import util.DbUtil;
import util.StringUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import dao.UserDao;
public class RegisterWindow extends JFrame {
    private WelcomeWindow welcomeWindow;
    private UserDao UserDao=new UserDao();
    JTextField usernameField;
    JPasswordField passwordField;
    JLabel confirmPasswordLabel;


    public RegisterWindow() {
        setTitle("注册 - 学生管理系统");
        setSize(300, 140);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false); // 设置窗口大小不可变

        // 将窗口居中显示
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) ((screenSize.getWidth() - getWidth()) / 2);
        int centerY = (int) ((screenSize.getHeight() - getHeight()) / 2);
        setLocation(centerX, centerY);

        // 创建注册表单
        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("    用户名：", JLabel.RIGHT);
        usernameLabel.setIcon(new ImageIcon(LoginWindow.class.getResource("/images/userName.png")));
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("      密  码：", JLabel.RIGHT);
        passwordLabel.setIcon(new ImageIcon(LoginWindow.class.getResource("/images/password.png")));
        passwordField = new JPasswordField();
        confirmPasswordLabel = new JLabel("确认密码：", JLabel.RIGHT);
        confirmPasswordLabel.setIcon(new ImageIcon(LoginWindow.class.getResource("/images/password.png")));
        JPasswordField confirmPasswordField = new JPasswordField();
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordField);
        add(formPanel, BorderLayout.CENTER);

        // 创建按钮
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton registerButton = new JButton("注册");
        registerButton.setIcon(new ImageIcon(LoginWindow.class.getResource("/images/signup.jpg")));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 在这里编写注册的代码
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                if(StringUtil.isEmpty(username)){
                    JOptionPane.showMessageDialog(null, "用户名不能为空！");
                    return;
                }
                if(StringUtil.isEmpty(password)||StringUtil.isEmpty(confirmPassword)){
                    JOptionPane.showMessageDialog(null, "用户密码不能为空！");
                    return;
                }
                User user=new User(username,password);
                Connection con=null;
                try{
                    con=DbUtil.getCon();
                    int addNum=UserDao.add(con, user);
                    if(addNum==1){
                        JOptionPane.showMessageDialog(null, "用户添加成功！");
                        resetValue();
                    }else{
                        JOptionPane.showMessageDialog(null, "用户添加失败！");
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "用户添加失败！");
                }finally{
                    try {
                        DbUtil.closeCon(con);
                    } catch (Exception ex) {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                    }
                }
            }
        });
        JButton cancelButton = new JButton("取消");
        cancelButton.setIcon(new ImageIcon(LoginWindow.class.getResource("/images/cancel.jpg")));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 关闭当前窗口
                welcomeWindow.setVisible(true); // 返回欢迎界面
            }
        });
        JButton reSetButton = new JButton("重置");
        reSetButton.setIcon(new ImageIcon(LoginWindow.class.getResource("/images/reset.png")));
        reSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                passwordField.setText("");
            }
        });
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(reSetButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    private void resetValue(){
        this.usernameField.setText("");
        this.passwordField.setText("");
        this.confirmPasswordLabel.setText("");
    }

    public void setWelcomeWindow(WelcomeWindow welcomeWindow) {
        this.welcomeWindow = welcomeWindow;
    }

}
