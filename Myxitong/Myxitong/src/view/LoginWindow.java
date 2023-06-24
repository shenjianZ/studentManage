package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import util.*;
import model.User;
import dao.UserDao;
public class LoginWindow extends JFrame {
    private WelcomeWindow welcomeWindow;

    public LoginWindow() {
        this.setTitle("登录 - 学生管理系统");
        this.setSize(300, 140);
        this.setDefaultCloseOperation(2);
        this.setResizable(false); // 设置窗口大小不可变

        //JLabel lblNewLabel = new JLabel("学生信息管理系统");
       // lblNewLabel.setFont(new Font("宋体", Font.BOLD, 23));
       // lblNewLabel.setIcon(new ImageIcon(LoginWindow.class.getResource("/images/logo.png")));
        // 将窗口居中显示
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) ((screenSize.getWidth() - getWidth()) / 2);
        int centerY = (int) ((screenSize.getHeight() - getHeight()) / 2);
        setLocation(centerX, centerY);

        // 创建登录表单
        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        JLabel usernameLabel = new JLabel("   用户名：", JLabel.RIGHT);
        usernameLabel.setIcon(new ImageIcon(LoginWindow.class.getResource("/images/userName.png")));
        JTextField usernameField = new JTextField();
        Dimension size = usernameField.getPreferredSize();
        size.height = 30;
        usernameField.setPreferredSize(size);
        JLabel passwordLabel = new JLabel("    密  码：", JLabel.RIGHT);
        passwordLabel.setIcon(new ImageIcon(LoginWindow.class.getResource("/images/password.png")));
        JPasswordField passwordField = new JPasswordField();
        Dimension size1= passwordField.getPreferredSize();
        size.height = 30;
        passwordField.setPreferredSize(size1);
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        this.add(formPanel, BorderLayout.CENTER);
        // 创建按钮
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton loginButton = new JButton("登录");
        loginButton.setIcon(new ImageIcon(LoginWindow.class.getResource("/images/login.png")));
        loginButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 在这里编写登录验证的代码
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (StringUtil.isEmpty(username)) {
                    JOptionPane.showMessageDialog(null, "用户名不能为空！");
                    return;
                }
                if (StringUtil.isEmpty(password)) {
                    JOptionPane.showMessageDialog(null, "密码不能为空！");
                    return;
                }
                Connection con = null;
                try {
                    User user = new User(username, password);
                    con = DbUtil.getCon();
                    User currentUser = UserDao.login(con, user);
                    if (currentUser != null) {
                        LoginWindow.this.dispose();
                        new StudentManagementWindow().setVisible(true);
                        //LoginWindow.this.setVisible(false);
                        welcomeWindow.dispose(); // 关闭WelcomeWindow
                    } else {
                        JOptionPane.showMessageDialog(null, "用户名或者密码错误！");
                    }
                } catch (Exception ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                } finally {
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
               // welcomeWindow.setVisible(true); // 返回欢迎界面
            }
        });
        //add(buttonPanel, BorderLayout.SOUTH);
        JButton reSetButton = new JButton("重置");
        reSetButton.setIcon(new ImageIcon(LoginWindow.class.getResource("/images/reset.png")));
        reSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                passwordField.setText("");
            }
        });
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(reSetButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setWelcomeWindow(WelcomeWindow welcomeWindow) {
        this.welcomeWindow = welcomeWindow;
    }
}
