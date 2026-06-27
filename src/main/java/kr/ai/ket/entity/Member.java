package kr.ai.ket.entity;

import jakarta.persistence.*;
import kr.ai.ket.entity.type.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "member",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_member_public_id", columnNames = "public_id"),
                @UniqueConstraint(name = "uk_member_username", columnNames = "username"),
                @UniqueConstraint(name = "uk_member_email", columnNames = "email"),
        }
)
public class Member extends BaseEntity {

    @Column(name = "public_id", nullable = false, updatable = false)
    private UUID publicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id", foreignKey = @ForeignKey(name = "fk_member_institution"))
    private Institution institution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "country_id",
            foreignKey = @ForeignKey(name = "fk_member_country")
    )
    private Country country;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String name;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column
    private LocalDate birthdate;

    @Column
    private String phoneNumber;

    @Column(name = "is_email_verified", nullable = false)
    private boolean emailVerified = false;

    @Override
    protected void onPrePersist() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }

}
