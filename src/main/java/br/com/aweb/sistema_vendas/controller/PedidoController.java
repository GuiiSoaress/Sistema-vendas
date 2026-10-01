package br.com.aweb.sistema_vendas.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.aweb.sistema_vendas.model.Pedido;
import br.com.aweb.sistema_vendas.model.StatusPedido;
import br.com.aweb.sistema_vendas.service.ClienteService;
import br.com.aweb.sistema_vendas.service.PedidoService;
import br.com.aweb.sistema_vendas.service.ProdutoService;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    public PedidoController(PedidoService pedidoService, ClienteService clienteService, ProdutoService produtoService) {
        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }

    // Listar pedidos
    @GetMapping
    public ModelAndView listarPedidos() {
        return new ModelAndView("pedido/list", Map.of("pedidos", pedidoService.listarTodos()));
    }

    // Formulário de novo pedido
    @GetMapping("/novo")
    public ModelAndView novoPedidoForm() {
        return new ModelAndView("pedido/form", Map.of("clientes", clienteService.listarTodos()));
    }

    // Criar pedido
    @PostMapping("/novo")
    public String criarPedido(@RequestParam Long clienteId, RedirectAttributes ra) {
        var optionalCliente = clienteService.buscarPorId(clienteId);
        if (optionalCliente.isEmpty()) {
            ra.addFlashAttribute("erro", "Cliente não encontrado.");
            return "redirect:/pedidos/novo";
        }
        Pedido pedido = pedidoService.criarPedido(optionalCliente.get());
        return "redirect:/pedidos/edit/" + pedido.getId();
    }

    // Formulário de edição
    @GetMapping("/edit/{id}")
    public Object editarPedidoForm(@PathVariable Long id, RedirectAttributes ra) {
        var optionalPedido = pedidoService.buscarPorId(id);
        if (optionalPedido.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Pedido pedido = optionalPedido.get();
        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            ra.addFlashAttribute("erro", "Pedido cancelado não pode ser editado.");
            return "redirect:/pedidos";
        }
        return new ModelAndView("pedido/edit", Map.of("pedido", pedido, "produtos", produtoService.listarTodos()));
    }

    // Adicionar item
    @PostMapping("/{pedidoId}/adicionar-item")
    public String adicionarItem(@PathVariable Long pedidoId, @RequestParam Long produtoId,
                                @RequestParam Integer quantidade, RedirectAttributes ra) {
        try {
            pedidoService.adicionarItem(pedidoId, produtoId, quantidade);
            ra.addFlashAttribute("sucesso", "Produto adicionado ao pedido.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/pedidos/edit/" + pedidoId;
    }

    // Remover item
    @PostMapping("/{pedidoId}/remover-item/{itemId}")
    public String removerItem(@PathVariable Long pedidoId, @PathVariable Long itemId, RedirectAttributes ra) {
        try {
            pedidoService.removerItem(pedidoId, itemId);
            ra.addFlashAttribute("sucesso", "Produto removido do pedido.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/pedidos/edit/" + pedidoId;
    }

    // Confirmação de cancelamento
    @GetMapping("/cancelar/{id}")
    public ModelAndView cancelarPedidoForm(@PathVariable Long id) {
        var optionalPedido = pedidoService.buscarPorId(id);
        if (optionalPedido.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("pedido/cancelar", Map.of("pedido", optionalPedido.get()));
    }

    // Cancelar
    @PostMapping("/cancelar/{id}")
    public String cancelarPedido(@PathVariable Long id, RedirectAttributes ra) {
        try {
            pedidoService.cancelarPedido(id);
            ra.addFlashAttribute("sucesso", "Pedido cancelado e produtos devolvidos ao estoque.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/pedidos";
    }

    // Detalhes
    @GetMapping("/detalhes/{id}")
    public ModelAndView detalhesPedido(@PathVariable Long id) {
        var optionalPedido = pedidoService.buscarPorId(id);
        if (optionalPedido.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("pedido/detalhes", Map.of("pedido", optionalPedido.get()));
    }
}
