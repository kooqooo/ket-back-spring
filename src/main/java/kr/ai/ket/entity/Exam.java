package kr.ai.ket.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;

@Getter
@Entity
@Table(
        name = "exam",
        indexes = {
                @Index(name = "ix_exam_institution_id", columnList = "institution_id"),
        }
        // (level, inst, iter) 조합의 unique constraint는 DDL에 정의
)
public class Exam extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "level_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_exam_exam_level")
    )
    private ExamLevel level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "institution_id",
            foreignKey = @ForeignKey(name = "fk_exam_institution")
    )
    private Institution institution;

    @Column(nullable = false)
    private int iterationNumber;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    Instant registrationStartsAt;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    Instant registrationEndsAt;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    Instant startsAt;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    Instant endsAt;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    Instant resultAvailableAt;

}