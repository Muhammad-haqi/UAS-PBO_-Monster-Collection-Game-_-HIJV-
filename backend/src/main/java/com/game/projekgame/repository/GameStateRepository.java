package com.game.projekgame.repository;

import com.game.projekgame.entity.GameState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameStateRepository extends JpaRepository<GameState, Long> {

    Optional<GameState> findByUserId(Long userId);

    Optional<GameState> findByUserUsername(String username);
}
