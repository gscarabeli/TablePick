/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 *
 * @author DAVI
 */
public class AlertaUtil {
    
    public static void exibirAlerta(String mensagem, AlertType alerta) {
        
        Alert alert = new Alert(alerta);
        if (alerta == AlertType.INFORMATION) {
            alert.setTitle("Sucesso");
        } else {
            alert.setTitle("Erro");
        }
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.show();
    }
}
