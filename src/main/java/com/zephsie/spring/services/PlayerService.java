package com.zephsie.spring.services;

import com.zephsie.spring.dto.PlayerDTO;
import com.zephsie.spring.models.Player;
import com.zephsie.spring.repositories.PlayerRepository;
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

    public List<Player> getTop() {
        return playerRepository.findTop5ByOrderByWinsDesc();
    }

    @Transactional
    public Player save(PlayerDTO playerDTO) {
        return playerRepository.findByName(playerDTO.getName()).orElseGet(() -> playerRepository.save(new Player(playerDTO.getName(), 0)));
    }

    @Transactional
    public Optional<Player> addWin(Long id) {
        Optional<Player> player = playerRepository.findById(id);

        if (player.isPresent()) {
            Player p = player.get();
            p.setWins(p.getWins() + 1);
            playerRepository.save(p);
        }

        return player;
    }
}