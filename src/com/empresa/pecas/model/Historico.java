package com.empresa.pecas.model;

import java.time.LocalDateTime;

public class Historico {
    private int id;
    private int pecaId;
    private String nomePeca;
    private int quantidade;
    private String responsavel;
    private String destino;
    private LocalDateTime dataMovimentacao;
    private String tipoMovimentacao;

    public Historico() {}

    public Historico(int id, int pecaId, String nomePeca, int quantidade,
                     String responsavel, String destino, String tipoMovimentacao) {
        this.id = id;
        this.pecaId = pecaId;
        this.nomePeca = nomePeca;
        this.quantidade = quantidade;
        this.responsavel = responsavel;
        this.destino = destino;
        this.tipoMovimentacao = tipoMovimentacao;
        this.dataMovimentacao = LocalDateTime.now();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPecaId() { return pecaId; }
    public void setPecaId(int pecaId) { this.pecaId = pecaId; }

    public String getNomePeca() { return nomePeca; }
    public void setNomePeca(String nomePeca) { this.nomePeca = nomePeca; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public LocalDateTime getDataMovimentacao() { return dataMovimentacao; }
    public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public String getTipoMovimentacao() { return tipoMovimentacao; }
    public void setTipoMovimentacao(String tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    @Override
    public String toString() {
        return String.format("Histórico [Data: %s, Peça: %s, Quantidade: %d, Responsável: %s, Tipo: %s]",
                dataMovimentacao.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                nomePeca, quantidade, responsavel, tipoMovimentacao);
    }
}