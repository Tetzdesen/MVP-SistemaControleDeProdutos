package com.br.sistemacontroledeprodutos.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author tetzner
 */
public final class SQLiteConnectionSingleton {
    
    private static SQLiteConnectionSingleton singleInstance = null;
    private Connection connectionSQLite;
    private static final String URL = "jdbc:sqlite:db/estoque.db";
    
    private SQLiteConnectionSingleton(){
        try {
            this.connectionSQLite = DriverManager.getConnection(URL);
            System.out.println("Conexão com o banco de dados SQLite estabelicida ");
            createRegistroTable();
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteConnectionSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static SQLiteConnectionSingleton getInstance(){
        if(singleInstance == null){
            singleInstance = new SQLiteConnectionSingleton();
        }
        
        return singleInstance;
    } 
    
    
    public Connection getConnection(){
            return connectionSQLite;      
    }
    
    private void createRegistroTable() {
        String sql = """
                     CREATE TABLE IF NOT EXISTS produto (
                         idProduto INTEGER PRIMARY KEY AUTOINCREMENT,
                         nome VARCHAR(200) NOT NULL,
                         precoCusto DOUBLE NOT NULL,
                         percentualLucro DOUBLE NOT NULL,
                         precoVenda DOUBLE NOT NULL
                     );""";

        try {
            
            if (connectionSQLite != null && !connectionSQLite.isClosed()) {

                var metaData = connectionSQLite.getMetaData();
                var tables = metaData.getTables(null, null, "produto", new String[] { "TABLE" });

                if (!tables.next()) {
                    connectionSQLite.createStatement().execute(sql);
                    System.out.println("Tabela criada com sucesso!");
                } else {
                    System.out.println("Tabela já existe, nenhuma criação necessária.");
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao criar a tabela: " + e.getMessage());
        }
        
    }
    
}
