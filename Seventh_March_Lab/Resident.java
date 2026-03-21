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
@ToString(exclude = "accounts")
@Entity
@Table(name = "residents")
public class Resident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resident_id")
    private Long id;
    @Column(name = "country_name")
    private String countryName;
    @ManyToMany(mappedBy = "residents")
    private List<Account> accounts =  new ArrayList<>();

}
