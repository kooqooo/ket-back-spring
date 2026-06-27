package kr.ai.ket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "rating_tier",
        indexes = {
                @Index(name = "ix_rating_tier_exam_level_id", columnList = "exam_level_id")
        }
)
public class RatingTier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "exam_level_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_rating_tier_exam_level")
    )
    private ExamLevel examLevel;

    @Column(nullable = false)
    private int tier;

    @Column(nullable = false, length = 50)
    private String level;

    @Column(nullable = false)
    private int minScore;
}
