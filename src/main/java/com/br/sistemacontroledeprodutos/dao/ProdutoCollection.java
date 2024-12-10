package com.br.sistemacontroledeprodutos.dao;

import com.br.sistemacontroledeprodutos.model.Produto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author tetzner
 */
public final class ProdutoCollection {

    private final List<Produto> produtos;

    public ProdutoCollection() {
        this.produtos = new ArrayList<>();
    }

    public List<Produto> getProdutos() {
        return Collections.unmodifiableList(produtos);
    }

    public void adicionarProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Informe um produto válido");
        }

        this.produtos.add(produto);
    }

    public void removerProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Informe um produto válido");
        }

        this.produtos.remove(produto);
    }

    public Optional<Produto> findProdutoByNome(String nome) {
        for (Produto produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nome)) {
                return Optional.of(produto);
            }
        }
        return Optional.empty();
    }

}
