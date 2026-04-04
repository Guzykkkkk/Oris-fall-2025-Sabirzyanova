package ru.itis.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trim_levels")
@NoArgsConstructor
@Data
public class TrimLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 400)
    private String description;

    @Column(nullable = false)
    private BigDecimal extraPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_model_id", nullable = false)
    private CarModel carModel;

    @OneToMany(mappedBy = "trimLevel", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id asc")
    private List<OptionItem> options = new ArrayList<>();

}
