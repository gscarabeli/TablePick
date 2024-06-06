/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 *
 * @author DAVI
 */
public class Conexao {
    
    private static final String URL = "jdbc:mysql://servertp-azuredb.mysql.database.azure.com:3306/tablepick_db";
    private static final String USER = "servertp";
    private static final String PASSWORD = "cefsa123#";

    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(20);
        
        dataSource = new HikariDataSource(config);
    }

    public static Connection abrirConexao() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("Falha na conexão com a base de dados.");
            e.printStackTrace();
            return null;
        }
    }

    public static void fecharConexao() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Conexão com a base encerrada.");
        }
    }
}
