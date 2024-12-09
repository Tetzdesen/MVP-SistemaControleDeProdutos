package com.br.sistemacontroledeprodutos.presenter;

import com.br.sistemacontroledeprodutos.dao.ProdutoCollection;
import com.br.sistemacontroledeprodutos.model.Produto;
import com.br.sistemacontroledeprodutos.dao.ProdutoDAOSQLite;
import com.br.sistemacontroledeprodutos.view.CadastroProdutoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author tetzner
 */
public final class CadastroProdutoPresenter {
    private final ProdutoDAOSQLite sqliteDB;
    private final ProdutoCollection produtos;
    private final CadastroProdutoView view;

    public CadastroProdutoPresenter() {
        
        sqliteDB = new ProdutoDAOSQLite();
        
        produtos = new ProdutoCollection();
        
        this.view = new CadastroProdutoView();
        
        configuraView();
    }
    
    private void configuraView(){
        
        this.view.setVisible(false);
        
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        this.view.getBtnIncluir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                   salvar();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        
        this.view.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelar();
            }
        });
        
        view.setLocationRelativeTo(null);
        
        view.setVisible(true);
            
        atualizarListaProdutos();
    }
    
    private void salvar() throws Exception{
        
        try{
    
            String nome = view.getTxtNome().getText();

            double precoCusto = Double.parseDouble(view.getTxtPrecoCusto().getText());

            double percentualLucro = Double.parseDouble(view.getTxtPercentualLucro().getText());

            verificarCampos(nome, precoCusto, percentualLucro);

            Produto produto = new Produto(nome, precoCusto, percentualLucro);

            produtos.adicionarProduto(produto);
            
            sqliteDB.inserirProduto(produto);

            JOptionPane.showMessageDialog(view, "Produto incluido com sucesso");
            
            atualizarListaProdutos();
            
            limparCampos();
            
            System.out.println(produtos.getProdutos().toString());
        }
        catch(ParseException | NumberFormatException erroDeDados){
            JOptionPane.showMessageDialog(view, "Favor informar dados válidos!" + erroDeDados);
        }
        catch(IllegalArgumentException erro){
            JOptionPane.showMessageDialog(view,erro.getMessage());
        }

    }
    
    private void cancelar(){
        view.dispose();
    }
    
    private void verificarCampos(String nome, double precoCusto, double percentualLucro) throws Exception{
        
        if(nome == null || nome.isEmpty()){
            throw new Exception("Nome do produto é obrigatório ");
        }
        
        if(precoCusto <= 0){
            throw new Exception("Preço de custo deve ser maior que zero ");
        }
        
        if(percentualLucro <= 0){
            throw new Exception("Percentual de lucro deve ser maior que zero ");
        }
        
    }
     
    private void limparCampos(){
        
        this.view.getTxtNome().setText("");
        this.view.getTxtPrecoCusto().setText("");
        this.view.getTxtPercentualLucro().setText("");
        this.view.getTxtPrecoVenda().setText("");
        
    }
    
    private void atualizarListaProdutos() {
        
        List<Produto> produtosCadastrados = sqliteDB.listarProdutos();
        JTextArea areaProdutos = view.getTxtAreaProdutos();

        areaProdutos.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        areaProdutos.setText("");

        try {
            int id = 0;

            areaProdutos.append(String.format("%-8s %-25s %-20s %-25s %-20s\n", 
                "ID", "Nome", "Preço de Custo", "Percentual de Lucro", "Preço de Venda"));
            areaProdutos.append("-------------------------------------------------------------------------------------------------------\n");

            for (Produto produto : produtosCadastrados) {
                areaProdutos.append(String.format("%-8d %-25s %-20.2f %-25s %-20.2f\n",
                    ++id,
                    produto.getNome(),
                    produto.getPrecoCusto(),
                    String.format("%.2f %%", produto.getPercentualLucro()), // Adiciona o símbolo de %
                    produto.getPrecoVenda()
                ));
            }
        } catch (IllegalArgumentException erroArrayVazio) {
            JOptionPane.showMessageDialog(view, erroArrayVazio.getMessage());
        }
        
    }
    
}
