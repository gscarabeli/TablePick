/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.controller;

import br.edu.fesa.tablepick.App;
import br.edu.fesa.tablepick.dao.ReservaDAO;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import br.edu.fesa.tablepick.model.Reserva;
import br.edu.fesa.tablepick.util.AlertaUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 *
 * @author DAVI
 */
public class ReservaDashController {
    
    @FXML
    private BarChart<String, Number> barChart;
    
    @FXML
    private Button btnVoltar;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private ObservableList<String> horarios = FXCollections.observableArrayList();
    
    private ReservaDAO reservaDAO;

    public ReservaDashController() {
        reservaDAO = new ReservaDAO();
    }
    
    @FXML
    public void initialize() {
        xAxis.setLabel("Período");
        yAxis.setLabel("Reservas");
        
        btnVoltar.setOnAction(event -> voltar());

        List<Reserva> reservas = reservaDAO.listar();

        processarDados(reservas);
    }
    
    private void processarDados(List<Reserva> reservas) {
        List<BarChart.Series<String, Number>> seriesList = new ArrayList<>();

        Map<String, Integer> contadorReservas = new HashMap<>();
        contadorReservas.put("Manhã", 0);
        contadorReservas.put("Tarde", 0);
        contadorReservas.put("Noite", 0);

        LocalTime inicioTarde = LocalTime.of(12, 0);
        LocalTime inicioNoite = LocalTime.of(18, 0);

        for (Reserva reserva : reservas) {
            LocalTime horaReserva = reserva.getDataReserva().toLocalTime();
            if (horaReserva.isBefore(inicioTarde)) {
                contadorReservas.put("Manhã", contadorReservas.get("Manhã") + 1);
            } else if (horaReserva.isBefore(inicioNoite)) {
                contadorReservas.put("Tarde", contadorReservas.get("Tarde") + 1);
            } else {
                contadorReservas.put("Noite", contadorReservas.get("Noite") + 1);
            }
        }

        for (String periodo : contadorReservas.keySet()) {
            BarChart.Series<String, Number> series = new BarChart.Series<>();
            series.setName(periodo);
            series.getData().add(new BarChart.Data<>("Período", contadorReservas.get(periodo)));
            seriesList.add(series);
        }

        barChart.getData().addAll(seriesList);
    }
    
    private void voltar() {
        try{
            App.setRoot("search_book");
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir tela de consulta de reservas.", Alert.AlertType.ERROR);
        }
    }
}
