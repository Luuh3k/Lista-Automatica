package com.empresa.pecas;

import com.empresa.pecas.model.Peca;
import com.empresa.pecas.model.Historico;
import com.empresa.pecas.model.PedidoPendente;
import com.empresa.pecas.service.PecaService;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    private static PecaService pecaService = new PecaService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== SISTEMA LOJA DE PEÇAS PARA CELULAR ===");
        System.out.println("📱 Baterias • Telas • Tampas 📱");
        System.out.println("Período das listas: 16h até 16h do dia seguinte");

        while (true) {
            exibirMenu();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    listarPecasPorCategoria();
                    break;
                case 2:
                    gerenciarPecas();
                    break;
                case 3:
                    registrarSaida();
                    break;
                case 4:
                    registrarEntrada();
                    break;
                case 5:
                    exibirHistorico();
                    break;
                case 6:
                    exibirUltimasMovimentacoes();
                    break;
                case 7:
                    gerenciarSaidas();
                    break;
                case 8:
                    gerenciarPedidosPendentes();
                    break;
                case 9:
                    gerarListaPeriodo16h();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }

            System.out.println("\nPressione Enter para continuar...");
            scanner.nextLine();
        }
    }

    private static void exibirMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Listar Peças por Categoria");
        System.out.println("2. Gerenciar Peças");
        System.out.println("3. Registrar Saída/Venda");
        System.out.println("4. Registrar Entrada/Compra");
        System.out.println("5. Ver Histórico Completo");
        System.out.println("6. Últimas Movimentações");
        System.out.println("7. Gerenciar Saídas");
        System.out.println("8. Pedidos Pendentes");
        System.out.println("9. Lista Período 16h-16h");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");

        System.out.println("\n" + pecaService.getInfoPeriodo16h());
        System.out.println("Horário atual: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }

    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void listarPecasPorCategoria() {
        System.out.println("\n=== ESTOQUE - PEÇAS PARA CELULAR ===");
        List<Peca> pecas = pecaService.listarTodasPecas();

        if (pecas.isEmpty()) {
            System.out.println("Nenhuma peça cadastrada.");
            return;
        }

        Map<String, List<Peca>> categorias = new HashMap<>();
        categorias.put("🔋 BATERIAS", new ArrayList<>());
        categorias.put("📱 TELAS/FRONTAIS", new ArrayList<>());
        categorias.put("🎯 TAMPAS", new ArrayList<>());
        categorias.put("📦 OUTROS", new ArrayList<>());

        for (Peca peca : pecas) {
            String nome = peca.getNome().toLowerCase();
            if (nome.contains("bateria")) {
                categorias.get("🔋 BATERIAS").add(peca);
            } else if (nome.contains("frontal") || nome.contains("tela")) {
                categorias.get("📱 TELAS/FRONTAIS").add(peca);
            } else if (nome.contains("tampa")) {
                categorias.get("🎯 TAMPAS").add(peca);
            } else {
                categorias.get("📦 OUTROS").add(peca);
            }
        }

        for (Map.Entry<String, List<Peca>> categoria : categorias.entrySet()) {
            if (!categoria.getValue().isEmpty()) {
                System.out.println("\n" + categoria.getKey() + ":");
                for (Peca peca : categoria.getValue()) {
                    System.out.printf("   ID: %d | %s | Estoque: %d | Local: %s%n",
                            peca.getId(), peca.getNome(), peca.getQuantidade(), peca.getLocalizacao());
                }
            }
        }

        System.out.println("\n=== RESUMO DO ESTOQUE ===");
        System.out.printf("Total de peças: %d | Tipos diferentes: %d%n",
                pecas.stream().mapToInt(Peca::getQuantidade).sum(),
                pecas.size());

        System.out.println("\n💡 LISTA DE PEDIDOS: Inclui apenas Baterias, Telas e Tampas");
    }

    private static void registrarSaida() {
        System.out.println("\n=== REGISTRAR VENDA ===");
        listarPecasPorCategoria();

        System.out.print("\nID da peça: ");
        int pecaId = lerOpcao();

        Optional<Peca> pecaOpt = pecaService.buscarPecaPorId(pecaId);
        if (pecaOpt.isPresent()) {
            Peca peca = pecaOpt.get();
            System.out.println("Peça selecionada: " + peca.getNome());
            System.out.println("Estoque atual: " + peca.getQuantidade());
        }

        System.out.print("Quantidade: ");
        int quantidade = lerOpcao();

        System.out.print("Vendedor: ");
        String responsavel = scanner.nextLine();

        System.out.print("Cliente: ");
        String cliente = scanner.nextLine();

        System.out.print("Tipo de venda: ");
        String tipoVenda = scanner.nextLine();

        String resultado = pecaService.registrarSaida(pecaId, quantidade, responsavel, tipoVenda, cliente);

        System.out.println("\n" + resultado);

        if (resultado.startsWith("PENDENTE")) {
            System.out.println("⚠️  Pedido adicionado à lista de espera por falta de estoque.");
        }
    }

    private static void registrarEntrada() {
        System.out.println("\n=== REGISTRAR ENTRADA/COMPRA ===");
        listarPecasPorCategoria();

        System.out.print("\nID da peça: ");
        int pecaId = lerOpcao();

        System.out.print("Quantidade: ");
        int quantidade = lerOpcao();

        System.out.print("Responsável: ");
        String responsavel = scanner.nextLine();

        System.out.print("Fornecedor: ");
        String fornecedor = scanner.nextLine();

        boolean sucesso = pecaService.registrarEntrada(pecaId, quantidade, responsavel, fornecedor);

        if (sucesso) {
            System.out.println("✅ Entrada registrada com sucesso!");
        } else {
            System.out.println("❌ Erro ao registrar entrada. Verifique o ID da peça.");
        }
    }

    private static void exibirHistorico() {
        System.out.println("\n=== HISTÓRICO COMPLETO ===");
        List<Historico> historico = pecaService.obterHistorico();

        if (historico.isEmpty()) {
            System.out.println("Nenhuma movimentação registrada.");
            return;
        }

        for (Historico registro : historico) {
            System.out.println(registro);
        }
    }

    private static void exibirUltimasMovimentacoes() {
        System.out.println("\n=== ÚLTIMAS 10 MOVIMENTAÇÕES ===");
        List<Historico> ultimas = pecaService.obterUltimasMovimentacoes(10);

        if (ultimas.isEmpty()) {
            System.out.println("Nenhuma movimentação registrada.");
            return;
        }

        for (Historico registro : ultimas) {
            System.out.println(registro);
        }
    }

    private static void gerenciarSaidas() {
        while (true) {
            System.out.println("\n=== GERENCIAR SAÍDAS ===");
            System.out.println("1. Listar Saídas");
            System.out.println("2. Editar Saída");
            System.out.println("3. Excluir Saída");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    listarSaidas();
                    break;
                case 2:
                    editarSaida();
                    break;
                case 3:
                    excluirSaida();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void listarSaidas() {
        System.out.println("\n=== LISTA DE SAÍDAS ===");
        List<Historico> saidas = pecaService.listarSaidas();

        if (saidas.isEmpty()) {
            System.out.println("Nenhuma saída registrada.");
            return;
        }

        for (Historico saida : saidas) {
            System.out.println(saida);
        }
    }

    private static void editarSaida() {
        System.out.println("\n=== EDITAR SAÍDA ===");
        listarSaidas();

        System.out.print("\nID do registro de saída: ");
        int historicoId = lerOpcao();

        Optional<Historico> historicoOpt = pecaService.buscarHistoricoPorId(historicoId);

        if (historicoOpt.isEmpty()) {
            System.out.println("Registro não encontrado!");
            return;
        }

        Historico historico = historicoOpt.get();

        if (!"SAIDA".equals(historico.getTipoMovimentacao())) {
            System.out.println("Este registro não é uma saída!");
            return;
        }

        System.out.println("Registro atual:");
        System.out.println(historico);

        System.out.print("\nNova quantidade (atual: " + historico.getQuantidade() + "): ");
        int novaQuantidade = lerOpcao();

        System.out.print("Novo responsável (atual: " + historico.getResponsavel() + "): ");
        String novoResponsavel = scanner.nextLine();

        System.out.print("Novo destino (atual: " + historico.getDestino() + "): ");
        String novoDestino = scanner.nextLine();

        boolean sucesso = pecaService.editarSaida(historicoId, novaQuantidade, novoResponsavel, novoDestino);

        if (sucesso) {
            System.out.println("Saída editada com sucesso!");
        } else {
            System.out.println("Erro ao editar saída. Verifique a quantidade disponível.");
        }
    }

    private static void excluirSaida() {
        System.out.println("\n=== EXCLUIR SAÍDA ===");
        listarSaidas();

        System.out.print("\nID do registro de saída: ");
        int historicoId = lerOpcao();

        Optional<Historico> historicoOpt = pecaService.buscarHistoricoPorId(historicoId);

        if (historicoOpt.isEmpty()) {
            System.out.println("Registro não encontrado!");
            return;
        }

        Historico historico = historicoOpt.get();

        if (!"SAIDA".equals(historico.getTipoMovimentacao())) {
            System.out.println("Este registro não é uma saída!");
            return;
        }

        System.out.println("Registro a ser excluído:");
        System.out.println(historico);

        System.out.print("\nTem certeza que deseja excluir esta saída? (S/N): ");
        String confirmacao = scanner.nextLine();

        if ("S".equalsIgnoreCase(confirmacao)) {
            boolean sucesso = pecaService.excluirSaida(historicoId);

            if (sucesso) {
                System.out.println("Saída excluída com sucesso! O estoque foi atualizado.");
            } else {
                System.out.println("Erro ao excluir saída.");
            }
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

    private static void gerenciarPecas() {
        while (true) {
            System.out.println("\n=== GERENCIAR PEÇAS ===");
            System.out.println("1. Cadastrar Nova Peça");
            System.out.println("2. Editar Peça");
            System.out.println("3. Excluir Peça");
            System.out.println("4. Adicionar Estoque");
            System.out.println("5. Buscar Peça por Nome");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    cadastrarPeca();
                    break;
                case 2:
                    editarPeca();
                    break;
                case 3:
                    excluirPeca();
                    break;
                case 4:
                    adicionarEstoque();
                    break;
                case 5:
                    buscarPecaPorNome();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarPeca() {
        System.out.println("\n=== CADASTRAR NOVA PEÇA ===");

        System.out.print("Nome da peça: ");
        String nome = scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.print("Quantidade inicial: ");
        int quantidade = lerOpcao();

        System.out.print("Localização: ");
        String localizacao = scanner.nextLine();

        Peca novaPeca = pecaService.cadastrarPeca(nome, descricao, quantidade, localizacao);

        System.out.println("Peça cadastrada com sucesso!");
        System.out.println("ID: " + novaPeca.getId() + " | " + novaPeca);
    }

    private static void editarPeca() {
        System.out.println("\n=== EDITAR PEÇA ===");
        listarPecasPorCategoria();

        System.out.print("\nID da peça: ");
        int id = lerOpcao();

        Optional<Peca> pecaOpt = pecaService.buscarPecaPorId(id);

        if (pecaOpt.isEmpty()) {
            System.out.println("Peça não encontrada!");
            return;
        }

        Peca peca = pecaOpt.get();

        System.out.println("Peça atual:");
        System.out.println("Nome: " + peca.getNome());
        System.out.println("Descrição: " + peca.getDescricao());
        System.out.println("Quantidade: " + peca.getQuantidade());
        System.out.println("Localização: " + peca.getLocalizacao());

        System.out.print("\nNovo nome (" + peca.getNome() + "): ");
        String novoNome = scanner.nextLine();
        if (novoNome.trim().isEmpty()) novoNome = peca.getNome();

        System.out.print("Nova descrição (" + peca.getDescricao() + "): ");
        String novaDescricao = scanner.nextLine();
        if (novaDescricao.trim().isEmpty()) novaDescricao = peca.getDescricao();

        System.out.print("Nova quantidade (" + peca.getQuantidade() + "): ");
        int novaQuantidade = lerOpcao();
        if (novaQuantidade <= 0) novaQuantidade = peca.getQuantidade();

        System.out.print("Nova localização (" + peca.getLocalizacao() + "): ");
        String novaLocalizacao = scanner.nextLine();
        if (novaLocalizacao.trim().isEmpty()) novaLocalizacao = peca.getLocalizacao();

        boolean sucesso = pecaService.atualizarPeca(id, novoNome, novaDescricao, novaQuantidade, novaLocalizacao);

        if (sucesso) {
            System.out.println("Peça atualizada com sucesso!");
        } else {
            System.out.println("Erro ao atualizar peça.");
        }
    }

    private static void excluirPeca() {
        System.out.println("\n=== EXCLUIR PEÇA ===");
        listarPecasPorCategoria();

        System.out.print("\nID da peça: ");
        int id = lerOpcao();

        Optional<Peca> pecaOpt = pecaService.buscarPecaPorId(id);

        if (pecaOpt.isEmpty()) {
            System.out.println("Peça não encontrada!");
            return;
        }

        System.out.println("Peça a ser excluída:");
        System.out.println(pecaOpt.get());

        System.out.print("\nTem certeza que deseja excluir esta peça? (S/N): ");
        String confirmacao = scanner.nextLine();

        if ("S".equalsIgnoreCase(confirmacao)) {
            boolean sucesso = pecaService.excluirPeca(id);

            if (sucesso) {
                System.out.println("Peça excluída com sucesso!");
            } else {
                System.out.println("Não é possível excluir a peça. Ela possui movimentações no histórico.");
            }
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

    private static void adicionarEstoque() {
        System.out.println("\n=== ADICIONAR ESTOQUE ===");
        listarPecasPorCategoria();

        System.out.print("\nID da peça: ");
        int pecaId = lerOpcao();

        System.out.print("Quantidade a adicionar: ");
        int quantidade = lerOpcao();

        System.out.print("Responsável: ");
        String responsavel = scanner.nextLine();

        System.out.print("Origem: ");
        String origem = scanner.nextLine();

        boolean sucesso = pecaService.adicionarEstoque(pecaId, quantidade, responsavel, origem);

        if (sucesso) {
            System.out.println("Estoque adicionado com sucesso!");
        } else {
            System.out.println("Erro ao adicionar estoque. Verifique o ID da peça e a quantidade.");
        }
    }

    private static void buscarPecaPorNome() {
        System.out.println("\n=== BUSCAR PEÇA ===");
        System.out.print("Digite o nome (ou parte) da peça: ");
        String nome = scanner.nextLine();

        List<Peca> resultados = pecaService.buscarPecaPorNome(nome);

        if (resultados.isEmpty()) {
            System.out.println("Nenhuma peça encontrada.");
            return;
        }

        System.out.println("\n=== RESULTADOS DA BUSCA ===");
        for (Peca peca : resultados) {
            System.out.println(peca);
        }
    }

    private static void gerenciarPedidosPendentes() {
        while (true) {
            System.out.println("\n=== PEDIDOS PENDENTES ===");
            System.out.println("1. Listar Pedidos Pendentes");
            System.out.println("2. Criar Pedido Pendente");
            System.out.println("3. Atender Pedido");
            System.out.println("4. Cancelar Pedido");
            System.out.println("5. Verificar Pedidos Prontos para Atender");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    listarPedidosPendentes();
                    break;
                case 2:
                    criarPedidoPendente();
                    break;
                case 3:
                    atenderPedido();
                    break;
                case 4:
                    cancelarPedido();
                    break;
                case 5:
                    verificarPedidosProntos();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void listarPedidosPendentes() {
        System.out.println("\n=== LISTA DE PEDIDOS PENDENTES ===");
        List<PedidoPendente> pedidos = pecaService.listarPedidosPendentes();

        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido pendente.");
            return;
        }

        for (PedidoPendente pedido : pedidos) {
            System.out.println(pedido);
        }
    }

    private static void criarPedidoPendente() {
        System.out.println("\n=== CRIAR PEDIDO PENDENTE ===");
        listarPecasPorCategoria();

        System.out.print("\nID da peça: ");
        int pecaId = lerOpcao();

        System.out.print("Quantidade: ");
        int quantidade = lerOpcao();

        System.out.print("Cliente: ");
        String cliente = scanner.nextLine();

        System.out.print("Responsável: ");
        String responsavel = scanner.nextLine();

        System.out.print("Observações: ");
        String observacoes = scanner.nextLine();

        boolean sucesso = pecaService.criarPedidoPendente(pecaId, quantidade, cliente, responsavel, observacoes);

        if (sucesso) {
            System.out.println("Pedido pendente criado com sucesso!");
        } else {
            System.out.println("Erro ao criar pedido pendente. Verifique o ID da peça.");
        }
    }

    private static void atenderPedido() {
        System.out.println("\n=== ATENDER PEDIDO PENDENTE ===");
        listarPedidosPendentes();

        System.out.print("\nID do pedido: ");
        int pedidoId = lerOpcao();

        System.out.print("Destino da entrega: ");
        String destino = scanner.nextLine();

        boolean sucesso = pecaService.atenderPedidoPendente(pedidoId, destino);

        if (sucesso) {
            System.out.println("Pedido atendido com sucesso! Estoque atualizado.");
        } else {
            System.out.println("Erro ao atender pedido. Verifique se há estoque suficiente.");
        }
    }

    private static void cancelarPedido() {
        System.out.println("\n=== CANCELAR PEDIDO PENDENTE ===");
        listarPedidosPendentes();

        System.out.print("\nID do pedido: ");
        int pedidoId = lerOpcao();

        System.out.print("Tem certeza que deseja cancelar este pedido? (S/N): ");
        String confirmacao = scanner.nextLine();

        if ("S".equalsIgnoreCase(confirmacao)) {
            boolean sucesso = pecaService.cancelarPedidoPendente(pedidoId);

            if (sucesso) {
                System.out.println("Pedido cancelado com sucesso!");
            } else {
                System.out.println("Erro ao cancelar pedido. Pedido não encontrado ou já foi atendido.");
            }
        } else {
            System.out.println("Cancelamento cancelado.");
        }
    }

    private static void verificarPedidosProntos() {
        System.out.println("\n=== PEDIDOS PRONTOS PARA ATENDER ===");
        listarPecasPorCategoria();

        System.out.print("\nID da peça para verificar pedidos: ");
        int pecaId = lerOpcao();

        List<PedidoPendente> pedidosProntos = pecaService.verificarPedidosAtendiveis(pecaId);

        if (pedidosProntos.isEmpty()) {
            System.out.println("Nenhum pedido pronto para atender para esta peça.");
            return;
        }

        System.out.println("\nPedidos que podem ser atendidos:");
        for (PedidoPendente pedido : pedidosProntos) {
            System.out.println(pedido);
        }

        System.out.print("\nDeseja atender algum desses pedidos? (S/N): ");
        String resposta = scanner.nextLine();

        if ("S".equalsIgnoreCase(resposta)) {
            System.out.print("ID do pedido para atender: ");
            int pedidoId = lerOpcao();

            System.out.print("Destino da entrega: ");
            String destino = scanner.nextLine();

            boolean sucesso = pecaService.atenderPedidoPendente(pedidoId, destino);

            if (sucesso) {
                System.out.println("Pedido atendido com sucesso!");
            } else {
                System.out.println("Erro ao atender pedido.");
            }
        }
    }

    private static void gerarListaPeriodo16h() {
        System.out.println("\n=== 📋 LISTA DE PEDIDOS - PERÍODO 16h-16h ===");
        System.out.println("🎯 INCLUI APENAS: Baterias • Telas/Frontais • Tampas");
        System.out.println(pecaService.getInfoPeriodo16h());
        System.out.println("Horário atual: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        List<Historico> vendasPeriodo = pecaService.listarVendasPeriodo16h();
        Map<String, Integer> listaConsolidada = pecaService.gerarListaConsolidada();

        if (vendasPeriodo.isEmpty()) {
            System.out.println("\n📭 Nenhuma venda registrada neste período para as categorias de pedidos.");
            return;
        }

        System.out.println("\n=== 📦 LISTA CONSOLIDADA PARA PEDIDOS ===");
        System.out.println("🎯 APENAS: Baterias • Telas • Tampas");

        Map<String, Integer> baterias = new HashMap<>();
        Map<String, Integer> telas = new HashMap<>();
        Map<String, Integer> tampas = new HashMap<>();

        for (Map.Entry<String, Integer> entry : listaConsolidada.entrySet()) {
            String nomePeca = entry.getKey().toLowerCase();
            if (nomePeca.contains("bateria")) {
                baterias.put(entry.getKey(), entry.getValue());
            } else if (nomePeca.contains("frontal") || nomePeca.contains("tela")) {
                telas.put(entry.getKey(), entry.getValue());
            } else if (nomePeca.contains("tampa")) {
                tampas.put(entry.getKey(), entry.getValue());
            }
        }

        int totalUnidades = 0;
        int totalItens = 0;

        if (!baterias.isEmpty()) {
            System.out.println("\n🔋 BATERIAS:");
            for (Map.Entry<String, Integer> entry : baterias.entrySet()) {
                System.out.printf("   %s: %d unidades%n", entry.getKey(), entry.getValue());
                totalUnidades += entry.getValue();
                totalItens++;
            }
        }

        if (!telas.isEmpty()) {
            System.out.println("\n📱 TELAS/FRONTAIS:");
            for (Map.Entry<String, Integer> entry : telas.entrySet()) {
                System.out.printf("   %s: %d unidades%n", entry.getKey(), entry.getValue());
                totalUnidades += entry.getValue();
                totalItens++;
            }
        }

        if (!tampas.isEmpty()) {
            System.out.println("\n🎯 TAMPAS:");
            for (Map.Entry<String, Integer> entry : tampas.entrySet()) {
                System.out.printf("   %s: %d unidades%n", entry.getKey(), entry.getValue());
                totalUnidades += entry.getValue();
                totalItens++;
            }
        }

        System.out.println("\n=== 📊 RESUMO FINAL ===");
        System.out.printf("Total de unidades: %d%n", totalUnidades);
        System.out.printf("Total de itens diferentes: %d%n", totalItens);

        System.out.print("\n💾 Deseja salvar esta lista? (S/N): ");
        String salvar = scanner.nextLine();

        if ("S".equalsIgnoreCase(salvar)) {
            salvarListaPedidos(listaConsolidada);
        }
    }

    private static void salvarListaPedidos(Map<String, Integer> consolidado) {
        String nomeArquivo = "lista_pedidos_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm")) + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            writer.println("=== LISTA DE PEDIDOS - PERÍODO 16h-16h ===");
            writer.println("🎯 INCLUI APENAS: Baterias • Telas/Frontais • Tampas");
            writer.println("Gerado em: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            writer.println(pecaService.getInfoPeriodo16h());
            writer.println();

            writer.println("=== LISTA CONSOLIDADA PARA PEDIDOS ===");
            writer.println("🎯 APENAS: Baterias • Telas • Tampas");

            Map<String, Integer> baterias = new HashMap<>();
            Map<String, Integer> telas = new HashMap<>();
            Map<String, Integer> tampas = new HashMap<>();

            for (Map.Entry<String, Integer> entry : consolidado.entrySet()) {
                String nomePeca = entry.getKey().toLowerCase();
                if (nomePeca.contains("bateria")) {
                    baterias.put(entry.getKey(), entry.getValue());
                } else if (nomePeca.contains("frontal") || nomePeca.contains("tela")) {
                    telas.put(entry.getKey(), entry.getValue());
                } else if (nomePeca.contains("tampa")) {
                    tampas.put(entry.getKey(), entry.getValue());
                }
            }

            int totalUnidades = 0;
            int totalItens = 0;

            if (!baterias.isEmpty()) {
                writer.println("\n🔋 BATERIAS:");
                for (Map.Entry<String, Integer> entry : baterias.entrySet()) {
                    writer.printf("%s: %d unidades%n", entry.getKey(), entry.getValue());
                    totalUnidades += entry.getValue();
                    totalItens++;
                }
            }

            if (!telas.isEmpty()) {
                writer.println("\n📱 TELAS/FRONTAIS:");
                for (Map.Entry<String, Integer> entry : telas.entrySet()) {
                    writer.printf("%s: %d unidades%n", entry.getKey(), entry.getValue());
                    totalUnidades += entry.getValue();
                    totalItens++;
                }
            }

            if (!tampas.isEmpty()) {
                writer.println("\n🎯 TAMPAS:");

                for (Map.Entry<String, Integer> entry : tampas.entrySet()) {
                    writer.printf("%s: %d unidades%n", entry.getKey(), entry.getValue());
                    totalUnidades += entry.getValue();
                    totalItens++;
                }
            }

            writer.println("\n---");
            writer.printf("TOTAL: %d peças | %d tipos diferentes%n", totalUnidades, totalItens);

            System.out.println("✅ Lista salva como: " + nomeArquivo);

        } catch (IOException e) {
            System.out.println("Erro ao salvar lista: " + e.getMessage());
        }
    }
}