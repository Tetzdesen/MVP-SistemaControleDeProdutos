package com.br.sistemacontroledeprodutos.model;

/**
 *
 * @author tetzner
 */
public final class Produto {

    private String nome;
    private double precoCusto;
    private double percentualLucro;
    private double precoVenda;

    public Produto(String nome, double precoCusto, double percentualLucro) {
        this.nome = nome;
        this.precoCusto = precoCusto;
        this.percentualLucro = percentualLucro;
        this.precoVenda = calcularPrecoVenda();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public double getPercentualLucro() {
        return percentualLucro;
    }

    public void setPercentualLucro(double percentualLucro) {
        this.percentualLucro = percentualLucro;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public double calcularPrecoVenda() {
        return precoCusto + (precoCusto * percentualLucro / 100);
    }

    @Override
    public String toString() {
        return "Produto{" + "nome=" + nome + ", precoCusto=" + precoCusto + ", percentualLucro=" + percentualLucro + ", precoVenda=" + precoVenda + '}';
    }
}
