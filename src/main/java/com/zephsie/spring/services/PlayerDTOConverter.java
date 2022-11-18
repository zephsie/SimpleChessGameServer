package com.zephsie.spring.services;

import com.zephsie.spring.dto.PlayerDTO;
import com.zephsie.spring.models.Player;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerDTOConverter {
    private final ModelMapper modelMapper;

    @Autowired
    public PlayerDTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Player convertToEntity(PlayerDTO playerDTO) {
        return modelMapper.map(playerDTO, Player.class);
    }
}