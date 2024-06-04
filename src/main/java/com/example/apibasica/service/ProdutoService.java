package com.example.apibasica.service;

import com.example.apibasica.exceptions.NaoExisteException;
import com.example.apibasica.model.DTO.ProdutoModelDTO;
import com.example.apibasica.model.DTO.ProdutoModelDTOResponse;
import com.example.apibasica.model.ProdutoModel;
import com.example.apibasica.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoModelDTO salvarProduto(ProdutoModel produtoModel) {
        ProdutoModel produtoSalvo = produtoRepository.save(produtoModel);

        return modelMapper.map(produtoSalvo, ProdutoModelDTO.class);
    }

    public ProdutoModelDTOResponse buscarTodosProdutos(int paginas, int qtdItens) {
        ProdutoModelDTOResponse produtoModelDTOResponse = new ProdutoModelDTOResponse();
        List<ProdutoModelDTO> listProdutosDTO = new ArrayList<>();
        Page<ProdutoModel> produtos = produtoRepository.findAll(PageRequest.of(paginas, qtdItens));

        if (!produtos.getContent().isEmpty()) {
            for (ProdutoModel produtoModel : produtos.getContent()) {
                listProdutosDTO.add(modelMapper.map(produtoModel, ProdutoModelDTO.class));
            }
        }

        produtoModelDTOResponse.setProdutos(listProdutosDTO);
        produtoModelDTOResponse.setTotalProdutos(produtoRepository.count()); // Conta o total de produtos

        return produtoModelDTOResponse;
    }

    public Optional<ProdutoModelDTO> buscarProduto(String registro) {
        ProdutoModel byRegistro = produtoRepository.findByRegistro(registro);

        return byRegistro != null ? Optional.of(modelMapper.map(byRegistro, ProdutoModelDTO.class)) : Optional.empty();
    }

    public Optional<ProdutoModelDTO> editarProduto(String registro, ProdutoModel produtoEditado) {
        ProdutoModel produtoSalvo = produtoRepository.findByRegistro(registro);

        if(produtoSalvo == null) {
            return Optional.empty();
        }

        BeanUtils.copyProperties(produtoEditado, produtoSalvo, "registro", "id");

        ProdutoModel map = modelMapper.map(produtoSalvo, ProdutoModel.class);

        ProdutoModel produtoModel = produtoRepository.save(map);

        return Optional.ofNullable(modelMapper.map(produtoModel, ProdutoModelDTO.class));
    }

    @Transactional
    public void excluirProduto(String registro) throws NaoExisteException {
        ProdutoModel produto = produtoRepository.findByRegistro(registro);

        if (produto == null) {
            throw new NaoExisteException("O produto informado não existe!");
        }

        produtoRepository.deleteByRegistro(registro);
    }

}
