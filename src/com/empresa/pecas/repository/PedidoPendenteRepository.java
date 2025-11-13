package com.empresa.pecas.repository;

import com.empresa.pecas.model.PedidoPendente;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoPendenteRepository {
    private List<PedidoPendente> pedidos;
    private int nextId;

    public PedidoPendenteRepository() {
        this.pedidos = new ArrayList<>();
        this.nextId = 1;
    }

    public List<PedidoPendente> findAll() {
        return new ArrayList<>(pedidos);
    }

    public Optional<PedidoPendente> findById(int id) {
        return pedidos.stream().filter(p -> p.getId() == id).findFirst();
    }

    public List<PedidoPendente> findByPecaId(int pecaId) {
        return pedidos.stream()
                .filter(p -> p.getPecaId() == pecaId && "PENDENTE".equals(p.getStatus()))
                .toList();
    }

    public PedidoPendente salvar(PedidoPendente pedido) {
        if (pedido.getId() == 0) {
            pedido.setId(nextId++);
        } else {
            pedidos.removeIf(p -> p.getId() == pedido.getId());
        }
        pedidos.add(pedido);
        return pedido;
    }

    public boolean atualizar(PedidoPendente pedido) {
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getId() == pedido.getId()) {
                pedidos.set(i, pedido);
                return true;
            }
        }
        return false;
    }

    public List<PedidoPendente> findPedidosPendentes() {
        return pedidos.stream()
                .filter(p -> "PENDENTE".equals(p.getStatus()))
                .toList();
    }
}