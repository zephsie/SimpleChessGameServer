package com.zephsie.spring.repositories;

import com.zephsie.spring.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByName(String name);

    List<Player> findTop5ByOrderByWinsDesc();
}
