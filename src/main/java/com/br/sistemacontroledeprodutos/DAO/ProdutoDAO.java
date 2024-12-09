package com.br.sistemacontroledeprodutos.DAO;

import com.br.sistemacontroledeprodutos.model.Produto;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author tetzner
 */
public interface ProdutoDAO {
    
    void inserirProduto(Produto produto);
       
    void atualizarProduto(Produto produto);
    
    void deletarProdutoPorNome(String nomeProduto);
    
    List<Produto> listarProdutos();
    
    Optional<Produto> buscarProdutoPorId(String idProduto);
    
    Optional<Produto> buscarProdutoPorNome(String nomeProduto);
    
}
