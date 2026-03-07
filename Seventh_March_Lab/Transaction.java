package org.Game.Seventh_March_Lab;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"fromCard", "toCard", "details"})
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;
    @Column
    private BigDecimal ammount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_card_id")
    private Card fromCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_card_id")
    private Card toCard;


    @OneToOne(mappedBy = "transaction")
    private TransactionDetails details;

}
