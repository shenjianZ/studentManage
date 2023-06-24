package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class WelcomeWindow extends JFrame {
    public JButton loginButton;
    public JButton registerButton;

    public WelcomeWindow() {
        setTitle("欢迎使用学生管理系统");
        setBounds(100,100,700,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // 设置窗口大小不可变
        // 创建包含 welcomeLabel 的 JPanel，使用 BorderLayout 进行布局
        JPanel welcomePanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("欢迎使用学生管理系统！");
        welcomeLabel.setIcon(new ImageIcon(WelcomeWindow.class.getResource("/images/logo.png")));
        welcomeLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));  // 设置字体样式：微软雅黑，加粗，24号
        welcomeLabel.setForeground(Color.BLUE);  // 设置文字颜色为蓝色
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);  // 设置水平居中对齐
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);  // 设置垂直居中对齐
        //welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
       //add(welcomePanel, BorderLayout.CENTER); // 将 welcomePanel 添加到 CENTER 区域
        // 将窗口居中显示
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) ((screenSize.getWidth() - getWidth()) / 2);
        int centerY = (int) ((screenSize.getHeight() - getHeight()) / 2);
        setLocation(centerX, centerY);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        loginButton = new JButton("登录");
        loginButton.setIcon(new ImageIcon(WelcomeWindow.class.getResource("/images/login.png")));
        loginButton.setFont(new Font("宋体", Font.PLAIN, 18));   // 设置字体样式：宋体，普通，18号
        loginButton.setForeground(new Color(0x4CAF50));   // 设置字体颜色为绿色
        loginButton.setBackground(Color.BLACK);   // 设置背景色为黑色
        loginButton.setFocusPainted(false);  // 去除聚焦边框
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginWindow loginWindow = new LoginWindow();
                loginWindow.setWelcomeWindow(WelcomeWindow.this);
                loginWindow.setVisible(true);
                // 打开登录窗口
            }
        });
        buttonPanel.add(loginButton);


        registerButton = new JButton("注册");
        registerButton.setIcon(new ImageIcon(WelcomeWindow.class.getResource("/images/signup.jpg")));
        registerButton.setFont(new Font("宋体", Font.PLAIN, 18));   // 设置字体样式：宋体，普通，18号
        registerButton.setForeground(new Color(0x4CAF50));   // 设置字体颜色为绿色
        registerButton.setBackground(Color.BLACK);   // 设置背景色黑色
        registerButton.setFocusPainted(false);  // 去除聚焦边框
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 打开注册窗口
                RegisterWindow registerWindow = new RegisterWindow();
                registerWindow.setWelcomeWindow(WelcomeWindow.this);
                registerWindow.setVisible(true);
            }
        });
        buttonPanel.add(registerButton);
       add(buttonPanel, BorderLayout.SOUTH);
        JPanel background = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon(WelcomeWindow.class.getResource("/images/bg.jpg"));
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
// 设置背景色为半透明的淡蓝色
        background.setBackground(new Color(0x00, 0xBF, 0xFF, 150));
        add(background);
// 将原有的 welcomeLabel 移动到 background 中
       background.add(welcomeLabel, BorderLayout.CENTER);

    }
    public static void main(String[] args) {
        new WelcomeWindow().setVisible(true);
    }
}