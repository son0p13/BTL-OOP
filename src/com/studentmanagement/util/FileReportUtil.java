package com.studentmanagement.util;

import com.studentmanagement.model.Grade;
import com.studentmanagement.model.Student;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Utility for exporting formatted Student Transcripts and Grade Reports in TXT format.
 */
public class FileReportUtil {

    private FileReportUtil() {}

    public static void exportStudentTranscript(File file, Student student, List<Grade> grades) throws IOException {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write("=========================================================================================");
            writer.newLine();
            writer.write("                              BẢNG ĐIỂM CÁ NHÂN SINH VIÊN                                ");
            writer.newLine();
            writer.write("=========================================================================================");
            writer.newLine();
            writer.write("Mã Sinh Viên : " + student.getStudentId());
            writer.newLine();
            writer.write("Họ và Tên    : " + student.getFullName());
            writer.newLine();
            writer.write("Lớp Sinh Viên: " + student.getClassId());
            writer.newLine();
            writer.write("Quê Quán     : " + student.getHometown());
            writer.newLine();
            writer.write("-----------------------------------------------------------------------------------------");
            writer.newLine();
            writer.write(String.format("%-10s | %-10s | %-10s | %-10s | %-12s | %-8s | %-10s",
                    "MÃ MÔN", "Đ.CẦN(10%)", "G.KỲ(30%)", "C.KỲ(60%)", "TỔNG KẾT(10)", "ĐIỂM CHỮ", "XẾP LOẠI"));
            writer.newLine();
            writer.write("-----------------------------------------------------------------------------------------");
            writer.newLine();

            double totalGpa4Sum = 0.0;
            int count = 0;

            for (Grade g : grades) {
                writer.write(String.format("%-10s | %-10.1f | %-10.1f | %-10.1f | %-12.2f | %-8s | %-10s",
                        g.getSubjectId(),
                        g.getAttendanceScore(),
                        g.getMidtermScore(),
                        g.getFinalScore(),
                        g.calculateTotal10(),
                        g.getLetterGrade(),
                        g.getRank()));
                writer.newLine();
                totalGpa4Sum += g.calculateTotal4();
                count++;
            }

            writer.write("=========================================================================================");
            writer.newLine();
            double avgGpa4 = (count > 0) ? totalGpa4Sum / count : 0.0;
            writer.write(String.format("DỂM GPA TÍCH LŨY (HỆ 4): %.2f", avgGpa4));
            writer.newLine();
        }
    }
}
