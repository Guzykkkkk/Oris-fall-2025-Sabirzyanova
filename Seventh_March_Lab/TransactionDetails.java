package org.Game.Seventh_March_Lab;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "transactions")
@Entity
@Table(name = "transaction_details")
public class TransactionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long id;
    @Column(nullable = false)
    private LocalDateTime dateTime;
    private String description;
    @OneToOne
    @JoinColumn(name = "transaction_id", unique = true)
    private Transaction transaction;
}
