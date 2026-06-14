package com.game.projekgame.repository;

import com.game.projekgame.entity.LeaderboardEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaderboardRepository extends JpaRepository<LeaderboardEntry, Long> {

    // Cari entry leaderboard berdasarkan username
    Optional<LeaderboardEntry> findByUsername(String username);

    // Top 10 diurutkan dari skor terbesar
    List<LeaderboardEntry> findTop10ByOrderByScoreDesc();
}
