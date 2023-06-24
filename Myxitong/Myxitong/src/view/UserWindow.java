package view;

import dao.UserDao;
import model.User;
import util.DbUtil;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

class UserWindow extends JFrame {
    private JTable userTable;
    private JMenuItem backButton;

    public UserWindow() {
        this.setTitle("System User Window");
        this.setSize(670, 400);
        // 将窗口居中显示
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) ((screenSize.getWidth() - getWidth()) / 2);
        int centerY = (int) ((screenSize.getHeight() - getHeight()) / 2);
        setLocation(centerX, centerY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.userTable = new JTable();

        JPanel panel = new JPanel(new BorderLayout());

        JPanel editPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel idLabel = new JLabel("ID:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel emailLabel = new JLabel("Email:");
        JTextField idField = new JTextField(5);
        JTextField nameField = new JTextField(5);
        JTextField passwordField = new JTextField(5);
        JTextField emailField = new JTextField(10);
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        editPanel.add(idLabel);
        editPanel.add(idField);
        editPanel.add(nameLabel);
        editPanel.add(nameField);
        editPanel.add(passwordLabel);
        editPanel.add(passwordField);
        editPanel.add(emailLabel);
        editPanel.add(emailField);
        editPanel.add(updateButton);
        editPanel.add(deleteButton);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = userTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(UserWindow.this,
                            "Please select a row first.");
                    return;
                }

                String userId = idField.getText().trim();
                String userName = nameField.getText().trim();
                String password = passwordField.getText().trim();
                String email = emailField.getText().trim();

                if (userId.length() == 0 || userName.length() == 0 || password.length() == 0 || email.length() == 0) {
                    JOptionPane.showMessageDialog(UserWindow.this,
                            "Please fill in all fields.");
                    return;
                }

                User user = new User(Integer.parseInt(userId), userName, password, email);
                try {
                    Connection con = DbUtil.getCon();
                    UserDao userDao = new UserDao();
                    userDao.update(con, user);
                    con.close();
                    refreshTable();
                    JOptionPane.showMessageDialog(UserWindow.this,
                            "User updated successfully.");
                } catch (SQLException exception) {
                    JOptionPane.showMessageDialog(UserWindow.this,
                            "Failed to update the user.");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = userTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(UserWindow.this,
                            "Please select a row first.");
                    return;
                }

                int result = JOptionPane.showConfirmDialog(UserWindow.this,
                        "Are you sure you want to delete this user?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    String userId = idField.getText().trim();
                    try {
                        Connection con = DbUtil.getCon();
                        UserDao userDao = new UserDao();
                        userDao.delete(con, Integer.parseInt(userId));
                        con.close();
                        refreshTable();
                        JOptionPane.showMessageDialog(UserWindow.this,
                                "User deleted successfully.");
                    } catch (SQLException exception) {
                        JOptionPane.showMessageDialog(UserWindow.this,
                                "Failed to delete the user.");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

        panel.add(editPanel, BorderLayout.NORTH);

        this.userTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = userTable.getSelectedRow();
                if (row != -1) {
                    String userId = userTable.getValueAt(row, 0).toString();
                    String userName = userTable.getValueAt(row, 1).toString();
                    String password = userTable.getValueAt(row, 2).toString();
                    String email = userTable.getValueAt(row, 3).toString();
                    // 将文本框中的值设置为选中行的值
                    idField.setText(userId);
                    nameField.setText(userName);
                    passwordField.setText(password);
                    emailField.setText(email);
                }
            }
        });

        try {
            Connection con = DbUtil.getCon();
            UserDao UserDao = new UserDao();
            List<User> users = UserDao.getAllUsers(con);
            String[] columnNames = {"ID", "Name", "Password", "Email"};
            Object[][] data = new Object[users.size()][4];
            for (int i = 0; i < users.size(); i++) {
                data[i][0] = users.get(i).getId();
                data[i][1] = users.get(i).getUserName();
                data[i][2] = users.get(i).getPassword();
                data[i][3] = users.get(i).getEmail();
            }
            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
            // 将新创建的JTable替换为原始的JTable即可
            userTable.setModel(tableModel);
            userTable.setRowHeight(30);
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(this, "Failed to retrieve system users from the database.");
            this.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        this.backButton = new JMenuItem("Back");
        menu.add(this.backButton);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        this.backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentManagementWindow studentManagementSystem = new StudentManagementWindow();
                studentManagementSystem.setVisible(true);
                UserWindow.this.setVisible(false);
            }
        });

        JScrollPane scrollPane = new JScrollPane(this.userTable);
        panel.add(scrollPane);
        this.getContentPane().add(panel);
    }

    private void refreshTable() {
        try {
            Connection con = DbUtil.getCon();
            UserDao userDao = new UserDao();
            List<User> users = userDao.getAllUsers(con);
            String[] columnNames = {"ID", "Name", "Password", "Email"};
            Object[][] data = new Object[users.size()][4];
            for (int i = 0; i < users.size(); i++) {
                data[i][0] = users.get(i).getId();
                data[i][1] = users.get(i).getUserName();
                data[i][2] = users.get(i).getPassword();
                data[i][3] = users.get(i).getEmail();
            }
            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
            userTable.setModel(tableModel);
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(this, "Failed to retrieve system users from the database.");
            this.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
