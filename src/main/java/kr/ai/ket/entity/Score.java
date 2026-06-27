package kr.ai.ket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(
        name = "score",
        indexes = {
                @Index(name = "ix_score_submitted_answer_id", columnList = "submitted_answer_id"),
                @Index(name = "ix_score_rubric_id", columnList = "rubric_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_score_answer_rubric", columnNames = {"submitted_answer_id", "rubric_id"})
        }
)
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "submitted_answer_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_score_submitted_answer")
    )
    private SubmittedAnswer submittedAnswer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "rubric_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_score_rubric")
    )
    private Rubric rubric;

    @Column(nullable = false)
    private int awardedScore;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant gradedAt;
}
