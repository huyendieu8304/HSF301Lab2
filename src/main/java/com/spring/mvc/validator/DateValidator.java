package com.spring.mvc.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class DateValidator implements ConstraintValidator<ValidDate, String> {

    private String datePattern;
    private String message;
    @Override
    public void initialize(ValidDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.datePattern = constraintAnnotation.pattern(); // Lấy định dạng từ annotation
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        // Nếu giá trị null thì bỏ qua validation (có thể dùng @NotNull để kiểm tra null)
        if (date == null) {
            return true;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        sdf.setLenient(false); // Đảm bảo rằng không chấp nhận ngày không hợp lệ như 30/02

        try {
            //Kiem tra neu message is a key
            if (message != null && message.contains("{")) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message)
                        .addConstraintViolation();
            }
            // Kiểm tra tính hợp lệ của chuỗi
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            // Nếu có lỗi, nghĩa là chuỗi không khớp với định dạng
            return false;
        }
    }
}
