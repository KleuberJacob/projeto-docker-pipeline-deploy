package com.example.apibasica.handler;

import com.example.apibasica.exceptions.NaoExisteException;
import com.example.apibasica.model.DTO.ObjectDefaultErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ObjectDefaultErrorDTO validaNomeProduto(MethodArgumentNotValidException exception) {
        ObjectDefaultErrorDTO objectDefaultErrorDTO = new ObjectDefaultErrorDTO();

        exception.getBindingResult().getAllErrors().forEach(err -> {
            objectDefaultErrorDTO.setTimeStamp(Instant.now());
            objectDefaultErrorDTO.setMensagem(err.getDefaultMessage());
        });
        return objectDefaultErrorDTO;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ObjectDefaultErrorDTO validaPrecoProduto(HttpMessageNotReadableException exception) {
        ObjectDefaultErrorDTO objectDefaultErrorDTO = new ObjectDefaultErrorDTO();

        objectDefaultErrorDTO.setTimeStamp(Instant.now());
        objectDefaultErrorDTO.setMensagem(exception.getLocalizedMessage());

        return objectDefaultErrorDTO;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(NaoExisteException.class)
    public ObjectDefaultErrorDTO produtoNaoEncontrado(NaoExisteException exception) {
        ObjectDefaultErrorDTO objectDefaultErrorDTO = new ObjectDefaultErrorDTO();

        objectDefaultErrorDTO.setTimeStamp(Instant.now());
        objectDefaultErrorDTO.setMensagem("Produto não encontrado!");

        return objectDefaultErrorDTO;
    }

}
