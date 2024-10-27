package com.spring.mvc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class AccountDto {
    private Integer id;
    @NotBlank (message = "{account.email.required}")
    @Email(message = "{account.email.format}")
    private String email;

    @NotBlank (message = "{account.password.required}")
    private String password;
}
