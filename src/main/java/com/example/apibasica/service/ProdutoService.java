package com.example.apibasica.service;

import com.example.apibasica.exceptions.NaoExisteException;
import com.example.apibasica.model.DTO.ProdutoModelDTO;
import com.example.apibasica.model.DTO.ProdutoModelDTOResponse;
import com.example.apibasica.model.ProdutoModel;
import com.example.apibasica.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private static Logger log = LoggerFactory.getLogger(ProdutoService.class.getName());

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoModelDTO salvarProduto(ProdutoModel produtoModel) {
        log.info("Salvando produto: {}", produtoModel.getNome());
        ProdutoModel produtoSalvo = produtoRepository.save(produtoModel);
        log.info("Produto salvo: {}", produtoModel.getNome());
        return modelMapper.map(produtoSalvo, ProdutoModelDTO.class);
    }

    public ProdutoModelDTOResponse buscarTodosProdutos(int paginas, int qtdItens) {
        ProdutoModelDTOResponse produtoModelDTOResponse = new ProdutoModelDTOResponse();
        List<ProdutoModelDTO> listProdutosDTO = new ArrayList<>();
        log.info("Buscando todos produtos");
        Page<ProdutoModel> produtos = produtoRepository.findAll(PageRequest.of(paginas, qtdItens));

        if (!produtos.getContent().isEmpty()) {
            log.info("Iniciada conversão de produtos para DTO.");
            for (ProdutoModel produtoModel : produtos.getContent()) {
                listProdutosDTO.add(modelMapper.map(produtoModel, ProdutoModelDTO.class));
            }
            log.info("Finalizada conversão de produtos para DTO.");
        }

        produtoModelDTOResponse.setProdutos(listProdutosDTO);
        log.info("Retornando lista de produtos.");
        produtoModelDTOResponse.setTotalProdutos(produtoRepository.count());
        log.info("Retornando quantidade total de itens cadastrados: {}.", produtoRepository.count());
        return produtoModelDTOResponse;
    }

    public Optional<ProdutoModelDTO> buscarProduto(String registro) {
        log.info("Buscando produto de registro: {}", registro);
        ProdutoModel byRegistro = produtoRepository.findByRegistro(registro);

        log.info("Retornando produto de registro: {}.", registro);
        return byRegistro != null ? Optional.of(modelMapper.map(byRegistro, ProdutoModelDTO.class)) : Optional.empty();
    }

    public Optional<ProdutoModelDTO> editarProduto(String registro, ProdutoModel produtoEditado) {
        log.info("Buscando produto registro: {}.", registro);
        ProdutoModel produtoSalvo = produtoRepository.findByRegistro(registro);

        if(produtoSalvo == null) {
            log.info("Produto registro: {} não encontrado.", registro);
            return Optional.empty();
        }
        log.info("Produto registro: {} encontrado.", registro);
        BeanUtils.copyProperties(produtoEditado, produtoSalvo, "registro", "id");
        log.info("Editando produto registro: {}.", registro);
        ProdutoModel map = modelMapper.map(produtoSalvo, ProdutoModel.class);

        ProdutoModel produtoModel = produtoRepository.save(map);
        log.info("Retornando produto registro: {}.", registro);
        return Optional.ofNullable(modelMapper.map(produtoModel, ProdutoModelDTO.class));
    }

    @Transactional
    public void excluirProduto(String registro) throws NaoExisteException {
        log.info("Excluindo produto registro: {}.", registro);
        ProdutoModel produto = produtoRepository.findByRegistro(registro);

        if (produto == null) {
            log.info("Produto registro: {} não existe.", registro);
            throw new NaoExisteException("O produto informado não existe!");
        }
        log.info("Produto excluido.");
        produtoRepository.deleteByRegistro(registro);
    }

}
