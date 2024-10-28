package com.spring.mvc.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateValidator.class) // Liên kết với class validator
@Target({ElementType.FIELD, ElementType.METHOD}) // Áp dụng cho field và method
@Retention(RetentionPolicy.RUNTIME) // Lưu giữ thông tin annotation tại runtime
public @interface ValidDate {

    String message() default "Invalid date format. It should be dd/MM/yyyy"; // Thông báo lỗi

    Class<?>[] groups() default {}; // Cho phép phân loại validator theo nhóm

    Class<? extends Payload>[] payload() default {}; // Cung cấp thêm thông tin

    String pattern() default "dd/MM/yyyy"; // Cho phép chỉ định định dạng
}
