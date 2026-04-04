package ru.itis.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lead_requests")
@NoArgsConstructor
@Data
public class LeadRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String phoneNumber;

    @Column(length = 255)
    private String pageSource;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}
