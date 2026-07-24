package com.studentmanagement.view;

import com.studentmanagement.exception.AppException;
import com.studentmanagement.service.AuthService;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordDialog extends JDialog {

    private JPasswordField txtOldPassword;
    private JPasswordField txtNewPassword;
    private JPasswordField txtConfirmPassword;
    private JButton btnSave;
    private JButton btnCancel;

    private final AuthService authService;

    public ChangePasswordDialog(Frame owner, AuthService authService) {
        super(owner, "Đổi Mật Khẩu Tài Khoản", true);
        this.authService = authService;
        initComponents();
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mật khẩu hiện tại:"), gbc);
        gbc.gridx = 1;
        txtOldPassword = new JPasswordField(18);
        formPanel.add(txtOldPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Mật khẩu mới:"), gbc);
        gbc.gridx = 1;
        txtNewPassword = new JPasswordField(18);
        formPanel.add(txtNewPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Xác nhận mật khẩu mới:"), gbc);
        gbc.gridx = 1;
        txtConfirmPassword = new JPasswordField(18);
        formPanel.add(txtConfirmPassword, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnSave = new JButton("Lưu Mật Khẩu");
        btnCancel = new JButton("Hủy Bỏ");

        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        add(btnPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> dispose());
    }

    private void onSave() {
        String oldPwd = new String(txtOldPassword.getPassword());
        String newPwd = new String(txtNewPassword.getPassword());
        String confirmPwd = new String(txtConfirmPassword.getPassword());

        try {
            authService.changePassword(oldPwd, newPwd, confirmPwd);
            JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi Thay Đổi Mật Khẩu", JOptionPane.ERROR_MESSAGE);
        }
    }
}
