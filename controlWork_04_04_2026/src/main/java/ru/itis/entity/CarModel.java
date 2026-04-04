package ru.itis.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "car_models")
@Data
@NoArgsConstructor
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 500)
    private String shortDescription;

    @Column(nullable = false)
    private BigDecimal basePrice;

    @Column(nullable = false, length = 255)
    private String imageUrl;

    @OneToMany(mappedBy = "carModel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("id asc")
    private List<TrimLevel> trims = new ArrayList<>();


}
