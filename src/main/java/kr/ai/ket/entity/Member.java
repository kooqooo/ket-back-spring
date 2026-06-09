package kr.ai.ket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(
        name = "member",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_username", columnNames = "username"),
                @UniqueConstraint(name = "uk_email", columnNames = "email"),
        }
)
public class Member extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_admin", nullable = false)
    private boolean admin = false;

    @Column(nullable = false)
    private String fullName;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column
    private LocalDate birthDate;

    @Column
    private String phoneNumber;

    @Column(name = "is_email_verified", nullable = false)
    private boolean emailVerified = false;
}