package kr.ai.ket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "exam_group",
        indexes = {
                @Index(name = "ix_exam_group_institution_id", columnList = "institution_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_exam_group_public_id", columnNames = "public_id")
        }
)
public class ExamGroup extends BaseEntity {

    @Column(name = "public_id", nullable = false, updatable = false)
    private UUID publicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "institution_id",
            foreignKey = @ForeignKey(name = "fk_exam_group_institution")
    )
    private Institution institution;

    @Column(nullable = false)
    private String name;

    @Column(name = "iteration_number", nullable = false)
    private int iterationNumber;

    @Override
    protected void onPrePersist() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }
}
