package com.game.projekgame.service;

import com.game.projekgame.dto.GameStateRequest;
import com.game.projekgame.dto.GameStateResponse;
import com.game.projekgame.entity.GameState;
import com.game.projekgame.entity.User;
import com.game.projekgame.exception.ResourceNotFoundException;
import com.game.projekgame.repository.GameStateRepository;
import com.game.projekgame.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameStateService {

    private final GameStateRepository gameStateRepository;
    private final UserRepository userRepository;

    // ─── Save game state (create or update) ──────────

    @Transactional
    public GameStateResponse saveGameState(String username, GameStateRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan"));

        // Cari existing state, kalau tidak ada buat baru
        GameState state = gameStateRepository.findByUserUsername(username)
                .orElse(GameState.builder().user(user).build());

        // Update semua field dari request (cocok dengan GameData.java)
        state.setLevel(request.getLevel());
        state.setScore(request.getScore());
        state.setExp(request.getExp());
        state.setActiveMonsterName(request.getActiveMonsterName());
        state.setMonsterCollection(listToCsv(request.getMonsterCollection()));
        state.setBattleTeam(listToCsv(request.getBattleTeam()));

        gameStateRepository.save(state);
        log.info("Game state disimpan untuk user '{}', score={}", username, state.getScore());

        return toResponse(username, state);
    }

    // ─── Load game state ──────────────────────────────

    public GameStateResponse loadGameState(String username) {
        GameState state = gameStateRepository.findByUserUsername(username).orElse(null);

        if (state == null) {
            // Belum pernah save, kembalikan state default
            return GameStateResponse.builder()
                    .username(username)
                    .level(1).score(0).exp(0)
                    .monsterCollection(Collections.emptyList())
                    .battleTeam(Collections.emptyList())
                    .hasSavedGame(false)
                    .build();
        }

        return toResponse(username, state);
    }

    // ─── Helper ───────────────────────────────────────

    private String listToCsv(List<String> list) {
        if (list == null || list.isEmpty()) return "";
        return String.join(",", list);
    }

    private List<String> csvToList(String csv) {
        if (csv == null || csv.isBlank()) return Collections.emptyList();
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private GameStateResponse toResponse(String username, GameState state) {
        return GameStateResponse.builder()
                .username(username)
                .level(state.getLevel())
                .score(state.getScore())
                .exp(state.getExp())
                .activeMonsterName(state.getActiveMonsterName())
                .monsterCollection(csvToList(state.getMonsterCollection()))
                .battleTeam(csvToList(state.getBattleTeam()))
                .hasSavedGame(true)
                .build();
    }
}
