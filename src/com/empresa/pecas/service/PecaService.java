package com.empresa.pecas.service;

import com.empresa.pecas.model.Peca;
import com.empresa.pecas.model.Historico;
import com.empresa.pecas.model.PedidoPendente;
import com.empresa.pecas.repository.PecaRepository;
import com.empresa.pecas.repository.HistoricoRepository;
import com.empresa.pecas.repository.PedidoPendenteRepository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class PecaService {
    private PecaRepository pecaRepository;
    private HistoricoRepository historicoRepository;
    private PedidoPendenteRepository pedidoRepository;

    public PecaService() {
        this.pecaRepository = new PecaRepository();
        this.historicoRepository = new HistoricoRepository();
        this.pedidoRepository = new PedidoPendenteRepository();
    }

    public List<Peca> listarTodasPecas() {
        return pecaRepository.findAll();
    }

    public Optional<Peca> buscarPecaPorId(int id) {
        return pecaRepository.findById(id);
    }

    public String registrarSaida(int pecaId, int quantidade, String responsavel, String destino, String cliente) {
        Optional<Peca> pecaOpt = pecaRepository.findById(pecaId);

        if (pecaOpt.isEmpty()) {
            return "ERRO: Peça não encontrada.";
        }

        Peca peca = pecaOpt.get();

        if (peca.getQuantidade() >= quantidade) {
            peca.setQuantidade(peca.getQuantidade() - quantidade);
            pecaRepository.salvar(peca);

            Historico historico = new Historico(
                    0, pecaId, peca.getNome(), quantidade, responsavel, destino, "SAIDA"
            );
            historicoRepository.salvar(historico);

            return "SUCESSO: Saída registrada com sucesso!";

        } else {
            PedidoPendente pedido = new PedidoPendente(
                    0, pecaId, peca.getNome(), quantidade, cliente, responsavel,
                    "Estoque insuficiente. Disponível: " + peca.getQuantidade()
            );
            pedidoRepository.salvar(pedido);

            return "PENDENTE: Estoque insuficiente. Pedido criado na lista de espera (ID: " + pedido.getId() + ")";
        }
    }

    public boolean registrarEntrada(int pecaId, int quantidade, String responsavel, String origem) {
        Optional<Peca> pecaOpt = pecaRepository.findById(pecaId);

        if (pecaOpt.isEmpty()) {
            return false;
        }

        Peca peca = pecaOpt.get();
        peca.setQuantidade(peca.getQuantidade() + quantidade);
        pecaRepository.salvar(peca);

        Historico historico = new Historico(
                0, pecaId, peca.getNome(), quantidade, responsavel, origem, "ENTRADA"
        );
        historicoRepository.salvar(historico);

        return true;
    }

    public List<Historico> obterHistorico() {
        return historicoRepository.findAll();
    }

    public List<Historico> obterUltimasMovimentacoes(int quantidade) {
        return historicoRepository.findUltimos(quantidade);
    }

    public List<Historico> listarSaidas() {
        return historicoRepository.findSaidas();
    }

    public Optional<Historico> buscarHistoricoPorId(int id) {
        return historicoRepository.findById(id);
    }

    public boolean editarSaida(int historicoId, int novaQuantidade, String novoResponsavel, String novoDestino) {
        Optional<Historico> historicoOpt = historicoRepository.findById(historicoId);

        if (historicoOpt.isEmpty() || !"SAIDA".equals(historicoOpt.get().getTipoMovimentacao())) {
            return false;
        }

        Historico historico = historicoOpt.get();
        Optional<Peca> pecaOpt = pecaRepository.findById(historico.getPecaId());

        if (pecaOpt.isEmpty()) {
            return false;
        }

        Peca peca = pecaOpt.get();
        int diferencaQuantidade = novaQuantidade - historico.getQuantidade();

        if (peca.getQuantidade() - diferencaQuantidade < 0) {
            return false;
        }

        peca.setQuantidade(peca.getQuantidade() - diferencaQuantidade);
        pecaRepository.salvar(peca);

        historico.setQuantidade(novaQuantidade);
        historico.setResponsavel(novoResponsavel);
        historico.setDestino(novoDestino);

        return historicoRepository.atualizar(historico);
    }

    public boolean excluirSaida(int historicoId) {
        Optional<Historico> historicoOpt = historicoRepository.findById(historicoId);

        if (historicoOpt.isEmpty() || !"SAIDA".equals(historicoOpt.get().getTipoMovimentacao())) {
            return false;
        }

        Historico historico = historicoOpt.get();
        Optional<Peca> pecaOpt = pecaRepository.findById(historico.getPecaId());

        if (pecaOpt.isEmpty()) {
            return false;
        }

        Peca peca = pecaOpt.get();
        peca.setQuantidade(peca.getQuantidade() + historico.getQuantidade());
        pecaRepository.salvar(peca);

        return historicoRepository.delete(historicoId);
    }

    public Peca cadastrarPeca(String nome, String descricao, int quantidade, String localizacao) {
        Peca novaPeca = new Peca(0, nome, descricao, quantidade, localizacao);
        return pecaRepository.salvar(novaPeca);
    }

    public boolean atualizarPeca(int id, String nome, String descricao, int quantidade, String localizacao) {
        Optional<Peca> pecaOpt = pecaRepository.findById(id);

        if (pecaOpt.isEmpty()) {
            return false;
        }

        Peca peca = pecaOpt.get();
        peca.setNome(nome);
        peca.setDescricao(descricao);
        peca.setQuantidade(quantidade);
        peca.setLocalizacao(localizacao);

        pecaRepository.salvar(peca);
        return true;
    }

    public boolean excluirPeca(int id) {
        List<Historico> movimentacoes = historicoRepository.findByPecaId(id);
        if (!movimentacoes.isEmpty()) {
            return false;
        }

        return pecaRepository.delete(id);
    }

    public List<Peca> buscarPecaPorNome(String nome) {
        return pecaRepository.findAll().stream()
                .filter(p -> p.getNome().toLowerCase().contains(nome.toLowerCase()))
                .toList();
    }

    public boolean adicionarEstoque(int pecaId, int quantidade, String responsavel, String origem) {
        Optional<Peca> pecaOpt = pecaRepository.findById(pecaId);

        if (pecaOpt.isEmpty() || quantidade <= 0) {
            return false;
        }

        Peca peca = pecaOpt.get();
        int novaQuantidade = peca.getQuantidade() + quantidade;
        peca.setQuantidade(novaQuantidade);
        pecaRepository.salvar(peca);

        Historico historico = new Historico(
                0, pecaId, peca.getNome(), quantidade, responsavel, origem, "ENTRADA"
        );
        historicoRepository.salvar(historico);

        return true;
    }

    public boolean criarPedidoPendente(int pecaId, int quantidade, String cliente, String responsavel, String observacoes) {
        Optional<Peca> pecaOpt = pecaRepository.findById(pecaId);

        if (pecaOpt.isEmpty()) {
            return false;
        }

        Peca peca = pecaOpt.get();
        PedidoPendente pedido = new PedidoPendente(
                0, pecaId, peca.getNome(), quantidade, cliente, responsavel, observacoes
        );
        pedidoRepository.salvar(pedido);

        return true;
    }

    public boolean atenderPedidoPendente(int pedidoId, String destino) {
        Optional<PedidoPendente> pedidoOpt = pedidoRepository.findById(pedidoId);

        if (pedidoOpt.isEmpty() || !"PENDENTE".equals(pedidoOpt.get().getStatus())) {
            return false;
        }

        PedidoPendente pedido = pedidoOpt.get();
        Optional<Peca> pecaOpt = pecaRepository.findById(pedido.getPecaId());

        if (pecaOpt.isEmpty() || pecaOpt.get().getQuantidade() < pedido.getQuantidade()) {
            return false;
        }

        Peca peca = pecaOpt.get();
        peca.setQuantidade(peca.getQuantidade() - pedido.getQuantidade());
        pecaRepository.salvar(peca);

        Historico historico = new Historico(
                0, pedido.getPecaId(), pedido.getNomePeca(), pedido.getQuantidade(),
                pedido.getResponsavel(), destino, "SAIDA"
        );
        historicoRepository.salvar(historico);

        pedido.setStatus("ATENDIDO");
        pedidoRepository.atualizar(pedido);

        return true;
    }

    public List<PedidoPendente> listarPedidosPendentes() {
        return pedidoRepository.findPedidosPendentes();
    }

    public boolean cancelarPedidoPendente(int pedidoId) {
        Optional<PedidoPendente> pedidoOpt = pedidoRepository.findById(pedidoId);

        if (pedidoOpt.isEmpty() || !"PENDENTE".equals(pedidoOpt.get().getStatus())) {
            return false;
        }

        PedidoPendente pedido = pedidoOpt.get();
        pedido.setStatus("CANCELADO");
        pedidoRepository.atualizar(pedido);

        return true;
    }

    public List<PedidoPendente> verificarPedidosAtendiveis(int pecaId) {
        Optional<Peca> pecaOpt = pecaRepository.findById(pecaId);

        if (pecaOpt.isEmpty()) {
            return List.of();
        }

        Peca peca = pecaOpt.get();
        return pedidoRepository.findByPecaId(pecaId).stream()
                .filter(pedido -> peca.getQuantidade() >= pedido.getQuantidade())
                .toList();
    }

    public List<Historico> listarVendasPeriodo16h() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicioPeriodo;
        LocalDateTime fimPeriodo;

        if (agora.getHour() < 16) {
            inicioPeriodo = LocalDateTime.of(agora.minusDays(1).getYear(),
                    agora.minusDays(1).getMonth(),
                    agora.minusDays(1).getDayOfMonth(), 16, 0);
            fimPeriodo = LocalDateTime.of(agora.getYear(), agora.getMonth(), agora.getDayOfMonth(), 16, 0);
        } else {
            inicioPeriodo = LocalDateTime.of(agora.getYear(), agora.getMonth(), agora.getDayOfMonth(), 16, 0);
            fimPeriodo = LocalDateTime.of(agora.plusDays(1).getYear(),
                    agora.plusDays(1).getMonth(),
                    agora.plusDays(1).getDayOfMonth(), 16, 0);
        }

        return historicoRepository.findAll().stream()
                .filter(h -> "SAIDA".equals(h.getTipoMovimentacao()))
                .filter(h -> h.getDataMovimentacao().isAfter(inicioPeriodo) &&
                        h.getDataMovimentacao().isBefore(fimPeriodo))
                .filter(h -> isPecaListaPedidos(h.getNomePeca()))
                .toList();
    }

    private boolean isPecaListaPedidos(String nomePeca) {
        String nomeLower = nomePeca.toLowerCase();
        return nomeLower.contains("bateria") ||
                nomeLower.contains("tela") ||
                nomeLower.contains("frontal") ||
                nomeLower.contains("tampa");
    }

    public String getInfoPeriodo16h() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicioPeriodo;
        LocalDateTime fimPeriodo;

        if (agora.getHour() < 16) {
            inicioPeriodo = LocalDateTime.of(agora.minusDays(1).getYear(),
                    agora.minusDays(1).getMonth(),
                    agora.minusDays(1).getDayOfMonth(), 16, 0);
            fimPeriodo = LocalDateTime.of(agora.getYear(), agora.getMonth(), agora.getDayOfMonth(), 16, 0);
        } else {
            inicioPeriodo = LocalDateTime.of(agora.getYear(), agora.getMonth(), agora.getDayOfMonth(), 16, 0);
            fimPeriodo = LocalDateTime.of(agora.plusDays(1).getYear(),
                    agora.plusDays(1).getMonth(),
                    agora.plusDays(1).getDayOfMonth(), 16, 0);
        }

        return String.format("Período da lista: %s até %s",
                inicioPeriodo.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                fimPeriodo.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }

    public Map<String, Integer> gerarListaConsolidada() {
        List<Historico> vendasPeriodo = listarVendasPeriodo16h();
        Map<String, Integer> consolidado = new HashMap<>();

        for (Historico venda : vendasPeriodo) {
            String nomePeca = venda.getNomePeca();
            if (isPecaListaPedidos(nomePeca)) {
                consolidado.put(nomePeca, consolidado.getOrDefault(nomePeca, 0) + venda.getQuantidade());
            }
        }

        return consolidado;
    }
}