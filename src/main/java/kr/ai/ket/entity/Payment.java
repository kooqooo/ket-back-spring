package kr.ai.ket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(
        name = "payment",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_payment_exam_session", columnNames = "exam_session_id")
        }
)
public class Payment extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "exam_session_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_payment_exam_session")
    )
    private ExamSession examSession;

    @Column(nullable = false)
    private String portonePaymentId;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Long refundAmount;

    @Column(nullable = false, length = 50)
    private String method;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant paidAt;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant canceledAt;
}
