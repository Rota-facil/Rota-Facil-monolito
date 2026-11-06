package com.rota.facil.security.persistence.entities;

import com.rota.facil.users.persistence.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Table(name = "token_complete_google_login_tb")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenCompleteGoogleLoginEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "token_complete_google_login_id")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "pending_token")
    private UUID pendingToken;

    @Builder.Default
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @UpdateTimestamp
    private LocalDateTime expiration = LocalDateTime.now();
}
