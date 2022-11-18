package com.zephsie.spring.services;

import com.zephsie.spring.models.Player;
import com.zephsie.spring.repositories.PlayerRepository;
import com.zephsie.spring.util.PlayerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Optional<Player> get(Long id) {
        return playerRepository.findById(id);
    }

    public List<Player> getTop5() {
        return playerRepository.findTop5ByOrderByWinsDesc();
    }

    @Transactional
    public Player save(Player player) {
        return playerRepository.findByName(player.getName()).orElseGet(() -> playerRepository.save(new Player(player.getName(), 0)));
    }

    @Transactional
    public Player addWin(Long id) {
        Optional<Player> player = playerRepository.findById(id);

        if (player.isPresent()) {
            Player p = player.get();
            p.setWins(p.getWins() + 1);
            playerRepository.save(p);
        } else {
            throw new PlayerNotFoundException("Player not found");
        }

        return player.get();
    }
}