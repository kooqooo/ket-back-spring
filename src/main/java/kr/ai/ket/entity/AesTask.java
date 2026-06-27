package kr.ai.ket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "aes_task",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_aes_task_exam_session", columnNames = "exam_session_id")
        }
)
public class AesTask extends BaseEntity {

    @Column(name = "public_id", nullable = false, updatable = false)
    private UUID publicId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "exam_session_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_aes_task_exam_session")
    )
    private ExamSession examSession;

    @Column(nullable = false, length = 50)
    private String status;

    @Override
    protected void onPrePersist() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }
}
