package br.edu.fesa.tablepick.controller;

import br.edu.fesa.tablepick.App;
import br.edu.fesa.tablepick.dao.FuncionarioDAO;
import br.edu.fesa.tablepick.model.Funcionario;
import br.edu.fesa.tablepick.util.CriptografiaUtil;
import br.edu.fesa.tablepick.util.ValidacaoUtil;
import br.edu.fesa.tablepick.util.AlertaUtil;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class CadastroController {
    
    @FXML
    private TextField txtNome;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private TextField txtCredencial;
    
    @FXML
    private PasswordField txtSenha;
    
    @FXML
    private TextField txtSenhaExibir;
    
    @FXML
    private PasswordField txtSenhaConfirmacao;
    
    @FXML
    private TextField txtSenhaConfirmacaoExibir;
    
    @FXML
    private ToggleButton btnExibirSenha;

    @FXML
    private ToggleButton btnExibirConfirmacaoSenha;
    
    @FXML
    private Button btnCadastrar;
    
    private FuncionarioDAO funcionarioDAO;
    
    public CadastroController() {
        this.funcionarioDAO = new FuncionarioDAO();
    }
    
    @FXML
    private void initialize() {
        btnCadastrar.setOnAction(event -> cadastrar());
        btnExibirSenha.setOnAction(event -> toggleExibirSenha());
        btnExibirConfirmacaoSenha.setOnAction(event -> toggleExibirConfirmacaoSenha());
    }
    
    private void cadastrar() {
        
        try{
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String credencial = txtCredencial.getText();
            String senha = txtSenha.isVisible() ? txtSenha.getText() : txtSenhaExibir.getText();
            String confirmacaoSenha = txtSenhaConfirmacao.isVisible() ? txtSenhaConfirmacao.getText() : txtSenhaConfirmacaoExibir.getText();
            
            System.out.println(nome + " " + email + " " + credencial + " " + senha);
        
            if (nome.isEmpty() || email.isEmpty() || credencial.isEmpty() || senha.isEmpty() || confirmacaoSenha.isEmpty()) {
                AlertaUtil.exibirAlerta("Por favor, preencha todos os campos.", AlertType.WARNING);
                return;
            }
        
            if (!ValidacaoUtil.validaEmail(email)) {
                AlertaUtil.exibirAlerta("O email não é válido.", AlertType.WARNING);
                return;
            }

            if (!senha.equals(confirmacaoSenha)) {
                AlertaUtil.exibirAlerta("As senhas não coincidem.", AlertType.WARNING);
                return;
            }

            if (!ValidacaoUtil.validaSenha(senha)) {
                AlertaUtil.exibirAlerta("A senha não atende aos requisitos.", AlertType.WARNING);
                return;
            }
            
            Funcionario existente = funcionarioDAO.buscarPorEmail(email);
            if (existente != null) {
                AlertaUtil.exibirAlerta("O email já está cadastrado.", Alert.AlertType.WARNING);
                return;
            }

            String salt = CriptografiaUtil.gerarSalt();
            String senhaCriptografada = CriptografiaUtil.criptografarSenha(senha, salt);
        
            Funcionario funcionario = new Funcionario(0, nome, email, senhaCriptografada, credencial, salt);
        
            funcionarioDAO.inserir(funcionario);
            
            AlertaUtil.exibirAlerta("Cadastrado com sucesso!", AlertType.INFORMATION);
            
            App.setRoot("login");
        }
        catch (IOException ex){
            AlertaUtil.exibirAlerta(ex.toString(), AlertType.ERROR);
        }  
    } 
    
    private void toggleExibirSenha() {
        if (btnExibirSenha.isSelected()) {
            txtSenhaExibir.setText(txtSenha.getText());
            txtSenhaExibir.setVisible(true);
            txtSenha.setVisible(false);
        } else {
            txtSenha.setText(txtSenhaExibir.getText());
            txtSenha.setVisible(true);
            txtSenhaExibir.setVisible(false);
        }
    }

    private void toggleExibirConfirmacaoSenha() {
        if (btnExibirConfirmacaoSenha.isSelected()) {
            txtSenhaConfirmacaoExibir.setText(txtSenhaConfirmacao.getText());
            txtSenhaConfirmacaoExibir.setVisible(true);
            txtSenhaConfirmacao.setVisible(false);
        } else {
            txtSenhaConfirmacao.setText(txtSenhaConfirmacaoExibir.getText());
            txtSenhaConfirmacao.setVisible(true);
            txtSenhaConfirmacaoExibir.setVisible(false);
        }
    }
}
