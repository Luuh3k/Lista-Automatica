package com.empresa.pecas.model;

import java.time.LocalDateTime;

public class PedidoPendente {
    private int id;
    private int pecaId;
    private String nomePeca;
    private int quantidade;
    private String cliente;
    private String responsavel;
    private LocalDateTime dataPedido;
    private String status;
    private String observacoes;

    public PedidoPendente() {}

    public PedidoPendente(int id, int pecaId, String nomePeca, int quantidade,
                          String cliente, String responsavel, String observacoes) {
        this.id = id;
        this.pecaId = pecaId;
        this.nomePeca = nomePeca;
        this.quantidade = quantidade;
        this.cliente = cliente;
        this.responsavel = responsavel;
        this.observacoes = observacoes;
        this.dataPedido = LocalDateTime.now();
        this.status = "PENDENTE";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPecaId() { return pecaId; }
    public void setPecaId(int pecaId) { this.pecaId = pecaId; }

    public String getNomePeca() { return nomePeca; }
    public void setNomePeca(String nomePeca) { this.nomePeca = nomePeca; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

    public LocalDateTime getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDateTime dataPedido) { this.dataPedido = dataPedido; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    @Override
    public String toString() {
        return String.format(
                "Pedido [ID: %d, Peça: %s, Quantidade: %d, Cliente: %s, Status: %s]",
                id, nomePeca, quantidade, cliente, status
        );
    }
}