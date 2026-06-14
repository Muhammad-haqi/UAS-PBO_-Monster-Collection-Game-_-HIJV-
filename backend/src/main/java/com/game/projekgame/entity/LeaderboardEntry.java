package com.game.projekgame.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "leaderboard")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LeaderboardEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime savedAt = LocalDateTime.now();
}
