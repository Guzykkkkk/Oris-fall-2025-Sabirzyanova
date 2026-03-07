package org.Game.Seventh_March_Lab;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "cards")
@Entity
@Table(name = "banks")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "bank")
    private List<Card> cards =  new ArrayList<>();
}
