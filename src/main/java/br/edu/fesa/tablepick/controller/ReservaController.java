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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import java.time.LocalDateTime;
import javafx.scene.control.Alert;

/**
 *
 * @author DAVI
 */
public class ReservaController {
    
    @FXML
    private TextField txtQtdPessoas;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCelular;

    @FXML
    private Button btnReserva;
    
    @FXML
    private Button btnVoltarHome;
    
    private ClienteDAO clienteDAO;
    private ReservaDAO reservaDAO;
    
    public ReservaController() {
        this.clienteDAO = new ClienteDAO();
        this.reservaDAO = new ReservaDAO();
    }
    
    @FXML
    public void initialize() {
        ValidarSomenteNumero(txtQtdPessoas);
        ValidarSomenteNumero(txtCelular);

        btnReserva.setOnAction(event -> reservar());
        btnVoltarHome.setOnAction(event -> abrirHome());
    }
    
    private void reservar() {
        
        try {
            String nome = txtNome.getText();
            String celular = txtCelular.getText();
            String qtdPessoas = txtQtdPessoas.getText();
            
            if (nome.isEmpty() || celular.isEmpty() ||  qtdPessoas.isEmpty()) {
                AlertaUtil.exibirAlerta("Por favor, preencha todos os campos.", Alert.AlertType.WARNING);
                return;
            }

            Cliente cliente = new Cliente();
            cliente.setNome(nome);
            cliente.setCelular(celular);

            int clienteId = clienteDAO.inserir(cliente);
            if (clienteId != -1) {
                Reserva reserva = new Reserva();
                reserva.setDataReserva(LocalDateTime.now());
                reserva.setQtdPessoas(Integer.parseInt(qtdPessoas));
                reserva.setIdCliente(clienteId);
                //reserva.setIdMesa();
                reserva.setStatus("Aguardando");

                int reservaId = reservaDAO.inserir(reserva);

                if (reservaId != -1) {
                    AlertaUtil.exibirAlerta("Reserva efetuada com sucesso! Sua senha é: " + reservaId, Alert.AlertType.INFORMATION);
                    
                    App.setRoot("first_page");
                } else {
                    AlertaUtil.exibirAlerta("Erro ao efetuar a reserva.", Alert.AlertType.ERROR);
                }
            } else {
                AlertaUtil.exibirAlerta("Erro ao salvar os dados do cliente.", Alert.AlertType.ERROR);
            }
        }
        catch (IOException ex){
            AlertaUtil.exibirAlerta(ex.toString(), Alert.AlertType.ERROR);
        }        
    }
    
    private void abrirHome() {
        try {
            App.setRoot("first_page");
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir a tela inicial.", Alert.AlertType.ERROR);
        }
    }
    
    private void ValidarSomenteNumero(TextField textField) {
        textField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) {
                event.consume();
            }
        });
    }
}
