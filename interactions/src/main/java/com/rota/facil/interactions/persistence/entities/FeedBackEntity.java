package com.rota.facil.interactions.persistence.entities;

import com.rota.facil.users.persistence.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@Table(name = "feedbacks_tb")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "feedback_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_user_id")
    private UserEntity receiver;

    private String feedback;

    private Double note;

    @Builder.Default
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
