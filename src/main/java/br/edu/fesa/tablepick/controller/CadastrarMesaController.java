/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.controller;

import br.edu.fesa.tablepick.App;
import br.edu.fesa.tablepick.dao.MesaDAO;
import br.edu.fesa.tablepick.model.Mesa;
import br.edu.fesa.tablepick.util.AlertaUtil;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author DAVI
 */
public class CadastrarMesaController {
    
    @FXML
    private TextField txtQtdLugares;

    @FXML
    private TextField txtTipo;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnVoltar;

    private MesaDAO mesaDAO;

    public CadastrarMesaController() {
        mesaDAO = new MesaDAO();
    }

    @FXML
    private void initialize() {
        ValidarSomenteNumero(txtQtdLugares);
        
        btnCadastrar.setOnAction(event -> cadastrarMesa());
        btnVoltar.setOnAction(event -> voltar());
    }
    
    private void cadastrarMesa() {
        String qtdLugaresText = txtQtdLugares.getText();
        String tipo = txtTipo.getText();

        if (qtdLugaresText.isEmpty() || tipo.isEmpty()) {
            AlertaUtil.exibirAlerta("Por favor, preencha todos os campos.", Alert.AlertType.WARNING);
            return;
        }

        try {
            Mesa mesa = new Mesa(0, true, Integer.parseInt(qtdLugaresText), tipo);
            mesaDAO.inserir(mesa);
            AlertaUtil.exibirAlerta("Mesa cadastrada com sucesso!", Alert.AlertType.INFORMATION);
            voltar();
        } catch (NumberFormatException e) {
            AlertaUtil.exibirAlerta("Quantidade de lugares inválida. Por favor, insira um número inteiro.", Alert.AlertType.ERROR);
        }
    }
    
    private void voltar() {
        try {
            App.setRoot("main_hub");
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao voltar para a tela de consulta de mesas.", Alert.AlertType.ERROR);
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
