package com.studentmanagement.util;

import com.studentmanagement.exception.InvalidDataException;
import java.util.regex.Pattern;

/**
 * Utility for input data validation.
 */
public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10,11}$");

    private ValidationUtil() {}

    public static void validateNotEmpty(String value, String fieldName) throws InvalidDataException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidDataException(fieldName + " không được để trống!");
        }
    }

    public static void validateEmail(String email) throws InvalidDataException {
        validateNotEmpty(email, "Email");
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new InvalidDataException("Email không đúng định dạng!");
        }
    }

    public static void validatePhone(String phone) throws InvalidDataException {
        validateNotEmpty(phone, "Số điện thoại");
        if (!PHONE_PATTERN.matcher(phone.trim()).matches()) {
            throw new InvalidDataException("Số điện thoại phải gồm 10 đến 11 chữ số!");
        }
    }

    public static void validateScore(double score, String scoreName) throws InvalidDataException {
        if (score < 0.0 || score > 10.0) {
            throw new InvalidDataException(scoreName + " phải nằm trong khoảng từ 0.0 đến 10.0!");
        }
    }
}
