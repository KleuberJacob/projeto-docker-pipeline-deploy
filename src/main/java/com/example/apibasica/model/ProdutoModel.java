package com.example.apibasica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "produto")
@Data
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String registro;

    @NotBlank(message = "Informe o nome do produto.")
    @Column()
    private String nome;

    @NotNull()
    @Min(value = 1, message = "O valor do produto deve ser maior que 0.")
    @Column()
    private Double preco;

    @PrePersist
    private void prePersist() {
        if (this.registro == null || this.registro.isEmpty()) {
            this.registro = UUID.randomUUID().toString();
        }
    }

}
