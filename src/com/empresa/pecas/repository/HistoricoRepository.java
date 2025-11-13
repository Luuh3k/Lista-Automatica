package com.empresa.pecas.repository;

import com.empresa.pecas.model.Historico;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HistoricoRepository {
    private List<Historico> historicos;
    private int nextId;

    public HistoricoRepository() {
        this.historicos = new ArrayList<>();
        this.nextId = 1;
    }

    public List<Historico> findAll() {
        return new ArrayList<>(historicos);
    }

    public Optional<Historico> findById(int id) {
        return historicos.stream().filter(h -> h.getId() == id).findFirst();
    }

    public List<Historico> findByPecaId(int pecaId) {
        return historicos.stream()
                .filter(h -> h.getPecaId() == pecaId)
                .toList();
    }

    public Historico salvar(Historico historico) {
        historico.setId(nextId++);
        historicos.add(historico);
        return historico;
    }

    public List<Historico> findUltimos(int quantidade) {
        return historicos.stream()
                .sorted((h1, h2) -> h2.getDataMovimentacao().compareTo(h1.getDataMovimentacao()))
                .limit(quantidade)
                .toList();
    }

    public boolean atualizar(Historico historico) {
        for (int i = 0; i < historicos.size(); i++) {
            if (historicos.get(i).getId() == historico.getId()) {
                historicos.set(i, historico);
                return true;
            }
        }
        return false;
    }

    public boolean delete(int id) {
        return historicos.removeIf(h -> h.getId() == id);
    }

    public List<Historico> findSaidas() {
        return historicos.stream()
                .filter(h -> "SAIDA".equals(h.getTipoMovimentacao()))
                .toList();
    }
}