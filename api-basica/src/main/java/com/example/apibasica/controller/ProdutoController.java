package com.example.apibasica.controller;

import com.example.apibasica.model.ProdutoModel;
import com.example.apibasica.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping()
    public ResponseEntity<ProdutoModel> produto(@RequestBody ProdutoModel produtoModel) {
        ProdutoModel produtoModel1 = produtoService.salvarProduto(produtoModel);

        return ResponseEntity.ok().body(produtoModel1);
    }

    @GetMapping()
    public ResponseEntity<List<ProdutoModel>> produtos() {
        List<ProdutoModel> produtoModels = produtoService.buscarProdutos();

        return ResponseEntity.ok(produtoModels);
    }

}
