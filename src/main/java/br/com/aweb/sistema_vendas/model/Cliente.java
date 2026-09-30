package br.com.aweb.sistema_vendas.model;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity // entidade do banco de dados
@Table(name = "Clientes") // nome da tabela (opcional)
// gera getters and setters lombok
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatóri!")
    @Column(nullable = false, length = 100)
    private String nome;

    @Email
    @NotBlank(message = "Email é obrigatório!")
    @Column(unique = true, nullable = false)
    private String email;

    @CPF
    @NotBlank(message = "CPF é obrigatório!")
    @Column(unique = true, nullable = false)
    private String cpf;

    @NotBlank(message = "Telefone é obrigatório!")
    @Column(nullable = false, length = 15)
    private String telefone;

    @NotBlank(message = "Logradouro é obrigatório")
    private String logradouro;

    private String numero;

    private String complemento;

    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "UF é obrigatória")
    private String uf;

    @NotBlank(message = "CEP é obrigatório")
    private String cep;

    @jakarta.persistence.OneToMany(mappedBy = "cliente")
    private java.util.List<Pedido> pedidos = new java.util.ArrayList<>();


    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return this.nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }
    public String getCpf() { return this.cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getTelefone() { return this.telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getLogradouro() { return this.logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
    public String getNumero() { return this.numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getComplemento() { return this.complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }
    public String getBairro() { return this.bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getCidade() { return this.cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getUf() { return this.uf; }
    public void setUf(String uf) { this.uf = uf; }
    public String getCep() { return this.cep; }
    public void setCep(String cep) { this.cep = cep; }

    public Cliente() {}
}
