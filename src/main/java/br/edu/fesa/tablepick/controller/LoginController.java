package br.edu.fesa.tablepick.controller;

import br.edu.fesa.tablepick.App;
import br.edu.fesa.tablepick.dao.FuncionarioDAO;
import br.edu.fesa.tablepick.model.Funcionario;
import br.edu.fesa.tablepick.util.AlertaUtil;
import br.edu.fesa.tablepick.util.CriptografiaUtil;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class LoginController {
    
    @FXML
    private TextField txtCredencial;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private PasswordField txtSenha;
    
    @FXML
    private TextField txtSenhaExibir;
    
    @FXML
    private ToggleButton btnExibirSenha;
    
    @FXML
    private Button btnEntrar;
    
    @FXML
    private Button btnEsqueciSenha;
    
    @FXML
    private Button btnCadastro;
    
    @FXML
    private Button btnVoltar;
    
    private FuncionarioDAO funcionarioDAO;
    
    public LoginController() {
        this.funcionarioDAO = new FuncionarioDAO();
    }
    
    @FXML
    private void initialize() {
        btnEntrar.setOnAction(event -> entrar());
        btnCadastro.setOnAction(event -> abrirCadastro());
        btnExibirSenha.setOnAction(event -> toggleExibirSenha());
        btnEsqueciSenha.setOnAction(event -> esqueciSenha());
        btnVoltar.setOnAction(event -> voltarFirstPage());
    }
    
    private void entrar() {
        String credencial = txtCredencial.getText();
        String email = txtEmail.getText();
        String senha = txtSenha.isVisible() ? txtSenha.getText() : txtSenhaExibir.getText();

        if (credencial.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            AlertaUtil.exibirAlerta("Por favor, preencha todos os campos.", Alert.AlertType.WARNING);
            return;
        }

        Funcionario funcionario = funcionarioDAO.buscarPorEmail(email);

        if (funcionario != null) {
            String senhaCriptografada = CriptografiaUtil.criptografarSenha(senha, funcionario.getSalt());
            if (funcionarioDAO.validarCredenciais(credencial, email, senhaCriptografada, funcionario.getSalt()) != null) {
                AlertaUtil.exibirAlerta("Login realizado com sucesso!", Alert.AlertType.INFORMATION);
                try {
                    App.setRoot("main_hub");
                } catch (IOException ex) {
                    AlertaUtil.exibirAlerta("Erro ao carregar a tela principal.", Alert.AlertType.ERROR);
                }
            } else {
                AlertaUtil.exibirAlerta("Email ou senha incorretos.", Alert.AlertType.ERROR);
            }
        } else {
            AlertaUtil.exibirAlerta("Email ou senha incorretos.", Alert.AlertType.ERROR);
        }
    }       
    
    private void abrirCadastro() {
        try {
            App.setRoot("sign_up");
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir a tela de cadastro.", Alert.AlertType.ERROR);
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
    
    private void esqueciSenha() {
        AlertaUtil.exibirAlerta("Contate o administrador para recuperar o login.", Alert.AlertType.ERROR);
    }
    
    private void voltarFirstPage(){
        try {
            App.setRoot("first_page");
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir tela principal.", Alert.AlertType.ERROR);
        }
    }
}
