package com.zephsie.spring.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PlayerDTO {
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 16, message = "Name must be between 2 and 30 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
