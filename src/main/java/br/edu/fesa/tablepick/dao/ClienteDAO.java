/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.dao;

import br.edu.fesa.tablepick.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DAVI
 */
public class ClienteDAO {
    
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM cliente");
             ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    clientes.add(new Cliente(resultSet.getInt("id_cliente"), resultSet.getString("nome"), resultSet.getString("celular")));
                }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar clientes.");
            ex.printStackTrace();
        }
        return clientes;
    }
    
    public int inserir(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, celular) VALUES (?, ?)";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, cliente.getNome());
                statement.setString(2, cliente.getCelular());
            
                statement.executeUpdate();
                
                ResultSet resultSet = statement.getGeneratedKeys();
                
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir cliente.");
            ex.printStackTrace();
        }
        return -1;
    }
    
    public void alterar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, celular = ? WHERE id_cliente = ?";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, cliente.getNome());
                statement.setString(2, cliente.getCelular());
                statement.setInt(3, cliente.getCodigo());
            
                statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao alterar cliente.");
            ex.printStackTrace();
        }
    }
    
    public void remover(Cliente cliente) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, cliente.getCodigo());
            
                statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao remover mesa.");
            ex.printStackTrace();
        }
    }
    
    public Cliente listarPorID(Cliente cliente) {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, cliente.getCodigo());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Cliente(resultSet.getInt("id_cliente"), resultSet.getString("nome"), resultSet.getString("celular"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar mesa.");
            ex.printStackTrace();
        }
        return null;
    }
    
}
