/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.controller;

import br.edu.fesa.tablepick.App;
import br.edu.fesa.tablepick.dao.ClienteDAO;
import br.edu.fesa.tablepick.dao.ReservaDAO;
import br.edu.fesa.tablepick.model.Cliente;
import br.edu.fesa.tablepick.model.Reserva;
import br.edu.fesa.tablepick.util.AlertaUtil;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author DAVI
 */
public class EditarReservaController {
    
    @FXML
    private TextField txtQtdPessoas;
    
    @FXML
    private TextField txtNome;
    
    @FXML
    private TextField txtCelular;
    
    @FXML
    private Button btnSalvar;
    
    @FXML
    private Button btnVoltar;
    
    private Reserva reserva;
    private Cliente cliente;
    
    private ClienteDAO clienteDAO;
    
    public EditarReservaController() {
        this.clienteDAO = new ClienteDAO();
    }
    
    @FXML
    public void initialize() {
        btnSalvar.setOnAction(event -> salvarEdicao());
        btnVoltar.setOnAction(event -> voltar());
    }
    
    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
        if (reserva != null) {
            cliente = clienteDAO.listarPorID(new Cliente(reserva.getIdCliente(), "", ""));
            txtQtdPessoas.setText(String.valueOf(reserva.getQtdPessoas()));
            txtNome.setText(cliente.getNome());
            txtCelular.setText(cliente.getCelular());
        }
    }
    
    private void salvarEdicao() {
        if (reserva != null && cliente != null) {
            reserva.setQtdPessoas(Integer.parseInt(txtQtdPessoas.getText()));
            cliente.setNome(txtNome.getText());
            cliente.setCelular(txtCelular.getText());

            clienteDAO.alterar(cliente);
            
            ReservaDAO reservaDAO = new ReservaDAO();
            
            reservaDAO.alterar(reserva);

            AlertaUtil.exibirAlerta("Reserva atualizada com sucesso!", Alert.AlertType.INFORMATION);
            voltar();
        }
    }
    
    private void voltar() {
        try{
            App.setRoot("search_book");
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir tela de consulta de reserva.", Alert.AlertType.ERROR);
        }
    }
}
