package com.empresa.pecas.repository;

import com.empresa.pecas.model.Peca;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PecaRepository {
    private List<Peca> pecas;
    private int nextId;

    public PecaRepository() {
        this.pecas = new ArrayList<>();
        this.nextId = 1;
        inicializarDadosCelular();
    }

    private void inicializarDadosCelular() {
        salvar(new Peca(nextId++, "Bateria iPhone 11", "Bateria original iPhone 11", 15, "Prateleira A1"));
        salvar(new Peca(nextId++, "Bateria Samsung S20", "Bateria original Samsung Galaxy S20", 12, "Prateleira A2"));
        salvar(new Peca(nextId++, "Bateria Xiaomi Redmi", "Bateria Xiaomi Redmi Note 10", 8, "Prateleira A3"));

        salvar(new Peca(nextId++, "Tela iPhone 12", "Tela frontal original iPhone 12", 8, "Prateleira B1"));
        salvar(new Peca(nextId++, "Tela Samsung A50", "Tela frontal Samsung Galaxy A50", 10, "Prateleira B2"));
        salvar(new Peca(nextId++, "Tela Motorola G100", "Tela Motorola Moto G100", 6, "Prateleira B3"));

        salvar(new Peca(nextId++, "Tampa iPhone 13", "Tampa traseira iPhone 13", 20, "Gaveta C1"));
        salvar(new Peca(nextId++, "Tampa Samsung S21", "Tampa traseira Samsung S21", 18, "Gaveta C2"));
        salvar(new Peca(nextId++, "Tampa Xiaomi", "Tampa traseira Xiaomi diversos", 25, "Gaveta C3"));
    }

    public List<Peca> findAll() {
        return new ArrayList<>(pecas);
    }

    public Optional<Peca> findById(int id) {
        return pecas.stream().filter(p -> p.getId() == id).findFirst();
    }

    public Peca salvar(Peca peca) {
        if (peca.getId() == 0) {
            peca.setId(nextId++);
        } else {
            pecas.removeIf(p -> p.getId() == peca.getId());
        }
        pecas.add(peca);
        return peca;
    }

    public boolean delete(int id) {
        return pecas.removeIf(p -> p.getId() == id);
    }
}