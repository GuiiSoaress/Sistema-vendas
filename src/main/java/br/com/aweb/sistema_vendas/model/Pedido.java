package br.com.aweb.sistema_vendas.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(
            mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    // CascadeType.ALL: propaga as operações de salvar,
    // atualizar e excluir do Pedido para os ItemPedido.
    //
    // orphanRemoval = true: remove automaticamente do banco
    // os itens que deixarem de pertencer ao Pedido.
    private List<ItemPedido> itens = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;


    // Construtor personalizado solicitado
    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.dataHora = LocalDateTime.now();
        this.valorTotal = BigDecimal.ZERO;
        this.status = StatusPedido.ATIVO;
    }


    public void adicionarItem(ItemPedido item) {
        item.setPedido(this);
        this.itens.add(item);
    }


    public void calcularValorTotal() {
        this.valorTotal = itens.stream()
                .map(item -> item.getPrecoUnitario()
                        .multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }
    public Long getVersion() { return this.version; }
    public void setVersion(Long version) { this.version = version; }
    public Cliente getCliente() { return this.cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public List<ItemPedido> getItens() { return this.itens; }
    public void setItens(List<ItemPedido> itens) { this.itens = itens; }
    public LocalDateTime getDataHora() { return this.dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public BigDecimal getValorTotal() { return this.valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    public StatusPedido getStatus() { return this.status; }
    public void setStatus(StatusPedido status) { this.status = status; }

    public Pedido() {}
}