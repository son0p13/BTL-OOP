package com.studentmanagement;

import com.studentmanagement.service.*;
import com.studentmanagement.view.LoginFrame;
import com.studentmanagement.view.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            AuthService authService = new AuthService();
            FacultyService facultyService = new FacultyService();
            ClassService classService = new ClassService();
            AdvisorService advisorService = new AdvisorService();
            SubjectService subjectService = new SubjectService();
            StudentService studentService = new StudentService();
            GradeService gradeService = new GradeService();

            LoginFrame loginFrame = new LoginFrame(authService);
            loginFrame.setLoginListener(user -> {
                MainFrame mainFrame = new MainFrame(authService, facultyService, classService, advisorService, subjectService, studentService, gradeService);
                mainFrame.setVisible(true);
            });
            loginFrame.setVisible(true);
        });
    }
}
