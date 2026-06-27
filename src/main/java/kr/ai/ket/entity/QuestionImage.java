package kr.ai.ket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "question_image",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_question_image_question", columnNames = "question_id")
        }
)
public class QuestionImage extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "question_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_question_image_question")
    )
    private Question question;

    @Column(nullable = false)
    private String objectKey;

    @Column(nullable = false, length = 100)
    private String mimeType;

    @Column(nullable = false)
    private int sizeBytes;

    @Column(nullable = false, length = 64)
    private String sha256;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private String storageBucket;

    @Column(nullable = false)
    private String storageRegion;
}
