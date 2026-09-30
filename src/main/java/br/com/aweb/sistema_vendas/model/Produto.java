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

@Entity // entidade do banco de dados
@Table(name = "produtos") // nome da tabela (opcional)
// gera getters and setters lombok
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


    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return this.nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return this.descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getPreco() { return this.preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public Integer getQuantidadeEmEstoque() { return this.quantidadeEmEstoque; }
    public void setQuantidadeEmEstoque(Integer quantidadeEmEstoque) { this.quantidadeEmEstoque = quantidadeEmEstoque; }

    public Produto() {}
}
