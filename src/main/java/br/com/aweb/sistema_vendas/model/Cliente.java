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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity // entidade do banco de dados
@Table(name = "Clientes") // nome da tabela (opcional)
@Data // gera getters and setters lombok
@NoArgsConstructor // Lombok
@AllArgsConstructor
@EqualsAndHashCode // gera .equals e hash code lombok

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

}
