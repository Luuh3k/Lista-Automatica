package com.empresa.pecas.model;

import java.time.LocalDateTime;

public class Peca {
    private int id;
    private String nome;
    private String descricao;
    private int quantidade;
    private String localizacao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public Peca() {}

    public Peca(int id, String nome, String descricao, int quantidade, String localizacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.localizacao = localizacao;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    @Override
    public String toString() {
        return String.format("Peça [ID: %d, Nome: %s, Quantidade: %d, Local: %s]",
                id, nome, quantidade, localizacao);
    }
}