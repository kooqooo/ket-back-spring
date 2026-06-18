package kr.ai.ket.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(
        name = "institution",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_institution_code", columnNames = "code")
        }
)
public class Institution extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String code;

    @Column(length = 100)
    private String alias;

}
