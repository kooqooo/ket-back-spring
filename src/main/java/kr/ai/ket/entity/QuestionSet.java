package kr.ai.ket.entity;

import jakarta.persistence.*;
import kr.ai.ket.entity.type.QuestionSetType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "question_set",
        indexes = {
                @Index(name = "ix_question_set_exam_level_id", columnList = "exam_level_id")
        }
)
public class QuestionSet extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "exam_level_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_question_set_exam_level")
    )
    private ExamLevel examLevel;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 100)
    private String difficulty;

    @Enumerated(EnumType.STRING)
    @Column(name = "set_type", nullable = false, length = 30)
    private QuestionSetType setType;
}
