package br.com.aweb.sistema_vendas.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.aweb.sistema_vendas.model.StatusPedido;
import br.com.aweb.sistema_vendas.service.ClienteService;
import br.com.aweb.sistema_vendas.service.PedidoService;
import br.com.aweb.sistema_vendas.service.ProdutoService;

@Controller
public class HomeController {

    private final ClienteService clienteService;
    private final ProdutoService produtoService;
    private final PedidoService pedidoService;

    public HomeController(ClienteService clienteService, ProdutoService produtoService, PedidoService pedidoService) {
        this.clienteService = clienteService;
        this.produtoService = produtoService;
        this.pedidoService = pedidoService;
    }

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index", Map.of(
                "totalClientes", clienteService.listarTodos().size(),
                "totalProdutos", produtoService.listarTodos().size(),
                "pedidosAtivos", pedidoService.listarPorStatus(StatusPedido.ATIVO).size()));
    }
}
