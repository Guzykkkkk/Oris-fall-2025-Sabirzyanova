package org.Game.Seventh_March_Lab;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"account", "bank", "outgoingTransactions", "incomingTransactions"})
@Entity
@Table(name = "cards")
    public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;

    @Column(nullable = false)
    private String pan;

    @Column(name = "exp_date", nullable = false)
    private LocalDate expDate;

    @Column(nullable = false)
    private String cvv;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @OneToMany(mappedBy = "fromCard")
    private List<Transaction> outgoingTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "toCard")
    private List<Transaction> incomingTransactions = new ArrayList<>();
}
