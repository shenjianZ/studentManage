package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class StudentManagementWindow extends JFrame {
    private JButton systemUserButton;
    private JButton studentButton;
    private JButton exitButton;

    public StudentManagementWindow() {
        this.setTitle("Student Management System");
        setBounds(100, 100, 600, 343);
        // 将窗口居中显示
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) ((screenSize.getWidth() - getWidth()) / 2);
        int centerY = (int) ((screenSize.getHeight() - getHeight()) / 2);
        setLocation(centerX, centerY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        systemUserButton = new JButton("System User");
        studentButton = new JButton("Student");
        exitButton = new JButton("Exit");

        systemUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserWindow().setVisible(true);
                StudentManagementWindow.this.dispose();
            }
        });

        this.studentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentWindow().setVisible(true);
                StudentManagementWindow.this.dispose();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WelcomeWindow().setVisible(true);
                StudentManagementWindow.this.dispose();
            }
        });





        JPanel panel = new JPanel();
        panel.add(this.systemUserButton);
        panel.add(this.studentButton);
        panel.add(this.exitButton);
        this.getContentPane().add(panel);
    }

    public static void main(String[] args) {
        new StudentManagementWindow().setVisible(true);
    }
}

