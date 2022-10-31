package com.zephsie.spring.controllers;

import com.zephsie.spring.models.Player;
import com.zephsie.spring.services.PlayerService;
import com.zephsie.spring.utits.PlayerValidator;
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

    private final PlayerValidator playerValidator;

    @Autowired
    public PlayerRestController(PlayerService playerService, PlayerValidator playerValidator) {
        this.playerService = playerService;
        this.playerValidator = playerValidator;
    }

    @GetMapping(value = "/top", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Player>> get() {
        try {
            return ResponseEntity.ok(playerService.get());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> save(@Valid @RequestBody Player player, BindingResult bindingResult) {
        try {
            playerValidator.validate(player, bindingResult);

            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().build();
            }

            playerService.save(player);
            return ResponseEntity.ok(player);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @PatchMapping(value = "/{id}/win", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> addWin(@PathVariable Long id) {
        try {
            return playerService.addWin(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}