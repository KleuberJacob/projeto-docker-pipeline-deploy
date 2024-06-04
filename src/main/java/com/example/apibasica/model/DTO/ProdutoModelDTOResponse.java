package com.example.apibasica.model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProdutoModelDTOResponse {

    private List<ProdutoModelDTO> produtos;
    private long totalProdutos;

}
