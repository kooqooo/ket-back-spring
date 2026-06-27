package kr.ai.ket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "question",
        indexes = {
                @Index(name = "ix_question_question_set_id", columnList = "question_set_id"),
                @Index(name = "ix_question_question_info_id", columnList = "question_info_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_question_set_info", columnNames = {"question_set_id", "question_info_id"})
        }
)
public class Question extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "question_set_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_question_question_set")
    )
    private QuestionSet questionSet;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "question_info_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_question_question_info")
    )
    private QuestionInfo questionInfo;

    @Column(columnDefinition = "TEXT")
    private String instruction;

    @Column(columnDefinition = "TEXT")
    private String contentText;

    @Column(columnDefinition = "TEXT")
    private String prompt;

    @Column(columnDefinition = "TEXT")
    private String modelAnswer;
}
