/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.dao;

import br.edu.fesa.tablepick.model.Mesa;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author DAVI
 */
public class MesaDAO /*implements GenericoDAO<Mesa>*/{
    
    //@Override
    public List<Mesa> listar() {
        List<Mesa> mesas = new ArrayList<>();
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM mesa");
             ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    mesas.add(new Mesa(resultSet.getInt("id_mesa"), resultSet.getBoolean("is_livre"), resultSet.getInt("quantidade_lugares"), resultSet.getString("tipo_mesa")));
                }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar mesas.");
            ex.printStackTrace();
        }
        return mesas;
    }
    
    //@Override
    public void inserir(Mesa mesa) {
        String sql = "INSERT INTO mesa (is_livre, quantidade_lugares, tipo_mesa) VALUES (?, ?, ?)";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setBoolean(1, mesa.getLivre());
                statement.setInt(2, mesa.getQtdLugares());
                statement.setString(3, mesa.getTipo());
            
                statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir mesa.");
            ex.printStackTrace();
        }
    }
    
    //@Override
    public void alterar(Mesa mesa) {
        String sql = "UPDATE mesa SET is_livre = ?, quantidade_lugares = ?, tipo_mesa = ? WHERE id_mesa = ?";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setBoolean(1, mesa.getLivre());
                statement.setInt(2, mesa.getQtdLugares());
                statement.setString(3, mesa.getTipo());
                statement.setInt(4, mesa.getCodigo());
            
                statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao alterar mesa.");
            ex.printStackTrace();
        }
    }
    
    //@Override
    public void remover(Mesa mesa) {
        String sql = "DELETE FROM mesa WHERE id_mesa = ?";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, mesa.getCodigo());
            
                statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao remover mesa.");
            ex.printStackTrace();
        }
    }
    
    //@Override
    public Mesa listarPorID(int mesa) {
        String sql = "SELECT * FROM mesa WHERE id_mesa = ?";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, mesa);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Mesa(resultSet.getInt("id_mesa"), resultSet.getBoolean("is_livre"), resultSet.getInt("quantidade_lugares"), resultSet.getString("tipo_mesa"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar mesa.");
            ex.printStackTrace();
        }
        return null;
    }
    
    public Mesa obterMesaDisponivel(int qtdPessoas) {
        String sql = "SELECT * FROM mesa WHERE is_livre = 1 AND quantidade_lugares >= ? ORDER BY quantidade_lugares ASC LIMIT 1";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {
         
            statement.setInt(1, qtdPessoas);
         
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Mesa(resultSet.getInt("id_mesa"), resultSet.getBoolean("is_livre"), resultSet.getInt("quantidade_lugares"), resultSet.getString("tipo_mesa"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao obter mesa disponível.");
            ex.printStackTrace();
        }
        return null;
    }
    
    public void alterarStatus(Mesa mesa) {
        String sql = "UPDATE mesa SET is_livre = ? WHERE id_mesa = ?";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setBoolean(1, mesa.getLivre());
                statement.setInt(2, mesa.getCodigo());
            
                statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao alterar status da mesa.");
            ex.printStackTrace();
        }
    }
    
}
