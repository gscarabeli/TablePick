/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.controller;

import br.edu.fesa.tablepick.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import br.edu.fesa.tablepick.model.Mesa;
import br.edu.fesa.tablepick.dao.MesaDAO;
import br.edu.fesa.tablepick.util.AlertaUtil;
import java.io.IOException;

/**
 *
 * @author DAVI
 */
public class EditarMesaController {
    
    @FXML
    private TextField txtQtdLugares;
    
    @FXML
    private TextField txtTipo;
    
    @FXML
    private Button btnSalvar;
    
    @FXML
    private Button btnVoltar;
    
    private Mesa mesa;
    
    @FXML
    public void initialize() {
        btnSalvar.setOnAction(event -> salvarEdicao());
        btnVoltar.setOnAction(event -> voltarConsultaMesa());
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
        if (mesa != null) {
            txtQtdLugares.setText(String.valueOf(mesa.getQtdLugares()));
            txtTipo.setText(mesa.getTipo());
        }
    }
    
    private void salvarEdicao() {
        if (mesa != null) {
            mesa.setQtdLugares(Integer.parseInt(txtQtdLugares.getText()));
            mesa.setTipo(txtTipo.getText());

            MesaDAO mesaDAO = new MesaDAO();
            mesaDAO.alterar(mesa);

            AlertaUtil.exibirAlerta("Mesa atualizada com sucesso!", Alert.AlertType.INFORMATION);
            voltarConsultaMesa();
        }
    }
    
    private void voltarConsultaMesa() {
        try {
            App.setRoot("search_table");
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir a tela de consulta de mesa.", Alert.AlertType.ERROR);
        }
    }
}
