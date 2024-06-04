package com.example.apibasica.controller;

import com.example.apibasica.exceptions.NaoExisteException;
import com.example.apibasica.model.DTO.ProdutoModelDTO;
import com.example.apibasica.model.DTO.ProdutoModelDTOResponse;
import com.example.apibasica.model.ProdutoModel;
import com.example.apibasica.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping()
    public ResponseEntity<ProdutoModelDTO> produto(@RequestBody @Valid ProdutoModel produtoModel) {
            return ResponseEntity.ok(produtoService.salvarProduto(produtoModel));
    }

    @GetMapping()
    public ResponseEntity<ProdutoModelDTOResponse> produtos(@RequestParam int pagina, @RequestParam int qtdItens) {
        ProdutoModelDTOResponse produtos = produtoService.buscarTodosProdutos(pagina, qtdItens);

        return produtos.getProdutos().isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(produtos);
    }

    @GetMapping("/{registro}")
    public ResponseEntity<ProdutoModelDTO> produto(@PathVariable String registro) {
        Optional<ProdutoModelDTO> produto = produtoService.buscarProduto(registro);

        return produto.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(produto.get());
    }

    @PutMapping("/editar/{registro}")
    public ResponseEntity<ProdutoModelDTO> editarProduto(@PathVariable String registro, @RequestBody @Valid ProdutoModel produtoModel) {
        Optional<ProdutoModelDTO> produto = produtoService.editarProduto(registro, produtoModel);

        return produto.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(produto.get());
    }

    @DeleteMapping("/excluir/{registro}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirProduto(@PathVariable String registro) throws NaoExisteException {
        produtoService.excluirProduto(registro);
    }

}
