package kr.ai.ket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(
        name = "submitted_answer",
        indexes = {
                @Index(name = "ix_submitted_answer_exam_session_id", columnList = "exam_session_id"),
                @Index(name = "ix_submitted_answer_question_id", columnList = "question_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_submitted_answer_session_question", columnNames = {"exam_session_id", "question_id"})
        }
)
public class SubmittedAnswer extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "exam_session_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_submitted_answer_exam_session")
    )
    private ExamSession examSession;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "question_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_submitted_answer_question")
    )
    private Question question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answerText;

    @Column(nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant submittedAt;
}
