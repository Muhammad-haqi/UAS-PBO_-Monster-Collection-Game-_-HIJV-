package com.game.projekgame.service;

import com.game.projekgame.dto.LeaderboardResponse;
import com.game.projekgame.entity.LeaderboardEntry;
import com.game.projekgame.repository.LeaderboardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;

    // Update leaderboard saat user save game
    @Transactional
    public void updateLeaderboard(String username, int score, int level) {
        LeaderboardEntry entry = leaderboardRepository.findByUsername(username)
                .orElse(LeaderboardEntry.builder().username(username).build());

        // Hanya update jika skor baru lebih tinggi
        if (score > entry.getScore()) {
            entry.setScore(score);
            entry.setLevel(level);
            entry.setSavedAt(LocalDateTime.now());
            leaderboardRepository.save(entry);
            log.info("Leaderboard diperbarui: {} → score={}", username, score);
        }
    }

    // Ambil top 10 leaderboard
    public List<LeaderboardResponse> getTop10() {
        List<LeaderboardEntry> entries =
                leaderboardRepository.findTop10ByOrderByScoreDesc();

        // Tambahkan rank (1, 2, 3, ...)
        return IntStream.range(0, entries.size())
                .mapToObj(i -> {
                    LeaderboardEntry e = entries.get(i);
                    return LeaderboardResponse.builder()
                            .rank(i + 1)
                            .username(e.getUsername())
                            .score(e.getScore())
                            .level(e.getLevel())
                            .savedAt(e.getSavedAt())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
