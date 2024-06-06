/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.controller;

import br.edu.fesa.tablepick.App;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import br.edu.fesa.tablepick.model.Reserva;
import br.edu.fesa.tablepick.dao.ReservaDAO;
import br.edu.fesa.tablepick.util.AlertaUtil;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

/**
 *
 * @author DAVI
 */
public class MesaDashController {
    
    @FXML
    private PieChart pieChart;
    
    @FXML
    private Button btnVoltar;
    
    private ReservaDAO reservaDAO;

    public MesaDashController() {
        reservaDAO = new ReservaDAO();
    }

    @FXML
    public void initialize() {
        pieChart.setLegendVisible(false);
        btnVoltar.setOnAction(event -> voltar());
        
        carregarDadosGrafico();
    }
    
    private void carregarDadosGrafico() {
        Map<Integer, Integer> reservasPorMesa = reservaDAO.obterReservasPorMesa();
        int totalReservasTip = reservasPorMesa.values().stream().mapToInt(Integer::intValue).sum();
        
        for (Map.Entry<Integer, Integer> entry : reservasPorMesa.entrySet()) {
            int idMesa = entry.getKey();
            int totalReservas = entry.getValue();

            PieChart.Data slice = new PieChart.Data("Mesa " + idMesa, totalReservas);
            pieChart.getData().add(slice);
            
            Tooltip tooltip = new Tooltip(
                    String.format("Mesa %d: %d reservas (%.2f%%)", 
                                  idMesa, 
                                  totalReservas, 
                                  (totalReservas / (double) totalReservasTip) * 100)
                );
                Tooltip.install(slice.getNode(), tooltip);
        }
    }
    
    private void voltar() {
        try{
            App.setRoot("search_table");
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir tela de consulta de mesas.", Alert.AlertType.ERROR);
        }
    }
}
