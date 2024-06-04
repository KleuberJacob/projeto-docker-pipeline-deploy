package com.example.apibasica.model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ObjectDefaultErrorDTO {

    private Instant timeStamp;
    private String mensagem;

    public ObjectDefaultErrorDTO() {}

    public ObjectDefaultErrorDTO(String mensagem) {
        this.mensagem = mensagem;
    }

}
