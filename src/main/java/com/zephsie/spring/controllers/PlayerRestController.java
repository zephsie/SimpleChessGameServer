package com.zephsie.spring.controllers;

import com.zephsie.spring.dto.PlayerDTO;
import com.zephsie.spring.models.Player;
import com.zephsie.spring.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerRestController {
    private final PlayerService playerService;

    @Autowired
    public PlayerRestController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(value = "/top", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Player>> getTop() {
        try {
            return ResponseEntity.ok(playerService.getTop());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> add(@Valid @RequestBody PlayerDTO playerDTO, BindingResult bindingResult) {
        try {
            return bindingResult.hasErrors() ? ResponseEntity.badRequest().build() : ResponseEntity.ok(playerService.save(playerDTO));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping(value = "/{id}/win", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> addWin(@PathVariable Long id) {
        try {
            return playerService.addWin(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}