package com.br.sistemacontroledeprodutos.dao;

import com.br.sistemacontroledeprodutos.model.Produto;
import com.br.sistemacontroledeprodutos.singleton.SQLiteConnectionSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author tetzner
 */
public final class ProdutoDAOSQLite implements ProdutoDAO {
    
    private final Connection connectionBD;
       
    public ProdutoDAOSQLite(){
        connectionBD = SQLiteConnectionSingleton.getInstance().getConnection();
    }
    
    @Override
    public void inserirProduto(Produto produto) {
        String sqlUsuario = "INSERT INTO produto (nome, precoCusto, percentualLucro, precoVenda) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmtProduto = connectionBD.prepareStatement(sqlUsuario)) {
             stmtProduto.setString(1, produto.getNome());
             stmtProduto.setDouble(2, produto.getPrecoCusto());
             stmtProduto.setDouble(3, produto.getPercentualLucro());
             stmtProduto.setDouble(4, produto.getPrecoVenda());
            
            stmtProduto.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir produto: " + e.getMessage());
        }
    }

    @Override
    public void atualizarProduto(Produto produto) {
       String sql = "UPDATE produto SET nome = ?, precoCusto = ?, percentualLucro = ?, precoVenda = ? WHERE id = ?";
        try (PreparedStatement stmt = connectionBD.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPrecoCusto());
            stmt.setDouble(3, produto.getPercentualLucro());
            stmt.setDouble(4, produto.getPrecoVenda());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage());
        }   
    }

    @Override
    public void deletarProdutoPorNome(String nomeProduto) {
       String sqlProduto = "DELETE FROM produto WHERE nome = ?";

        try (PreparedStatement stmt = connectionBD.prepareStatement(sqlProduto)) {

            stmt.setString(1, nomeProduto);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar produto: " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("empty-statement")
    public List<Produto> listarProdutos() {
        ProdutoCollection produtosCollections = new ProdutoCollection();
        String sql = "SELECT * FROM produto";
        try (Statement stmt = connectionBD.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Produto Produto = converterResultSetParaProduto(rs);
                produtosCollections.adicionarProduto(Produto);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao resgatar um produto: " + e.getMessage());
        } 
        
        return produtosCollections.getProdutos();
    }

    @Override
    public Optional<Produto> buscarProdutoPorId(String idProduto) {
        String sql = "SELECT * FROM produto WHERE id = ?";
        try (PreparedStatement stmt = connectionBD.prepareStatement(sql)) {
            stmt.setString(0, idProduto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Produto produto = converterResultSetParaProduto(rs);
                    return Optional.of(produto);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto pelo id: " + e.getMessage());
        }
    }

    @Override
    public Optional<Produto> buscarProdutoPorNome(String nomeProduto) {
        String sql = "SELECT * FROM produto WHERE username = ?";
        try (PreparedStatement stmt = connectionBD.prepareStatement(sql)) {
            stmt.setString(1, nomeProduto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Produto produto = converterResultSetParaProduto(rs);
                    return Optional.of(produto);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto pelo nome: " + e.getMessage());
        }
    }
  
    private Produto converterResultSetParaProduto(ResultSet rs) throws SQLException {
         
        String nome = rs.getString("nome");
        double precoCusto = rs.getDouble("precoCusto");
        double percentualLucro = rs.getDouble("percentualLucro");

        return new Produto(nome, precoCusto, percentualLucro);
    }

}
