/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.dao;

import br.edu.fesa.tablepick.model.Mesa;
import br.edu.fesa.tablepick.model.Reserva;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DAVI
 */
public class ReservaDAO {
    
    public List<Reserva> listar() {
        List<Reserva> reservas = new ArrayList<>();
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM reserva ORDER BY status");
             ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    reservas.add(new Reserva(resultSet.getInt("id_reserva"), resultSet.getTimestamp("data_reserva").toLocalDateTime(), resultSet.getInt("quantidade_pessoas"), resultSet.getInt("id_cliente"), resultSet.getInt("id_mesa"), resultSet.getString("status")));
                }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar reservas.");
            ex.printStackTrace();
        }
        return reservas;
    }
    
    public int inserir(Reserva reserva) {
        String sql = "INSERT INTO reserva (data_reserva, quantidade_pessoas, id_cliente, id_mesa, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                statement.setTimestamp(1, Timestamp.valueOf(reserva.getDataReserva()));
                statement.setInt(2, reserva.getQtdPessoas());
                statement.setInt(3, reserva.getIdCliente());
                statement.setNull(4, java.sql.Types.INTEGER);
                statement.setString(5, reserva.getStatus());
            
                statement.executeUpdate();
                
                ResultSet resultSet = statement.getGeneratedKeys();
                
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir reserva.");
            ex.printStackTrace();
        }
        return -1;
    }
    
    public void alterar(Reserva reserva, Mesa mesa) {
        String sql = "UPDATE reserva SET id_mesa = ?, status = 'Ativa' WHERE id_reserva = ?";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, mesa.getCodigo());
                statement.setInt(2, reserva.getCodigo());
            
                statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao alterar reserva.");
            ex.printStackTrace();
        }
    }
    
    public void alterar(Reserva reserva) {
        String sql = "UPDATE reserva SET quantidade_pessoas = ? WHERE id_reserva = ?";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, reserva.getQtdPessoas());
                statement.setInt(2, reserva.getCodigo());
            
                statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao alterar reserva.");
            ex.printStackTrace();
        }
    }
    
    public void alterarStatus(Reserva reserva) {
        String sql = "UPDATE reserva SET status = 'Finalizada' WHERE id_reserva = ?";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, reserva.getCodigo());
            
                statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao alterar reserva.");
            ex.printStackTrace();
        }
    }
    
    public void remover(Reserva reserva) {
        String sql = "DELETE FROM reserva WHERE id_reserva = ?";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, reserva.getCodigo());
            
                statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao remover reserva.");
            ex.printStackTrace();
        }
    }
    
    public Reserva listarPorID(int reserva) {
        String sql = "SELECT * FROM reserva WHERE id_reserva = ?";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, reserva);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Reserva(resultSet.getInt("id_reserva"), resultSet.getTimestamp("data_reserva").toLocalDateTime(), resultSet.getInt("quantidade_pessoas"), resultSet.getInt("id_cliente"), resultSet.getInt("id_mesa"), resultSet.getString("status"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar reserva.");
            ex.printStackTrace();
        }
        return null;
    }
    
    public Reserva obterReservaMaisAntiga(){
        String sql = "SELECT * FROM reserva WHERE status = 'Aguardando' ORDER BY data_reserva ASC LIMIT 1";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    System.out.println("Reserva encontrada: ID " + resultSet.getInt("id_reserva"));
                    return new Reserva(resultSet.getInt("id_reserva"), resultSet.getTimestamp("data_reserva").toLocalDateTime(), resultSet.getInt("quantidade_pessoas"), resultSet.getInt("id_cliente"), resultSet.getInt("id_mesa"), resultSet.getString("status"));
                }
        } catch (SQLException ex) {
            System.out.println("Erro ao obter reserva mais antiga.");
            ex.printStackTrace();
        }
        return null;
    }
    
    public Map<Integer, Integer> obterReservasPorMesa() {
        Map<Integer, Integer> reservas = new HashMap<>();
        String sql = "SELECT id_mesa, COUNT(*) as totalReservas FROM reserva WHERE id_mesa IS NOT NULL GROUP BY id_mesa";
        try (Connection connection = Conexao.abrirConexao();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int idMesa = resultSet.getInt("id_mesa");
                    int totalReservas = resultSet.getInt("totalReservas");
                    reservas.put(idMesa, totalReservas);
                }
        } catch (SQLException ex) {
            System.out.println("Erro ao obter reservas por mesa.");
            ex.printStackTrace();
        }
        return reservas;
    }
    
}
