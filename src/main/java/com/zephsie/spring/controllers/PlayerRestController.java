package com.zephsie.spring.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zephsie.spring.dto.PlayerDTO;
import com.zephsie.spring.models.Player;
import com.zephsie.spring.services.PlayerService;
import com.zephsie.spring.util.PlayerErrorResponse;
import com.zephsie.spring.util.PlayerNotFoundException;
import com.zephsie.spring.util.PlayerValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.StringJoiner;

@RestController
@RequestMapping("/players")
public class PlayerRestController {
    private final PlayerService playerService;

    private final ModelMapper modelMapper;

    @Autowired
    public PlayerRestController(PlayerService playerService, ModelMapper modelMapper) {
        this.playerService = playerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> getPlayerById(@PathVariable("id") Long id) {
        return playerService.get(id).map(ResponseEntity::ok).orElseThrow(() -> new PlayerNotFoundException("Player not found"));
    }

    @GetMapping(value = "/top", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Player> getTop() {
        return playerService.getTop5();
    }

    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public Player add(@Valid @RequestBody PlayerDTO playerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringJoiner stringJoiner = new StringJoiner(", ");

            bindingResult.getAllErrors().forEach(error -> stringJoiner.add(error.getDefaultMessage()));

            throw new PlayerValidationException(stringJoiner.toString());
        }

        return playerService.save(modelMapper.map(playerDTO, Player.class));
    }


    @PutMapping(value = "/{id}/win", produces = MediaType.APPLICATION_JSON_VALUE)
    public Player addWin(@PathVariable Long id) {
        return playerService.addWin(id);
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<PlayerErrorResponse> handlePlayerNotFoundException(PlayerNotFoundException e) {
        return ResponseEntity.status(404).body(new PlayerErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(PlayerValidationException.class)
    public ResponseEntity<PlayerErrorResponse> handlePlayerValidationException(PlayerValidationException e) {
        return ResponseEntity.badRequest().body(new PlayerErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<PlayerErrorResponse> handleJsonProcessingException(JsonProcessingException e) {
        return ResponseEntity.badRequest().body(new PlayerErrorResponse(e.getMessage()));
    }
}