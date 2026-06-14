package com.game.projekgame.controller;

import com.game.projekgame.dto.ApiResponse;
import com.game.projekgame.dto.LeaderboardResponse;
import com.game.projekgame.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    /**
     * GET /api/leaderboard
     * Dipanggil dari LeaderboardController.java di frontend
     * untuk menampilkan top 10 player
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<LeaderboardResponse>>> getLeaderboard() {
        List<LeaderboardResponse> top10 = leaderboardService.getTop10();
        return ResponseEntity.ok(ApiResponse.success("Top 10 Leaderboard", top10));
    }
}
