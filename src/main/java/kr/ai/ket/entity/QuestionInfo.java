package kr.ai.ket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "question_info",
        indexes = {
                @Index(name = "ix_question_info_exam_level_id", columnList = "exam_level_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_question_info_exam_level_number", columnNames = {"exam_level_id", "number"})
        }
)
public class QuestionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "exam_level_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_question_info_exam_level")
    )
    private ExamLevel examLevel;

    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    private String assessmentGoal;

    @Column(nullable = false)
    private String curriculumCode;

    @Column(nullable = false, length = 100)
    private String questionType;

    @Column(nullable = false, length = 100)
    private String contentType;

    @Column(nullable = false)
    private String contentTitle;

    @Column(nullable = false, length = 100)
    private String difficulty;

    @Column(nullable = false, length = 100)
    private String writingStyle;

    @Column(nullable = false)
    private int assessmentTime;

    @Column(nullable = false)
    private int characterCount;

    @Column(nullable = false)
    private int defaultScore;
}
