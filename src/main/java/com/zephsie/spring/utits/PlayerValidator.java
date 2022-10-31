package com.zephsie.spring.utits;

import com.zephsie.spring.models.Player;
import com.zephsie.spring.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PlayerValidator implements Validator {
    private final PlayerService playerService;

    @Autowired
    public PlayerValidator(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Player.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Player player = (Player) target;

        if (playerService.get(player.getName()).isPresent()) {
            errors.rejectValue("name", "", "This name is already taken");
        }
    }
}