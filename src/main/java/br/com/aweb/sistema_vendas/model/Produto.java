package br.com.aweb.sistema_vendas.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity // entidade do banco de dados
@Table(name = "produtos") // nome da tabela (opcional)
@Data // gera getters and setters lombok
@NoArgsConstructor // Lombok 
@AllArgsConstructor 
@EqualsAndHashCode // gera .equals e hash code lombok

public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincremento 
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório!")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "Descrição é obrigatória!")
    @Column(nullable = false, length = 255)
    private String descricao;

    @NotNull(message = "Preço é obrigatório!")
    @PositiveOrZero(message = "O valor deve ser maior ou igual a zero!")
    @Column(nullable = false)
    private BigDecimal preco;

    @NotNull(message = "A quantidade é obrigatória!")
    @PositiveOrZero(message = "O valor deve ser maior ou igual a zero!")
    @Column(nullable = false)
    private Integer quantidadeEmEstoque;

}
