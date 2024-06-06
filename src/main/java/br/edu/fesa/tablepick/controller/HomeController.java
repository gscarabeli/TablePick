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
public class HomeController {
    
    @FXML
    private Button btnReservar;
    
    @FXML
    private Button btnLogin;
    
    
    @FXML
    private void initialize() {
        btnReservar.setOnAction(event -> abrirReservar());
        btnLogin.setOnAction(event -> abrirLogin());        
    }
    
    private void abrirReservar() {
        try {
            App.setRoot("book_table");
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir a tela de reserva.", Alert.AlertType.ERROR);
        }
    }
    
    private void abrirLogin() {
        try {
            App.setRoot("login");
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir a tela de login.", Alert.AlertType.ERROR);
        }
    }
}
