package kr.ai.ket.entity;

import jakarta.persistence.*;
import kr.ai.ket.entity.type.ExamSessionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "exam_session",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_exam_session_public_id", columnNames = "public_id"),
                @UniqueConstraint(name = "uk_exam_session_exam_member", columnNames = {"exam_id", "member_id"}),
        },
        indexes = {
                @Index(name = "ix_exam_session_member_id", columnList = "member_id"),
                @Index(name = "ix_exam_session_question_set_id", columnList = "question_set_id"),
                @Index(name = "ix_exam_session_rating_tier_id", columnList = "rating_tier_id"),
                @Index(name = "ix_exam_session_status", columnList = "status"),
        }
)
public class ExamSession extends BaseEntity {

    @Column(name = "public_id", nullable = false, updatable = false)
    private UUID publicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "exam_id",
            foreignKey = @ForeignKey(name = "fk_exam_session_exam")
    )
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "question_set_id",
            foreignKey = @ForeignKey(name = "fk_exam_session_question_set")
    )
    private QuestionSet questionSet;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "member_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_exam_session_member")
    )
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "examinee_country_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_exam_session_examinee_country")
    )
    private Country examineeCountry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "rating_tier_id",
            foreignKey = @ForeignKey(name = "fk_exam_session_rating_tier")
    )
    private RatingTier ratingTier;

    @Column(name = "applicant_is_examinee", nullable = false)
    private boolean applicantExaminee = true;

    @Column(name = "examinee_name", nullable = false)
    private String examineeName;

    @Column
    private String examineeFirstName;

    @Column
    private String examineeLastName;

    @Column
    private LocalDate examineeBirthdate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ExamSessionStatus status = ExamSessionStatus.REGISTERED;

    @Column(nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant registeredAt;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant startedAt;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant submittedAt;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant gradedAt;

    @Column
    private Integer totalScore;

    @Column
    private boolean passed;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant canceledAt;

    @Override
    protected void onPrePersist() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
        if (registeredAt == null) {
            registeredAt = Instant.now();
        }
    }
}
