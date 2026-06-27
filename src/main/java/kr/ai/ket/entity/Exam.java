package kr.ai.ket.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
@Table(
        name = "exam",
        indexes = {
                @Index(name = "ix_exam_exam_group_id", columnList = "exam_group_id"),
                @Index(name = "ix_exam_question_set_id", columnList = "question_set_id"),
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_exam_public_id", columnNames = "public_id")
        }
)
public class Exam extends BaseEntity {

    @Column(name = "public_id", nullable = false, updatable = false)
    private UUID publicId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "exam_group_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_exam_exam_group")
    )
    private ExamGroup examGroup;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "question_set_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_exam_question_set")
    )
    private QuestionSet questionSet;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant registrationStartsAt;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant registrationEndsAt;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant startsAt;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant endsAt;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant resultsAvailableAt;

    @Override
    protected void onPrePersist() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }

}
