/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.dao;

import br.edu.fesa.tablepick.model.Funcionario;
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
public class FuncionarioDAO {
    
    public List<Funcionario> listar() {
        List<Funcionario> funcionarios = new ArrayList<>();
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM funcionario");
             ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    funcionarios.add(new Funcionario(resultSet.getInt("id_funcionario"), resultSet.getString("nome"), resultSet.getString("email"), resultSet.getString("senha"), resultSet.getString("credencial"), resultSet.getString("salt")));
                }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar funcionários.");
            ex.printStackTrace();
        }
        return funcionarios;
    }
    
    public void inserir(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario (nome, email, senha, credencial, salt) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, funcionario.getNome());
                statement.setString(2, funcionario.getEmail());
                statement.setString(3, funcionario.getSenha());
                statement.setString(4, funcionario.getCredencial());
                statement.setString(5, funcionario.getSalt());
            
                statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir funcionario.");
            ex.printStackTrace();
        }
    }
    
    public Funcionario buscarPorEmail(String email) {
        String sql = "SELECT * FROM funcionario WHERE email = ?";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return new Funcionario(
                    resultSet.getInt("id_funcionario"),
                    resultSet.getString("nome"),
                    resultSet.getString("email"),
                    resultSet.getString("senha"),
                    resultSet.getString("credencial"),
                    resultSet.getString("salt")
                );
            } /*else {
                return null;
            }*/
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar funcionario por email.");
            ex.printStackTrace();
        } 
        return null;
    }
    
    public Funcionario validarCredenciais(String credencial, String email, String senhaCriptografada, String salt) {
        String sql = "SELECT * FROM funcionario WHERE credencial = ? AND email = ? AND senha = ? AND salt = ?";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, credencial);
            statement.setString(2, email);
            statement.setString(3, senhaCriptografada);
            statement.setString(4, salt);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Funcionario(
                    resultSet.getInt("id_funcionario"),
                    resultSet.getString("nome"),
                    resultSet.getString("email"),
                    resultSet.getString("senha"),
                    resultSet.getString("credencial"),
                    resultSet.getString("salt")
                );
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao validar credenciais.");
            ex.printStackTrace();
        }
        return null;
    }
}
