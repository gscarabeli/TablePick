/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.model;

import java.time.LocalDateTime;

/**
 *
 * @author DAVI
 */
public class Reserva {
    private Integer codigo;
    private LocalDateTime dataReserva;
    private Integer qtdPessoas;
    private Integer idCliente;
    private Integer idMesa;
    private String status;

    public Reserva() {
    }

    public Reserva(Integer codigo, LocalDateTime dataReserva, Integer qtdPessoas, Integer idCliente, Integer idMesa, String status) {
        this.codigo = codigo;
        this.dataReserva = dataReserva;
        this.qtdPessoas = qtdPessoas;
        this.idCliente = idCliente;
        this.idMesa = idMesa;
        this.status = status;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDateTime dataReserva) {
        this.dataReserva = dataReserva;
    }

    public Integer getQtdPessoas() {
        return qtdPessoas;
    }

    public void setQtdPessoas(Integer qtdPessoas) {
        this.qtdPessoas = qtdPessoas;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Integer idMesa) {
        this.idMesa = idMesa;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
