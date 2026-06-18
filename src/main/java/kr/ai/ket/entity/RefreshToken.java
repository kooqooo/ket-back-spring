package kr.ai.ket.entity;

import jakarta.persistence.*;
import kr.ai.ket.entity.type.RefreshTokenRevokedReason;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "refresh_token",
        indexes = {
                @Index(name = "ix_refresh_token_member_id", columnList = "member_id"),
                @Index(name = "ix_refresh_token_expires_at", columnList = "expires_at"),
                @Index(name = "ix_refresh_token_member_active", columnList = "member_id, revoked_at, expires_at")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_refresh_token_jti", columnNames = "jti"),
                @UniqueConstraint(name = "uk_refresh_token_hash", columnNames = "token_hash")
        }
)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "member_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_refresh_token_member")
    )
    private Member member;

    @Column(nullable = false)
    private UUID jti;

    @Column(nullable = false, length = 64)
    private String tokenHash;

    @Column(nullable = false)
    private Instant issuedAt;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column
    private Instant revokedAt;

    @Enumerated(EnumType.STRING)
    @Column
    private RefreshTokenRevokedReason revokedReason;

    @Column(columnDefinition = "TEXT")
    private String userAgent;

    @Column(length = 45)
    private String ipAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "replaced_by_token_id",
            foreignKey = @ForeignKey(name = "fk_refresh_token_replaced_by_token")
    )
    private RefreshToken replacedByToken;

    public boolean isExpired(Instant now) {
        return !expiresAt.isAfter(now);
    }

    public boolean isRevoked() {
        return revokedAt != null;
    }

    public boolean isActive(Instant now) {
        return !isExpired(now) && !isRevoked();
    }

    public void revokeByLogout(Instant now) {
        this.revokedAt = now;
        this.revokedReason = RefreshTokenRevokedReason.LOGOUT;
    }

    public void rotate(Instant now, RefreshToken newRefreshToken) {
        this.revokedAt = now;
        this.revokedReason = RefreshTokenRevokedReason.ROTATED;
        this.replacedByToken = newRefreshToken;
    }

}
