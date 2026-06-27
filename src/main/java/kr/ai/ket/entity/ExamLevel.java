package kr.ai.ket.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(
        name = "exam_level",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_exam_level_level", columnNames = "level")
        }
)
public class ExamLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private int price;

}
