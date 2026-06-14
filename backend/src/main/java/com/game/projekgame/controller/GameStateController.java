package com.game.projekgame.controller;

import com.game.projekgame.dto.ApiResponse;
import com.game.projekgame.dto.GameStateRequest;
import com.game.projekgame.dto.GameStateResponse;
import com.game.projekgame.service.GameStateService;
import com.game.projekgame.service.LeaderboardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameStateController {

    private final GameStateService gameStateService;
    private final LeaderboardService leaderboardService;

    /**
     * POST /api/game/save
     * Dipanggil saat: handleLogout(), setelah battle menang, setelah capture monster
     *
     * Body (cocok dengan GameData.java):
     * {
     *   "level": 2,
     *   "score": 350,
     *   "exp": 50,
     *   "activeMonsterName": "🔥 Fire Dragon",
     *   "monsterCollection": ["🔥 Fire Dragon", "💧 Water Turtle"],
     *   "battleTeam": ["🔥 Fire Dragon"]
     * }
     */
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<GameStateResponse>> saveGame(
            @RequestBody GameStateRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        GameStateResponse state = gameStateService.saveGameState(username, request);

        // Otomatis update leaderboard saat save
        leaderboardService.updateLeaderboard(username, request.getScore(), request.getLevel());

        return ResponseEntity.ok(ApiResponse.success("Game berhasil disimpan!", state));
    }

    /**
     * GET /api/game/load
     * Dipanggil saat login berhasil (newPlayer=false) untuk restore GameData
     *
     * Response:
     * {
     *   "hasSavedGame": true,
     *   "level": 2, "score": 350, "exp": 50,
     *   "activeMonsterName": "🔥 Fire Dragon",
     *   "monsterCollection": [...],
     *   "battleTeam": [...]
     * }
     */
    @GetMapping("/load")
    public ResponseEntity<ApiResponse<GameStateResponse>> loadGame(
            @AuthenticationPrincipal UserDetails userDetails) {

        GameStateResponse state = gameStateService.loadGameState(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Game state dimuat", state));
    }
}
