/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.model;

/**
 *
 * @author DAVI
 */
public class Mesa {
    
    private Integer codigo;
    private Boolean livre;
    private Integer qtdLugares;
    private String tipo;
    
    public Mesa() { 
    }
    
    public Mesa(Integer codigo, Boolean livre, Integer qtdLugares, String tipo) {
        this.codigo = codigo;
        this.livre = livre;
        this.qtdLugares = qtdLugares;
        this.tipo = tipo;
    }
    
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    
    public Boolean getLivre() {
        return livre;
    }

    public void setLivre(Boolean livre) {
        this.livre = livre;
    }
    
    public Integer getQtdLugares() {
        return qtdLugares;
    }

    public void setQtdLugares(Integer qtdLugares) {
        this.qtdLugares = qtdLugares;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
