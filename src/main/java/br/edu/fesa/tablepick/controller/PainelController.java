/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.controller;

import br.edu.fesa.tablepick.dao.MesaDAO;
import br.edu.fesa.tablepick.dao.ReservaDAO;
import br.edu.fesa.tablepick.model.Mesa;
import br.edu.fesa.tablepick.model.Reserva;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 *
 * @author DAVI
 */
public class PainelController {
    
    @FXML
    private Label lblSenha;
    
    private ReservaDAO reservaDAO;
    private MesaDAO mesaDAO;
    private ScheduledExecutorService scheduler;
    
    public PainelController() {
        this.reservaDAO = new ReservaDAO();
        this.mesaDAO = new MesaDAO();
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    @FXML
    public void initialize() {
        scheduler.scheduleAtFixedRate(() -> Platform.runLater(this::atualizarSenha), 0, 5, TimeUnit.SECONDS);
    }
    
    private void atualizarSenha() {
    
        Reserva reserva = obterReservaMaisAntiga();
        if (reserva != null) {
            Mesa mesaDisponivel = obterMesaDisponivel(reserva.getQtdPessoas());
            if (mesaDisponivel != null) {
                mesaDisponivel.setLivre(false);
                associarReservaAMesa(reserva, mesaDisponivel);
                lblSenha.setText(String.valueOf(reserva.getCodigo()));
                System.out.println("Senha atualizada.");
            }
        }
    }
    
    private Reserva obterReservaMaisAntiga() {
        return reservaDAO.obterReservaMaisAntiga();
    }
    
    private Mesa obterMesaDisponivel(int qtdPessoas) {
        return mesaDAO.obterMesaDisponivel(qtdPessoas);
    }
    
    private void associarReservaAMesa (Reserva reserva, Mesa mesa) {
        reservaDAO.alterar(reserva, mesa);
        mesaDAO.alterarStatus(mesa);
    }
}
