package com.game.projekgame.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "game_states")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GameState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relasi ke User
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // Data dari GameData.java di frontend
    @Builder.Default
    private int level = 1;

    @Builder.Default
    private int score = 0;

    @Builder.Default
    private int exp = 0;

    // Monster aktif saat ini (nama monster)
    private String activeMonsterName;

    // Koleksi monster disimpan sebagai string CSV
    // contoh: "🔥 Fire Dragon,💧 Water Turtle,⚡ Thunder Wolf"
    @Column(length = 1000)
    @Builder.Default
    private String monsterCollection = "";

    // Battle team (max 3) disimpan sebagai string CSV
    @Column(length = 500)
    @Builder.Default
    private String battleTeam = "";
}
