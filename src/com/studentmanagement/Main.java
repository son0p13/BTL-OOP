package com.studentmanagement;

import com.studentmanagement.service.*;
import com.studentmanagement.view.LoginFrame;
import com.studentmanagement.view.MainFrame;

import javax.swing.*;

/**
 * Main application entry point for CodeMap Studio Student Management System.
 */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            // Instantiate Services
            AuthService authService = new AuthService();
            FacultyService facultyService = new FacultyService();
            ClassService classService = new ClassService();
            AdvisorService advisorService = new AdvisorService();
            SubjectService subjectService = new SubjectService();
            StudentService studentService = new StudentService();
            GradeService gradeService = new GradeService();

            // Launch Login Frame (Video #2)
            LoginFrame loginFrame = new LoginFrame(authService);
            loginFrame.setLoginListener(user -> {
                // Open Main Application Window with all 8 modules on successful login
                MainFrame mainFrame = new MainFrame(authService, facultyService, classService, advisorService, subjectService, studentService, gradeService);
                mainFrame.setVisible(true);
            });
            loginFrame.setVisible(true);
        });
    }
}
