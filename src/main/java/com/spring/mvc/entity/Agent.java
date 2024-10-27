package com.spring.mvc.entity;

import com.spring.mvc.enumeration.EAgentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique=true)
    private String name;

    @Column(nullable = false, unique=true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EAgentStatus status;

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String address;

    @Column(name = "register_date", nullable = false)
    private LocalDate registerDate;

    @Column(name = "account_balance")
    private Double accountBalance;



}
