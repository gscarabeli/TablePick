/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.controller;

import br.edu.fesa.tablepick.App;
import br.edu.fesa.tablepick.util.AlertaUtil;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 *
 * @author DAVI
 */
public class HubController {
    
    @FXML
    private Button btnCadastrarMesa;
    
    @FXML
    private Button btnConsultarMesa;
    
    @FXML
    private Button btnConsultarReserva;
    
    @FXML
    private Button btnVoltarLogin;
    
    @FXML
    private void initialize() {
        btnCadastrarMesa.setOnAction(event -> abrirCadastrarMesa());
        btnConsultarMesa.setOnAction(event -> abrirConsultarMesa());  
        btnConsultarReserva.setOnAction(event -> abrirConsultarReserva());  
        btnVoltarLogin.setOnAction(event -> voltarLogin());  
    }
    
    private void abrirCadastrarMesa() {
        try {
            App.setRoot("create_table");
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir a tela cadastro de mesa.", Alert.AlertType.ERROR);
        }
    }
    
    private void abrirConsultarMesa() {
        try {
            App.setRoot("search_table");
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir a tela de consulta de mesa.", Alert.AlertType.ERROR);
        }
    }
    
    private void abrirConsultarReserva() {
        try {
            App.setRoot("search_book");
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir a tela de consulta de reservas.", Alert.AlertType.ERROR);
        }
    }
    
    private void voltarLogin() {
        try {
            App.setRoot("first_page");
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir tela principal.", Alert.AlertType.ERROR);
        }
    }
}
