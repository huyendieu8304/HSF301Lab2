package com.spring.mvc.dto;

import com.spring.mvc.enumeration.EAgentStatus;
import com.spring.mvc.validator.ValidDate;
import com.spring.mvc.validator.ValidNumber;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentDto {

    private Integer id;

    @NotBlank(message = "{agent.name.required}")
    @Pattern(regexp = "[A-Za-zÀ-ỹ\\s]+", message = "{agent.name.invalid}")
    private String name;

    @NotBlank(message = "{agent.email.required}")
    @Email(message = "agent.email.format")
    private String email;

    @NotNull(message = "{agent.status.required}")
    private EAgentStatus status;

    @NotBlank(message = "{agent.address.required}")
    private String address;

    @NotBlank(message = "{agent.registerDate.required}")
    @ValidDate(pattern = "dd/MM/yyyy", message = "{valid.date}")
    private String registerDate;

    @NotNull(message = "{agent.accountBalance.required}")
    @ValidNumber(min = 1, message = "{valid.number}")
    private String accountBalance;


    @Override
    public String toString() {
        return "AgentDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", address='" + address + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", accountBalance='" + accountBalance + '\'' +
                '}';
    }
}
