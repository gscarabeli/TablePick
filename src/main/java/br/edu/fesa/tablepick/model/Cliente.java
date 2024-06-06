/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.model;

/**
 *
 * @author DAVI
 */
public class Cliente {
    private Integer codigo;
    private String nome;
    private String celular;
    
    public Cliente() {
    }
    
    public Cliente(Integer codigo, String nome, String celular) {
        this.codigo = codigo;
        this.nome = nome;
        this.celular = celular;
    }
    
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
    
}
