package kr.ai.ket.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(
        name = "country",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_country_code", columnNames = "code"),
                @UniqueConstraint(name = "uk_country_code3", columnNames = "code3"),
        }
)
@Getter
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 2)
    private String code;

    @Column(nullable = false, length = 3)
    private String code3;

    @Column(nullable = false, length = 8)
    private String flagEmoji;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

}
