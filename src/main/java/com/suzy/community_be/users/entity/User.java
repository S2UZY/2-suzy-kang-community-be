package com.suzy.community_be.users.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @Column(nullable = false, length = 40, unique = true)
    private String email;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String profile;

    @Column(nullable = false, length = 40)
    private String password;

    @Column(nullable = false, length = 10)
    private String nickname;

    @Builder
    public User(String email, String profile, String password, String nickname) {
        this.email = email;
        this.profile = profile;
        this.password = password;
        this.nickname = nickname;
    }


}
