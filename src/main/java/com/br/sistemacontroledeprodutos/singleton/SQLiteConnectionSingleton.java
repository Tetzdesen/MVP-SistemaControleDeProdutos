package com.br.sistemacontroledeprodutos.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class SQLiteConnectionSingleton {

    private static volatile SQLiteConnectionSingleton singleInstance = null;
    private static final String URL = "jdbc:sqlite:db/produtos.db";

    private SQLiteConnectionSingleton() {

    }

    public static SQLiteConnectionSingleton getInstance() {
        SQLiteConnectionSingleton instance = SQLiteConnectionSingleton.singleInstance;
        if (instance == null) {
            synchronized (SQLiteConnectionSingleton.class) {
                instance = SQLiteConnectionSingleton.singleInstance;
                if (instance == null) {
                    SQLiteConnectionSingleton.singleInstance = instance = new SQLiteConnectionSingleton();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        criarTabelaProduto(conn);
        return conn;
    }

    private void criarTabelaProduto(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS produto (
                idProduto INTEGER PRIMARY KEY AUTOINCREMENT,
                nome VARCHAR(200) NOT NULL,
                precoCusto DOUBLE NOT NULL,
                percentualLucro DOUBLE NOT NULL,
                precoVenda DOUBLE NOT NULL
            );
        """;

        try (var stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexão SQLite fechada.");
            } catch (SQLException ex) {
                System.err.println("Erro ao fechar conexão SQLite: " + ex.getMessage());
            }
        }
    }
}
