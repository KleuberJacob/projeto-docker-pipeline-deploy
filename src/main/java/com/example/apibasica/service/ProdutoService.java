package com.example.apibasica.service;


import com.example.apibasica.model.ProdutoModel;
import com.example.apibasica.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoModel salvarProduto(ProdutoModel produtoModel) {
        ProdutoModel save = produtoRepository.save(produtoModel);
        return save;
    }

    public List<ProdutoModel> buscarProdutos() {
        List<ProdutoModel> all = produtoRepository.findAll();

        return all;
    }
}
