package kr.ai.ket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "criterion",
        indexes = {
                @Index(name = "ix_criterion_question_info_id", columnList = "question_info_id"),
                @Index(name = "ix_criterion_rubric_id", columnList = "rubric_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_criterion_question_info_rubric_score",
                        columnNames = {"question_info_id", "rubric_id", "score_value"}
                )
        }
)
public class Criterion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "question_info_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_criterion_question_info")
    )
    private QuestionInfo questionInfo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "rubric_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_criterion_rubric")
    )
    private Rubric rubric;

    @Column(name = "score_value", nullable = false)
    private int scoreValue;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descriptor;
}
